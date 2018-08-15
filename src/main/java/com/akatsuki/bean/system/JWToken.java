package com.akatsuki.bean.system;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "JWToken")
public class JWToken {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "tokenUid",length = 36)
    private String tokenUid;

    private String author;

    private String claim;

    private String userUid;

    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTokenUid() {
        return tokenUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public JWToken(String author, String claim, String userUid, String subject) {
        this.author = author;
        this.claim = claim;
        this.userUid = userUid;
        this.subject = subject;
    }

    public JWToken(){}

    public String toString(){
        return getAuthor()+";"+getClaim();
    }

}
