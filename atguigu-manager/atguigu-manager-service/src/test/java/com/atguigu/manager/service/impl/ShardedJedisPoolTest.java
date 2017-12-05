package com.atguigu.manager.service.impl;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-redis.xml")
public class ShardedJedisPoolTest {
    @Resource
    ShardedJedisPool shardedJedisPool;

    @Test
    public void test() {
        ShardedJedis sj = shardedJedisPool.getResource();
        sj.set("sj01", "ShardedJedisPool");
        String sj1 = sj.get("sj");
        System.out.println(sj1);
    }
}
