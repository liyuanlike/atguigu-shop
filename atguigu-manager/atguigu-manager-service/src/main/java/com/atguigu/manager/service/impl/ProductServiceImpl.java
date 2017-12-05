package com.atguigu.manager.service.impl;

import com.atguigu.manager.mapper.ProductDescMapper;
import com.atguigu.manager.pojo.Product;
import com.atguigu.manager.pojo.ProductDesc;
import com.atguigu.manager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    ProductDescMapper productDescMapper;

    public int insert(Product product, String productDesc) {
        int row = this.insert(product);
        ProductDesc pd = new ProductDesc();
        pd.setId(product.getId());
        pd.setProductDesc(productDesc);
        productDescMapper.insert(pd);
        return row;
    }

}
