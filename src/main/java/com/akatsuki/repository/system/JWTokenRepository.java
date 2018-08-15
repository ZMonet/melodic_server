package com.akatsuki.repository.system;

import com.akatsuki.bean.system.JWToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JWTokenRepository extends JpaRepository<JWToken,String> {

    JWToken findByTokenUid(String tokenUid);

    JWToken findByUserUidAndSubject(String userUid, String subject);
}
