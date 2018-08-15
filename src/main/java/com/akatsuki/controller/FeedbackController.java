package com.akatsuki.controller;

import com.akatsuki.bean.system.ResultModel;
import com.akatsuki.service.FeedbackService;
import com.akatsuki.service.TaskBoxService;
import com.akatsuki.service.TaskService;
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
 * Created by think on 2018/5/3.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/addFeedback")
    public ResponseEntity<ResultModel> AddTask(@RequestBody String param) throws ServletException {

        JSONObject feedback = JSONObject.fromObject(param);
        String uerUid = feedback.getString("userUid");
        String massage = feedback.getString("feedback");

        feedbackService.addFeedback(uerUid, massage);

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK), HttpStatus.OK);

    }
}
