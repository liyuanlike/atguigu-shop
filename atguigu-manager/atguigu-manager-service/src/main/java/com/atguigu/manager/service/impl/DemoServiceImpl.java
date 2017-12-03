package com.atguigu.manager.service.impl;

import com.atguigu.manager.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String hello(String name) {
        return "Hello " + name + " !";
    }
}
