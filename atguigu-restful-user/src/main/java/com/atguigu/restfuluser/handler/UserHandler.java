package com.atguigu.restfuluser.handler;

import com.atguigu.restfuluser.mapper.UserMapper;
import com.atguigu.restfuluser.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserHandler {

    @Autowired
    UserMapper userMapper;

    /*
     * 添加用户
     * http://localhost/user
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(User user) {
        try {
            userMapper.insertSelective(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除用户
     * http://localhost/user/2
     */
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeUser(@PathVariable("id") Long id) {
        try {
            userMapper.deleteByPrimaryKey(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 修改用户
     * http://localhost/user
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(User user) {
        try {
            userMapper.updateByPrimaryKeySelective(user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 根据 id 查询单个用户
     * http://localhost/user/2
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userMapper.selectByPrimaryKey(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 查询所有用户
     * http://localhost/user
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userMapper.select(null);
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
