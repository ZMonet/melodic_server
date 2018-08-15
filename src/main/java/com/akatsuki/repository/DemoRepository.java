package com.akatsuki.repository;

import com.akatsuki.bean.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yusee on 2018/4/9.
 */
public interface DemoRepository extends JpaRepository<Demo,String>{
    List<Demo> findByDemoUid(String demoUid);
}
