package com.atguigu.sso.service;

public interface RedisService {

    String get(String key);

    String set(String key, String value);

    Long set(String key, String value, Integer seconds);

    Long expire(String key, Integer seconds);

    Long del(String key);
}
