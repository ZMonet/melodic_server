package com.akatsuki.service;

import com.akatsuki.bean.Task;
import com.akatsuki.bean.TaskBox;
import com.akatsuki.repository.TaskBoxRepository;
import com.akatsuki.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;


/**
 * Created by think on 2018/4/25.
 */
@Service
public class TaskBoxService {

    @Autowired
    private TaskBoxRepository taskBoxRepository;

    @Transactional
    public void save(TaskBox task){taskBoxRepository.save(task);}

    @Transactional
    public void delete(TaskBox task){taskBoxRepository.delete(task);}

    @Transactional
    public TaskBox findByTaskBoxUid(String taskBoxUid){return taskBoxRepository.findByTaskBoxUid(taskBoxUid);}

    @Transactional
    public List<TaskBox> findByTaskBoxByUserUid(String userUid){
        return taskBoxRepository.findByUserUid(userUid);
    }

    @Transactional
    public Task addTaskToTaskBox(String taskBoxUid,String title,String description,String startTime,String endTime,String level,String repeat,String status,String userUid){

        TaskBox taskBox = taskBoxRepository.findByTaskBoxUid(taskBoxUid);
        Task task = new Task(title,description,startTime,endTime,level,repeat,status,taskBox,userUid);
        taskBox.addTaskToTaskBox(task);
        taskBoxRepository.save(taskBox);
        return task;
    }

    @Transactional
    public TaskBox addTaskBox(String name,String userUid){
        TaskBox taskBox = new TaskBox(name,userUid);
        taskBoxRepository.save(taskBox);
        return taskBox;
    }

    @Transactional
    public void deleteTaskBox(String taskBoxUid){
        TaskBox taskBox = taskBoxRepository.findByTaskBoxUid(taskBoxUid);
        taskBoxRepository.delete(taskBox);
    }
}
