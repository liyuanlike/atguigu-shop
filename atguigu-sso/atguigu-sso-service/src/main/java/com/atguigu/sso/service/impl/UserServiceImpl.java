package com.atguigu.sso.service.impl;

import com.atguigu.manager.mapper.UserMapper;
import com.atguigu.manager.pojo.User;
import com.atguigu.sso.service.RedisService;
import com.atguigu.sso.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Boolean check(String param, Integer type) {
        User user = new User();
        switch (type) {
            case 1:
                user.setUsername(param);
                break;
            case 2:
                user.setPhone(param);
                break;
            case 3:
                user.setEmail(param);
                break;
            default:
                throw new RuntimeException("请求参数不合法");
        }
        User selectOne = userMapper.selectOne(user);
        if (selectOne == null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(User user) {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        try {
            userMapper.insert(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String login(User user) {
        // 查询的用户不能为null
        if (user == null) return null;

        User selectUser = new User();
        selectUser.setUsername(user.getUsername());
        User loginUser = userMapper.selectOne(selectUser);

        // 如果查询的用户为空,直接返回null
        if (loginUser == null || !StringUtils.equals(DigestUtils.md5Hex(user.getPassword()), loginUser.getPassword()))
            return null;

        // 向缓存中放入ticket和user数据
        String ticket = DigestUtils.md5Hex(loginUser.getUsername()) + System.nanoTime();
        try {
            redisService.set(ticket, MAPPER.writeValueAsString(loginUser), 1800);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public User selectUserByTicket(String ticket) {
        String jsonData = redisService.get(ticket);
        if (StringUtils.isNotBlank(jsonData)) {
            redisService.expire(ticket, 1800);
            try {
                User user = MAPPER.readValue(jsonData, User.class);
                return user;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
