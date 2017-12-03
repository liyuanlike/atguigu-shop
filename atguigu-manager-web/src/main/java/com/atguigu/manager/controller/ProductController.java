package com.atguigu.manager.controller;

import com.atguigu.manager.pojo.Product;
import com.atguigu.manager.pojo.Productdesc;
import com.atguigu.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/restful/page")
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // POST http://localhost:8180/restful/page/product
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<Void> saveProduct(Product product, Productdesc productdesc) {
        try {
            productService.insertSelective(product);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
