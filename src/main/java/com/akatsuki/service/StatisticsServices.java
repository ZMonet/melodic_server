package com.akatsuki.service;

import com.akatsuki.bean.Status;
import com.akatsuki.bean.Task;
import com.akatsuki.util.DateApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by think on 2018/5/12.
 */
@Service
public class StatisticsServices {

    @Autowired
    TaskService taskService;

    @Transactional
    public int[] getWeekTask(String userUid)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int[] taskNum=new int[7];
        for(int a=0;a<=6;a++)
        {
            String day = DateApplication.getCurrentMonday(a);
            Date date = null;
            try {
                date = dateFormat.parse(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            day = dateFormat.format(date);
            List<Task> weekTasks = taskService.findCompletedTaskByDate(userUid,day);
            taskNum[a] = weekTasks==null?0:weekTasks.size();
        }
        return taskNum;
    }

    @Transactional
    public int getWeekBest(String userUid)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        float[] max = {-1,-1};
        for(int a=0;a<=6;a++)
        {
            String day = DateApplication.getCurrentMonday(a);
            Date date = null;
            try {
                date = dateFormat.parse(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            day = dateFormat.format(date);
            List<Task> allTask = taskService.findTasksByDate(userUid,day);
            int all = allTask==null?0:allTask.size();
            List<Task> completeTask = taskService.findCompletedTaskByDate(userUid,day);
            int complete = completeTask==null?0:completeTask.size();
            if(all!=0 && complete!=0){
                float result = complete/all;
                if(result>max[1]){
                    max[1] = result;
                    max[0] = a;
                }
            }
        }
        return (int)max[0];
    }


    @Transactional
    public int[] getAllStatistic(String userUid){
        List<Task> tasks= taskService.findTaskByUserUid(userUid);
        List<Task> finished = new ArrayList<>();
        List<Task> unfinished = new ArrayList<>();
        List<Task> invalid = new ArrayList<>();

        for(Task task:tasks){
            if (task.getStatus()==Status.FINISHED){
                finished.add(task);
            }
            else if (task.getStatus()==Status.UNFINISHED){
                unfinished.add(task);
            }
            else if (task.getStatus()==Status.INVAILD){
                invalid.add(task);
            }
        }

        return new int[] {finished.size(),unfinished.size(),invalid.size()};
    }

}
