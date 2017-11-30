package com.atguigu.restfuluser.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-mybatis.xml"})
public class UserMapperTest {

    @Resource
    UserMapper userMapper;

    @Test
    public void testUserMapper() {
        System.out.println(userMapper);
    }

}