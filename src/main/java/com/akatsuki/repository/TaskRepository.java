package com.akatsuki.repository;

import com.akatsuki.bean.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by think on 2018/4/25.
 */
public interface TaskRepository extends JpaRepository<Task,String> {

     Task findByTaskUid(long taskUid);

     List<Task> findByUserUid(String userUid);

     List<Task> findByUserUidAndStartTimeLike(String userUid,String data);
}
