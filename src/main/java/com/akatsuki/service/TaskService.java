package com.akatsuki.service;

import com.akatsuki.bean.Priority;
import com.akatsuki.bean.Status;
import com.akatsuki.bean.Task;
import com.akatsuki.repository.TaskRepository;
import com.akatsuki.util.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.akatsuki.bean.Status.FINISHED;

/**
 * Created by think on 2018/4/25.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public void delete(Task task){taskRepository.delete(task);}

    @Transactional
    public Task findByTaskUid(long taskUid){return taskRepository.findByTaskUid(taskUid);}

    @Transactional
    public long updateByTaskUid(long taskUid,String title,String description,String startTime,String endTime,String level,String repeat,String status){
        Task task = taskRepository.findByTaskUid(taskUid);
        task.setCircle(repeat);
        task.setDescription(description);
        task.setTitle(title);
        task.setEndTime(endTime);
        task.setStartTime(startTime);
        task.setPriority(Priority.valueOf(level));
        task.setStatus(Status.valueOf(status));
        return task.getTaskUid();
    }

    @Transactional
    public void deleteTaskByTaskUid(long taskUid){
        Task task = taskRepository.findByTaskUid(taskUid);
        taskRepository.delete(task);
    }

    @Transactional
    public List<Task> findTasksByDateTime(String userUid,String dateTime){
        List<Task> result = new LinkedList<>();
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        Date date = null;
        try {
            date = dateTimeFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String patternDate = dateFormat.format(date)+'%';

        Date beforeDate = null;
        Date afterDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE,30);
        afterDate = cal.getTime();
        cal.add(Calendar.HOUR,-1);
        beforeDate = cal.getTime();

        List<Task> tasks = taskRepository.findByUserUidAndStartTimeLike(userUid,patternDate);

        if(tasks.size() == 0) return null;

        for (Task task:tasks) {

            Date tempDate = null;
            try {
                tempDate = dateTimeFormat.parse(task.getStartTime());
                Calendar tempCal = Calendar.getInstance();
                tempCal.setTime(tempDate);
                cal.set(Calendar.HOUR,tempCal.get(Calendar.HOUR));
                cal.set(Calendar.MINUTE,tempCal.get(Calendar.MINUTE));
                tempDate = cal.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(tempDate.getTime() > beforeDate.getTime()&&tempDate.getTime()<afterDate.getTime()){
                result.add(task);
            }
        }

        return result;
    }

    @Transactional
    public String findSimilarTasksByDateTime(String userUid,String dateTime){
        List<String> result = new LinkedList<>();

        List<String> taskTitleList = new LinkedList<>();
        List<Task> alltTskList = taskRepository.findByUserUid(userUid);
        for(Task task:alltTskList){
            taskTitleList.add(task.getTitle());
        }

        List<Task> originTasks = findTasksByDateTime(userUid,dateTime);

        for(Task task:originTasks){
            Recommend recommend = new Recommend();
            result.addAll(recommend.recommendByTitle(task.getTitle(),taskTitleList));
        }

        int min=0;
        Random random = new Random();
        int max=result.size();
        if(max == 0){
            if(alltTskList.size()!=0){
                max = alltTskList.size();
                return alltTskList.get(random.nextInt(max)%(max-min+1) + min).getTitle();
            }else{
                return null;
            }
        }
        int s = random.nextInt(max)%(max-min+1) + min;

        return result.get(s);
    }

    @Transactional
    public List<Task> findTasksByDate(String userUid,String dateTime){
        return taskRepository.findByUserUidAndStartTimeLike(userUid,dateTime+'%');
    }

    @Transactional
    public List<Task> findTaskByUserUid(String userUid){
        return taskRepository.findByUserUid(userUid);
    }

    @Transactional
    public List<Task> findCompletedTaskByDate(String userUid,String dateTime) {
        List<Task> tasks = findTasksByDate(userUid, dateTime);
        if (tasks.size() == 0){
            return null;
        }
        List<Task> CompleteTask= new LinkedList<>();
        for(int i = 0 ; i < tasks.size() ; i++) {
            if(tasks.get(i).getStatus()==Status.FINISHED)
            {
                CompleteTask.add(tasks.get(i));
            }
        }
        return CompleteTask;
    }

}
