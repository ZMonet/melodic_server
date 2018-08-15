package com.akatsuki.service.system;

import com.akatsuki.bean.system.User;
import com.akatsuki.repository.system.UserRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 * Created by yusee on 2018/4/14.
 */
@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User findUserByName(String Name){
        return userRepository.findByUserName(Name);
    }

    @Transactional
    public User findUserByEmail(String Email){
        return userRepository.findByUserEmail(Email);
    }

    @Transactional
    public String getUserInfo(String userUid){
        User user = userRepository.findByUserUid(userUid);
        JSONObject obj = JSONObject.fromObject(user);
        obj.remove("userVerification");
        return obj.toString();
    }

    @Transactional
    public String addUser(String Email,String pwd,String userName){
        User user = new User(Email,pwd,userName);
        userRepository.save(user);
        return user.getUserUid();
    }

    @Transactional
    public void resetPassword(String userEmail,String pwd){
        User user = userRepository.findByUserEmail(userEmail);
        user.setUserVerification(pwd);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserInfo(String userUid,String userName){
        User user = userRepository.findByUserUid(userUid);
        user.setUserName(userName);
        userRepository.save(user);
    }

}
