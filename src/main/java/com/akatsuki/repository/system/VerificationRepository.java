package com.akatsuki.repository.system;

import com.akatsuki.bean.system.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yusee on 2018/4/16.
 */
public interface VerificationRepository extends JpaRepository<Verification,String>{

    Verification findByUserEmail(String userEmail);
}
