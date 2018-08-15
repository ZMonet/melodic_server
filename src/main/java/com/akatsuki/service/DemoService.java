package com.akatsuki.service;

import com.akatsuki.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yusee on 2018/4/9.
 */
@Service
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    public String Demo(){return "This is a Demo";}
}
