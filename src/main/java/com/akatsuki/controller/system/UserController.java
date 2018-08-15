package com.akatsuki.controller.system;

import com.akatsuki.bean.system.ResultModel;
import com.akatsuki.bean.system.User;
import com.akatsuki.service.TaskBoxService;
import com.akatsuki.service.system.JWTokenService;
import com.akatsuki.service.system.UserService;
import com.akatsuki.service.system.VerificationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by yusee on 2018/4/14.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private JWTokenService tokenService;

    @Autowired
    private TaskBoxService taskBoxService;

    @PostMapping("/login")
    public ResponseEntity<ResultModel> Login(@RequestBody String param) throws ServletException  {
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");
        String Name = user.getString("userName");
        String Verification = user.getString("userVerification");

        User currentUser = null;
        String key = "";
        if(!Email.equals("")){
            key = Email;
            currentUser = userService.findUserByEmail(Email);
        }else if(!Name.equals("")){
            key = Name;
            currentUser = userService.findUserByName(Name);
        }

        String jwtToken = "";

        if(currentUser == null){
            return new ResponseEntity<>(ResultModel.error(HttpStatus.FORBIDDEN,"亲，您输入的账号还未注册呢"),HttpStatus.FORBIDDEN);
        }

        String currentUserUserVerification = currentUser.getUserVerification();
        if(!Verification.equals(currentUserUserVerification)){
            return new ResponseEntity<>(ResultModel.error(HttpStatus.UNAUTHORIZED, "亲，你输入的用户名或密码不正确哦"), HttpStatus.UNAUTHORIZED);
        }

        jwtToken = Jwts.builder()
                .setSubject("verification")
                .claim("userUid",currentUser.getUserUid())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"akatsuki")
                .compact();

        String token = tokenService.createToken("Akatsuki",jwtToken,userService.findUserByEmail(Email).getUserUid(),"verification");;

        JSONObject response = new JSONObject();
        response.put("Authorization",token);
        response.put("userUid",currentUser.getUserUid());

        /** 测试*/
        if( taskBoxService.findByTaskBoxByUserUid(currentUser.getUserUid()).size() == 0){
            taskBoxService.addTaskBox("默认收集箱",currentUser.getUserUid());
        }

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, response.toString()), HttpStatus.OK);
    }

    @PostMapping("signup/signupEmailCkeck")
    public ResponseEntity<ResultModel> signupEmailCkeck(@RequestBody String param) throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");

        if(userService.findUserByEmail(Email)!=null){
            return new ResponseEntity<>(ResultModel.error(HttpStatus.FORBIDDEN, "亲，这个邮箱号已经被注册过了哦"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, "邮箱账号符合规范"), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResultModel> signup(@RequestBody String param) throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");
        String Verification = user.getString("userVerification");

        String userUid = userService.addUser(Email,Verification,Email);
        /** 测试*/
        taskBoxService.addTaskBox("默认收集箱",userUid);
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, "注册成功"), HttpStatus.OK);
    }

    @PostMapping("/signup/verificate")
    public ResponseEntity<ResultModel> verificate(@RequestBody String param) throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");

        verificationService.sendVerificationMail(Email,"code","Melodic注册验证","我们已经收到了您的注册请求，请在APP内输入上方的验证码完成注册");

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, "发送成功"), HttpStatus.OK);
    }

    @PostMapping("/signip/matchVerificationCode")
    public ResponseEntity<ResultModel> matchVerificationCode(@RequestBody String param) throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");
        String VerificationCode = user.getString("verificationCode");

        if(!verificationService.matchVerificationCode(Email,VerificationCode)){
            return new ResponseEntity<>(ResultModel.error(HttpStatus.UNAUTHORIZED, "亲，你输入的验证码不正确哦"), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, "验证成功"), HttpStatus.OK);
    }

    @PostMapping("/emailCheck")
    public ResponseEntity<ResultModel> emailCheck(@RequestBody String param) throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");

        if(userService.findUserByEmail(Email) == null){
            return new ResponseEntity<>(ResultModel.error(HttpStatus.FORBIDDEN, "亲，这个邮箱号还没有注册过呢"), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, "邮箱账号符合规范"), HttpStatus.OK);
    }

    @PostMapping("/loginProblem")
    public ResponseEntity<ResultModel> loginProblem(@RequestBody String param) throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");

        String jwtToken = Jwts.builder()
                .setSubject("resetPassword")
                .claim("userUid",userService.findUserByEmail(Email).getUserUid())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"akatsuki")
                .compact();

        tokenService.createToken("Akatsuki",jwtToken,userService.findUserByEmail(Email).getUserUid(),"resetPassword");

        verificationService.sendVerificationMail(Email,"url","Melodic密码重置","http://www.wereadthinker.cn/user/resetPasswordHome?authorization=Akatsuki;"+jwtToken+"&email="+Email,"我们已经收到了您的密码重置请求，请复制上方链接到浏览器进行密码重置");

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, "密码重置邮件已发送到您的邮箱"), HttpStatus.OK);
    }

    @GetMapping("/resetPasswordHome")
    public String getResetPasswordHome (HttpServletRequest request, Model model) throws ServletException{
        String email = request.getParameter("email");
        String authorization = request.getParameter("authorization");
        model.addAttribute("email", email);
        model.addAttribute("authorization",authorization);
        return "password-reset";
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ResultModel> resetPassword (@RequestBody String param)throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String Email = user.getString("userEmail");
        String Password = user.getString("password");

        userService.resetPassword(Email,Password);
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,"密码重置成功"),HttpStatus.OK);
    }

    @PostMapping("/addTaskBox")
    public ResponseEntity<ResultModel> addTaskBox(@RequestBody String param) throws ServletException{
        JSONObject obj = JSONObject.fromObject(param);
        String userUid = obj.getString("userUid");
        String taskBoxName = obj.getString("taskBoxName");

        String taskBox = JSONObject.fromObject(taskBoxService.addTaskBox(taskBoxName,userUid)).toString();
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,taskBox),HttpStatus.OK);
    }

    @PostMapping("/deleteTaskBox")
    public ResponseEntity<ResultModel> deleteTaskBox(@RequestBody String param) throws ServletException{
        JSONObject obj = JSONObject.fromObject(param);
        String taskBoxUid = obj.getString("taskBoxUid");

        taskBoxService.deleteTaskBox(taskBoxUid);
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,"收集箱删除成功"),HttpStatus.OK);
    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<ResultModel> updateUserInfo(@RequestBody String param) throws ServletException{
        JSONObject obj = JSONObject.fromObject(param);
        String userUid = obj.getString("userUid");
        String userName = obj.getString("userName");

        userService.updateUserInfo(userUid,userName);
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,"修改用户名成功"),HttpStatus.OK);
    }

    @PostMapping("/getUserInfo")
    public ResponseEntity<ResultModel> getUserInfo(@RequestBody String param) throws ServletException{
        JSONObject obj = JSONObject.fromObject(param);
        String userUid = obj.getString("userUid");

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,userService.getUserInfo(userUid)),HttpStatus.OK);
    }

    @PostMapping("/logOut")
    public ResponseEntity<ResultModel> logOut(@RequestBody String param) throws ServletException{
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,"退出当前账号"),HttpStatus.OK);
    }

}



