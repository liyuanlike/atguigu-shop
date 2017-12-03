package com.atguigu.manager.controller;

import com.atguigu.manager.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class DemoController {

    @Autowired
    DemoService DemoService;

    @RequestMapping("/hello")
    public String hello(Map<String, Object> map, @RequestParam("name") String name) {
        String hello = DemoService.hello(name);
        map.put("hello", hello);
        System.out.println(">>>>>>>>>>>>>>>>>>>>" + hello);
        return "hello";
    }

}