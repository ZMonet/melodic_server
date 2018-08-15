package com.akatsuki.service.system;

import com.akatsuki.bean.system.Verification;
import com.akatsuki.repository.system.VerificationRepository;
import com.akatsuki.util.VerificationMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Random;

/**
 * Created by yusee on 2018/4/17.
 */
@Service
public class VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Transactional
    public void save(Verification verification){
        verificationRepository.save(verification);
    }

    private String getVerificationCode(String userEmail){

        Verification verification = null;
        if((verification = findVerificationCodeByuserEmail(userEmail))!=null){
            verification.setVerificationCode(String.valueOf(new Random().nextInt(899999) + 100000));
        }else{
            verification = new Verification(String.valueOf(new Random().nextInt(899999) + 100000),userEmail);
        }
        save(verification);
        return verification.getVerificationCode();
    }

    @Transactional
    public Boolean matchVerificationCode(String Email,String Code){
        Verification verification = findVerificationCodeByuserEmail(Email);
        if(Code.equals(verification.getVerificationCode())) return true;
        return false;
    }

    @Transactional
    public Verification findVerificationCodeByuserEmail(String userEmail){
        return verificationRepository.findByUserEmail(userEmail);
    }

    @Transactional
    public void sendVerificationMail(String receiver,String type,String title,String message){
        VerificationMail verificationMail = new VerificationMail(receiver,type,title,getVerificationCode(receiver),message);

        try {
            verificationMail.sendVerificationMail();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void sendVerificationMail(String receiver,String type,String title,String url,String message){
        VerificationMail verificationMail = new VerificationMail(receiver,type,title,url,message);

        try {
            verificationMail.sendVerificationMail();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
