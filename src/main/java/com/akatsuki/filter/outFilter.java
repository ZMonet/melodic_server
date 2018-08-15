package com.akatsuki.filter;

import com.akatsuki.bean.system.JWToken;
import com.akatsuki.service.system.JWTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class outFilter implements Filter {

    private final String[] delSubject = {
            "resetPassword",
            "verification"
    };

    public JWTokenService jwTokenService;

    private void tokenFilter(JWToken token){
        if (token == null) return;
        for(String subject : delSubject){
            if (token.getSubject().equals(subject)){
                jwTokenService.delTokenByTokenUid(token.getTokenUid());
            }
        }
    }

    public outFilter(JWTokenService jwTokenService){
        this.jwTokenService = jwTokenService;
    }

    @Override
    public void destroy() {

    }

    @Override
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

            final String token = authHeader_req.substring(9);

            JWToken token_ = null;
            try {
                final Claims claims = Jwts.parser().setSigningKey("akatsuki").parseClaimsJws(token).getBody();

                token_ = jwTokenService.findByUserUidAndSubject(claims.get("userUid").toString(),claims.getSubject());

                tokenFilter(token_);

            } catch (final SignatureException e) {
                throw new ServletException("Invalid JWToken");
            }

            chain.doFilter(req, res);

        }
        else {

            final String token = authHeader.substring(9);

            JWToken token_ = null;
            try {
                final Claims claims = Jwts.parser().setSigningKey("akatsuki").parseClaimsJws(token).getBody();

                token_ = jwTokenService.findByUserUidAndSubject(claims.get("userUid").toString(),claims.getSubject());

                tokenFilter(token_);

            } catch (final SignatureException e) {
                e.printStackTrace();
                throw new ServletException("Invalid JWToken");
            }

            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
