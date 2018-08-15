package com.akatsuki.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by think on 2018/4/21.
 */
@Entity
@Table(name= "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long feedbackId;

    private String userUid;

    private String message;

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getFeedbackId() {
        return feedbackId;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getMessage() {
        return message;
    }

    public Feedback(String userUid, String message) {
        this.userUid = userUid;
        this.message = message;
    }



}

