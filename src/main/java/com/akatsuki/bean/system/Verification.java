package com.akatsuki.bean.system;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by yusee on 2018/4/16.
 */
@Entity
@Table(name = "Verification")
public class Verification {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "verificationUid",length = 36)
    private String verificationUid;

    private String verificationCode;

    private String userEmail;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getVerificationUid() {
        return verificationUid;
    }

    public Verification(String verificationCode, String userEmail) {
        this.verificationCode = verificationCode;
        this.userEmail = userEmail;
    }

    public Verification(){}

}
