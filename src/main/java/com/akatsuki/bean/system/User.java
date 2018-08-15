package com.akatsuki.bean.system;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by yusee on 2018/4/14.
 */
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "userUid",length = 36)
    private String userUid;

    private String userName;

    private String userVerification;

    private String userEmail;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserVerification(String userVerification) {
        this.userVerification = userVerification;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserVerification() {
        return userVerification;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User(String userEmail,String userVerification,String userName) {
        this.userVerification = userVerification;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    /*default constructor of entity*/
    public User(){}

}
