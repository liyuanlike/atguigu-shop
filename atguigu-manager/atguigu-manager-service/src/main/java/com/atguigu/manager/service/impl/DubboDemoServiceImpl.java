package com.atguigu.manager.service.impl;

import com.atguigu.manager.service.DubboDemoService;
import org.springframework.stereotype.Service;

@Service
public class DubboDemoServiceImpl implements DubboDemoService {
    @Override
    public String hello(String name) {
        return "Hello " + name + " !";
    }
}
