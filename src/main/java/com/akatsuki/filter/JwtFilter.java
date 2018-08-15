package com.akatsuki.filter;

/**
 * Created by yusee on 2018/4/6
 */
import com.akatsuki.bean.system.JWToken;
import com.akatsuki.service.system.JWTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends GenericFilterBean {

    public JWTokenService jwTokenService;

    public JwtFilter(JWTokenService jwTokenService){
        this.jwTokenService = jwTokenService;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String authHeader = request.getHeader("authorization");
        String authHeader_req = request.getParameter("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(req, res);
        }
        else if(authHeader_req != null){
            if(!authHeader_req.startsWith("Akatsuki;")){
                throw new ServletException(" Missing or invalid Authorization authHeader_req");
            }

            final String token = authHeader_req.substring(9);

            JWToken token_ = null;
            try {
                final Claims claims = Jwts.parser().setSigningKey("akatsuki").parseClaimsJws(token).getBody();

                token_ = jwTokenService.findByUserUidAndSubject(claims.get("userUid").toString(),claims.getSubject());

                if(token_ == null){
                    throw new ServletException("Invalid JWToken");
                }

                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                throw new ServletException("Invalid JWToken");
            }

            chain.doFilter(req, res);

        }
        else {

            if (authHeader == null || !authHeader.startsWith("Akatsuki;")) {
                throw new ServletException(" Missing or invalid Authorization authHeader");
            }

            final String token = authHeader.substring(9);

            JWToken token_ = null;
            try {
                final Claims claims = Jwts.parser().setSigningKey("akatsuki").parseClaimsJws(token).getBody();

                token_ = jwTokenService.findByUserUidAndSubject(claims.get("userUid").toString(),claims.getSubject());

                if(token_ == null){
                    throw new ServletException("Invalid JWToken");
                }
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                e.printStackTrace();
                throw new ServletException("Invalid JWToken");
            }

            chain.doFilter(req, res);
        }
    }
}