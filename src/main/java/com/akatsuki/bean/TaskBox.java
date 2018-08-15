package com.akatsuki.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2018/4/21.
 */
@Entity
@Table(name="TaskBox")
public class TaskBox {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "taskBoxUid",length = 36)
    private String taskBoxUid;

    @OneToMany(targetEntity = Task.class,cascade = CascadeType.ALL)
    private List<Task> tasks;

    private String name;

    private String userUid;

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getTaskBoxUid() {
        return taskBoxUid;
    }

    public String getTaskBoxName() {return this.name;}

    public void setTaskBoxName(String name) {this.name=name;}

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTaskToTaskBox(Task task ){
        this.tasks.add(task);
    }

    public boolean deleteTaskFromTaskBox(Task task ){
        return  this.tasks.remove(task);
    }

    public TaskBox(String name, String userUid) {
        this.name = name;
        this.userUid = userUid;
    }

    public TaskBox(){}
}
