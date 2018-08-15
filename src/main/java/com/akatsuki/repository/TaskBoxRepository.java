package com.akatsuki.repository;

import com.akatsuki.bean.Task;
import com.akatsuki.bean.TaskBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskBoxRepository extends JpaRepository<TaskBox,String> {

    TaskBox findByTaskBoxUid(String taskBoxUid);

    List<TaskBox> findByUserUid(String userUid);
}