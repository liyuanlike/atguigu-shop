package com.atguigu.sso.controller;

import com.atguigu.manager.pojo.User;
import com.atguigu.sso.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查参数是否可用
     * http://sso.atguigu.com/user/check/{param}/{type}
     */
    @RequestMapping(value = "/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
                                         @PathVariable("type") Integer type) {
        try {
            Boolean res = userService.check(param, type);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 用户注册
     * http://sso.atguigu.com/user/register
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(User user) {
        try {
            Boolean res = userService.register(user);
            String msg;
            if (res)
                return ResponseEntity.status(HttpStatus.CREATED).body("注册成功");
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册失败,请校验数据后再提交数据");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 用户登陆
     * http://sso.atguigu.com/user/login
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(User user) {
        try {
            String ticket = userService.login(user);
            if (StringUtils.isNotBlank(ticket)) {
                return ResponseEntity.status(HttpStatus.OK).body(ticket);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 通过ticket查询用户信息
     * http://sso.atguigu.com/user/{ticket}
     */
    @RequestMapping(value = "{ticket}", method = RequestMethod.GET)
    public ResponseEntity<User> selectUserByTicket(@PathVariable("ticket") String ticket) {
        try {
            User user = userService.selectUserByTicket(ticket);
            if (user == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            else
                return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
