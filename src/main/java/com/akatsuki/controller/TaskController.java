package com.akatsuki.controller;

import com.akatsuki.bean.system.ResultModel;
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
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/deleteTask")
    public ResponseEntity<ResultModel> DeleteTask(@RequestBody String param) throws ServletException
    {
        JSONObject obj = JSONObject.fromObject(param);
        long taskUid = Integer.valueOf(obj.getString("taskUid"));
        taskService.deleteTaskByTaskUid(taskUid);

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, "列表箱删除成功"), HttpStatus.OK);
    }

    @PostMapping("/findSimilarTasksByDateTime")
    public ResponseEntity<ResultModel> findSimilarTasksByDate(@RequestBody String param) throws ServletException{
        JSONObject obj = JSONObject.fromObject(param);
        String userUid = obj.getString("userUid");
        String currentTime = obj.getString("currentTime");

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, taskService.findSimilarTasksByDateTime(userUid,currentTime).toString()), HttpStatus.OK);
    }

    @PostMapping("/findTasksByDate")
    public ResponseEntity<ResultModel> findTaskByDate(@RequestBody String param) throws ServletException{
        JSONObject obj = JSONObject.fromObject(param);
        String userUid = obj.getString("userUid");
        String dateTime = obj.getString("dateTime");

        JSONArray currentDateTaskList = JSONArray.fromObject(taskService.findTasksByDate(userUid,dateTime));

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, currentDateTaskList.toString()), HttpStatus.OK);
    }

    @PostMapping("/findTasksByUserUid")
    public ResponseEntity<ResultModel> findTaskByUserUid(@RequestBody String param) throws ServletException{
        JSONObject obj = JSONObject.fromObject(param);
        String userUid = obj.getString("userUid");

        JSONArray currentDateTaskList = JSONArray.fromObject(taskService.findTaskByUserUid(userUid));

        return new ResponseEntity<>(ResultModel.ok(HttpStatus.OK, currentDateTaskList.toString()), HttpStatus.OK);
    }

}
