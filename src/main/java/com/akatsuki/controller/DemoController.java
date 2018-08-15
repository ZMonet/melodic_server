package com.akatsuki.controller;

import com.akatsuki.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yusee on 2018/4/6.
 */
@RestController
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/demo")
    public String Demo() {
        return demoService.Demo();
    }
}
