package com.atguigu.sso.service;

import com.atguigu.manager.pojo.User;

public interface UserService {
    Boolean check(String param, Integer type);

    Boolean register(User user);

    String login(User user);

    User selectUserByTicket(String ticket);
}
