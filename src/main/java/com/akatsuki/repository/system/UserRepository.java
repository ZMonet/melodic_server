package com.akatsuki.repository.system;

import com.akatsuki.bean.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yusee on 2018/4/14.
 */
public interface UserRepository extends JpaRepository<User,String>{

    User findByUserEmail(String Email);

    User findByUserName(String Name);

    User findByUserUid(String userUid);
}
