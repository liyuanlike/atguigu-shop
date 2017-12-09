package com.atguigu.sso.service.impl;

import com.atguigu.sso.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private ShardedJedisPool shardedJedisPool;

    private <T> T execute(Callback<ShardedJedis, T> fun) {
        ShardedJedis resource = null;
        try {
            resource = shardedJedisPool.getResource();
            return fun.callback(resource);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resource != null) {
                resource.close();
            }
        }
        return null;
    }

    @Override
    public String get(String key) {
        return this.execute(fun -> fun.get(key));
    }

    @Override
    public String set(String key, String value) {
        return this.execute(fun -> set(key, value));
    }

    @Override
    public Long set(String key, String value, Integer seconds) {
        return this.execute(fun -> {
            fun.set(key, value);
            return fun.expire(key, seconds);
        });
    }

    @Override
    public Long expire(String key, Integer seconds) {
        return this.execute(fun -> fun.expire(key, seconds));
    }

    @Override
    public Long del(String key) {
        return this.execute(fun -> fun.del(key));
    }

    private interface Callback<E, T> {
        T callback(E e);
    }
}
