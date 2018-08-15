package com.akatsuki.service;

import com.akatsuki.bean.Feedback;
import com.akatsuki.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * Created by think on 2018/4/25.
 */
@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Transactional
    public void save(Feedback feedback){feedbackRepository.save(feedback);}

    @Transactional
    public void delete(Feedback task){feedbackRepository.delete(task);}

    @Transactional
    public Feedback findByUserUid(String UserUid){return feedbackRepository.findByUserUid(UserUid);}

    @Transactional
    public void addFeedback(String userUid,String massage)
    {
        Feedback feedback=new Feedback(userUid,massage);
        feedbackRepository.save(feedback);
    }
}