package com.atguigu.manager.service.impl;

import com.atguigu.manager.service.DubboDemoService;


public class DubboDemoServiceImpl implements DubboDemoService {
    @Override
    public String hello(String name) {
        return "Hello " + name + " !";
    }
}
