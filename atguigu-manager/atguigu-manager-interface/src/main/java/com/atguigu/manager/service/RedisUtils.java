package com.atguigu.manager.service;

public interface RedisUtils {

    String get(String key);

    String set(String key, String value);

    Long set(String key, String value, Integer second);

    Long delete(String key);

    Long expire(String key, Integer second);

}
