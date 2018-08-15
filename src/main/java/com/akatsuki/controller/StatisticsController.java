package com.akatsuki.controller;

import com.akatsuki.bean.system.ResultModel;
import com.akatsuki.service.StatisticsServices;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletException;
/**
 * Created by think on 2018/4/25.
 */
@Controller
@RequestMapping("/statics")
public class StatisticsController {

    @Autowired
    private StatisticsServices staticticsservice=new StatisticsServices();

    @PostMapping("/weekStatics")
    public ResponseEntity<ResultModel> weekStatics(@RequestBody String param) throws ServletException {

        JSONObject data = JSONObject.fromObject(param);
        String userUid=data.getString("userUid");
        JSONArray tasks = JSONArray.fromObject(staticticsservice.getWeekTask(userUid));
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,tasks.toString()), HttpStatus.OK);

    }

    @PostMapping("/allStatics")
    public ResponseEntity<ResultModel> allStatics(@RequestBody String param) throws ServletException {

        JSONObject data = JSONObject.fromObject(param);
        String userUid=data.getString("userUid");
        JSONArray tasks = JSONArray.fromObject(staticticsservice.getAllStatistic(userUid));
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,tasks.toString()), HttpStatus.OK);

    }

    @PostMapping("/bestStatics")
    public ResponseEntity<ResultModel> bestStatics(@RequestBody String param) throws ServletException {

        JSONObject data = JSONObject.fromObject(param);
        String userUid=data.getString("userUid");
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK,staticticsservice.getWeekBest(userUid)), HttpStatus.OK);

    }
}
