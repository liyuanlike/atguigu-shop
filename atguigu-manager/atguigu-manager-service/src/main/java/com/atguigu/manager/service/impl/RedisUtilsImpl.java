package com.atguigu.manager.service.impl;

import com.atguigu.manager.service.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisUtilsImpl implements RedisUtils {

    @Autowired
    ShardedJedisPool ShardedJedisPool;

    @Override
    public String get(final String key) {
        return execute(fn -> fn.get(key));
    }

    @Override
    public String set(String key, final String value) {
        return execute(fn -> fn.set(key, value));
    }

    @Override
    public Long set(String key, String value, Integer second) {
        return execute(fn -> {
            fn.set(key, value);
            return fn.expire(key, second);
        });
    }

    @Override
    public Long delete(String key) {
        return execute(fn -> fn.del(key));
    }

    @Override
    public Long expire(String key, Integer second) {
        return execute(fn -> fn.expire(key, second));
    }

    /**
     * 统一实现 try catch
     * @param fn
     * @param <T>
     * @return
     */
    private <T> T execute(Callback<T> fn) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = this.ShardedJedisPool.getResource();
            return fn.callback(shardedJedis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (shardedJedis != null)
                shardedJedis.close();
        }
        return null;
    }

    private interface Callback<T> {
        T callback(ShardedJedis sj);
    }
}
