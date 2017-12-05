package com.atguigu.manager.controller;

import com.atguigu.manager.pojo.Category;
import com.atguigu.manager.service.CategoryService;
import com.atguigu.manager.service.RedisUtils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@RequestMapping("/page")
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtils redisUtils;

    private static final String CATEGORY_LIST = "CATEGORY_LIST";
    private static final ObjectMapper objectMapper= new ObjectMapper();

    /**
     * 分类展现树
     * @return
     */
    @RequestMapping("category")
    public ResponseEntity<List<Category>> category() {

        // 先命中缓存

        try {
            String jsonData = redisUtils.get(CATEGORY_LIST);
            if (jsonData != null) {
                // 获取需要解析的java类型
                JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Category.class);
                // 解析为java对象
                List<Category> categories = objectMapper.readValue(jsonData, javaType);
                return ResponseEntity.status(HttpStatus.OK).body(categories);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 未命中缓存就命中数据库
        try {
            List<Category> categories = categoryService.select(null);
            redisUtils.set(CATEGORY_LIST, objectMapper.writeValueAsString(categories), 30*24*3600);
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
