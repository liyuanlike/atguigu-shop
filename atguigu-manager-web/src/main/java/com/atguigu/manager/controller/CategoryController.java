package com.atguigu.manager.controller;

import com.atguigu.manager.pojo.Category;
import com.atguigu.manager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/restful/page/")
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // GET http://localhost:8180/restful/page/contentcategory
    @RequestMapping("/contentcategory")
    public ResponseEntity<List<Category>> contentCategory() {

        try {
            List<Category> categories = categoryService.select(null);
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping("category")
    public ResponseEntity<List<Category>> category() {
        try {
            List<Category> categories = categoryService.select(null);
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
