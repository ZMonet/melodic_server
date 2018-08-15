package com.akatsuki.service.system;

import com.akatsuki.bean.system.JWToken;
import com.akatsuki.repository.system.JWTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class JWTokenService {

    @Autowired
    private JWTokenRepository tokenRepository;

    @Transactional
    public void save(JWToken token){
        tokenRepository.save(token);
    }

    @Transactional
    public JWToken findByTokenUid(String tokenUid){
        return tokenRepository.findByTokenUid(tokenUid);
    }

    @Transactional
    public JWToken findByUserUidAndSubject(String userUid, String subject){
        return tokenRepository.findByUserUidAndSubject(userUid,subject);
    }

    @Transactional
    public String createToken(String author, String claim, String userId, String subject){

        JWToken token = new JWToken(author,claim,userId,subject);;
        JWToken tempToken = null;

        if((tempToken = tokenRepository.findByUserUidAndSubject(userId, subject)) != null){
            tempToken.setAuthor(token.getAuthor());
            tempToken.setClaim(token.getClaim());
            tempToken.setSubject(token.getSubject());
            tempToken.setUserUid(token.getUserUid());
            tokenRepository.save(tempToken);
        }else{
            tokenRepository.save(token);
        }

        return token.toString();
    }

    @Transactional
    public void delTokenByTokenUid(String tokenUid){
        JWToken token = tokenRepository.findByTokenUid(tokenUid);
        tokenRepository.delete(token);
    }

    @Transactional
    public void delTokenByUserUidAndSubject(String userUid,String subject){
        JWToken token = tokenRepository.findByUserUidAndSubject(userUid,subject);
        tokenRepository.delete(token);
    }
}
