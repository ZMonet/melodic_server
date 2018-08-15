package com.akatsuki.bean;

import javax.persistence.*;

/**
 * Created by think on 2018/4/21.
 */
@Entity
@Table(name= "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long taskUid;

    private String title;

    private String description;

    private String startTime;

    private String endTime;

    private Priority priority;

    private Circle circle;

    private Status status;

    private String userUid;

    private String taskBoxUid;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = Circle.valueOf(circle);
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTaskUid() {
        return taskUid;
    }

    public String getTaskBoxUid() {
        return taskBoxUid;
    }

    public void setTaskBoxUid(String taskBoxUid) {
        this.taskBoxUid = taskBoxUid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription(){return this.description;}

    public void setDescription(String description){this.description=description;}

    public Enum getPriority(){return this.priority;}

    public void setPriority(Priority priority){this.priority=priority;}

    public Task(String title, String description, String startTime, String endTime, String priority, String circle, String status, TaskBox taskBox, String userUid) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = Priority.valueOf(priority);
        this.circle = Circle.valueOf(circle);
        this.userUid = userUid;
        this.status = Status.valueOf(status);
        this.taskBoxUid = taskBox.getTaskBoxUid();
    }

    public Task(){};
}
