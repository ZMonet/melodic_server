package com.akatsuki.repository;

import com.akatsuki.bean.Feedback;
import com.akatsuki.bean.Task;
import com.akatsuki.bean.TaskBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,String> {

    Feedback findByUserUid(String userUid);
}