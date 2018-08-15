package com.akatsuki.config;

import com.akatsuki.filter.JwtFilter;
import com.akatsuki.filter.outFilter;
import com.akatsuki.service.system.JWTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class JwtFilterConfig {

    @Autowired
    private JWTokenService jwTokenService;

    @Bean
    @Order(Integer.MAX_VALUE)
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(jwTokenService));

        //需要token认证才能调用的API
        registrationBean.addUrlPatterns("/user/resetPasswordHome");
        registrationBean.addUrlPatterns("/user/resetPassword");
        registrationBean.addUrlPatterns("/user/logOut");
        registrationBean.addUrlPatterns("/user/addTaskBox");
        registrationBean.addUrlPatterns("/user/deleteTaskBox");
        registrationBean.addUrlPatterns("/user/updateUserInfo");
        registrationBean.addUrlPatterns("/user/getUserInfo");
        registrationBean.addUrlPatterns("/task/*");
        registrationBean.addUrlPatterns("/taskbox/*");
        registrationBean.addUrlPatterns("/feedback/*");
        registrationBean.addUrlPatterns("/statics/*");
        return registrationBean;
    }

    @Bean
    @Order(Integer.MIN_VALUE)
    public FilterRegistrationBean outFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new outFilter(jwTokenService));

        //过滤的API
        registrationBean.addUrlPatterns("/user/resetPassword");
        registrationBean.addUrlPatterns("/user/logOut");
        return registrationBean;
    }

}
