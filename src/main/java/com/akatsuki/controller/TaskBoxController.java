package com.akatsuki.controller;

import com.akatsuki.bean.system.ResultModel;
import com.akatsuki.service.TaskBoxService;
import com.akatsuki.service.TaskService;
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
@RequestMapping("/taskbox")
public class TaskBoxController {

    @Autowired
    private TaskBoxService taskBoxService;

    @Autowired
    private TaskService taskService;

    /**
     * @title
     * @description
     * @startTime
     * @endTime
     * @level
     * @repeat
     * @collectioinUid
     * @userUid
     * @return
     * @throws ServletException
     */
    @PostMapping("/addTask")
    public ResponseEntity<ResultModel> AddTask(@RequestBody String param) throws ServletException
    {

        JSONObject task = JSONObject.fromObject(param);
        String title = task.getString("title");
        String description = task.getString("description");

        String startTime = task.getString("startTime");
        String endTime = task.getString("endTime");

        String level = task.getString("level");
        String repeat = task.getString("repeat");
        String status = task.getString("status");
        String collectioinUid = task.getString("collectionUid");
        String userUid = task.getString("userUid");

        System.out.println(task);

        String _task = JSONObject.fromObject(taskBoxService.addTaskToTaskBox(collectioinUid,title,description,startTime,endTime,level,repeat,status,userUid)).toString();
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, _task), HttpStatus.OK);
    }

    @PostMapping("/updateTask")
    public ResponseEntity<ResultModel> UpdateTask(@RequestBody String param) throws ServletException
    {

        JSONObject task = JSONObject.fromObject(param);
        System.out.println(task);
        long taskUid = Integer.valueOf(task.getString("taskUid"));
        String title = task.getString("title");
        String description = task.getString("description");

        String startTime = task.getString("startTime");
        String endTime = task.getString("endTime");

        String level = task.getString("level");
        String repeat = task.getString("repeat");
        String status = task.getString("status");

        taskService.updateByTaskUid(taskUid,title,description,startTime,endTime,level,repeat,status);
        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, taskUid), HttpStatus.OK);
    }

    @PostMapping("/findAll")
    public ResponseEntity<ResultModel> FindAll(@RequestBody String param) throws ServletException{
        JSONObject user = JSONObject.fromObject(param);
        String userUid = user.getString("userUid");

        JSONArray taskBoxList = JSONArray.fromObject(taskBoxService.findByTaskBoxByUserUid(userUid));

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, taskBoxList.toString()), HttpStatus.OK);
    }

}
