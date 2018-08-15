package com.akatsuki.config;

import org.hibernate.LazyInitializationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class ErrorConfig {

    /**
     * 异常处理机制
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler({NullPointerException.class, LazyInitializationException.class})
    public String handleInteralError(HttpServletRequest request, Exception e, Model model) throws Exception {
        model.addAttribute("errorMess",e.getMessage()) ;
        /*return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);*/
        return "/error/500";
    }

    @ExceptionHandler(ServletException.class)
    @ResponseBody
    public String handleBadRequest(HttpServletRequest req, Exception e,Model model) throws Exception {
        model.addAttribute("errorMess",e.getMessage()) ;
        /*return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);*/
        return "/error/404";
    }
}
