package com.atguigu.manager.controller;


import com.atguigu.common.pojo.DataTableJSONResponse;
import com.atguigu.manager.pojo.Product;
import com.atguigu.manager.pojo.ProductDesc;
import com.atguigu.manager.service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/restful/page")
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // POST http://localhost:8180/restful/page/product

    /**
     * 实现商品添加
     * @param product
     * @param productdesc
     * @return
     */
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<Void> saveProduct(Product product, ProductDesc productdesc) {
        try {
            productService.insertSelective(product);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // http://localhost:8180/restful/page/product/ GET
    /**
     * 实现商品的查询
     * @param cid
     * @param aodata
     * @return
     */
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<DataTableJSONResponse> getProduct(@RequestParam(value = "cid", required = false) Long cid,
                                            @RequestParam(value = "aodata", required = false) String aodata) {
        DataTableJSONResponse dataTableJSONResponse = new DataTableJSONResponse();
        String sEcho = "0"; // 计算访问次数
        int iDisplayStart = 0; // 从第几条记录开始
        int iDisplayLength = 10; // 每页显示的记录数

        try {
            if (aodata != null) {
                JSONArray jsonArray = new JSONArray(aodata);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    Object name = jsonObject.get("name");
                    if ("sEcho".equals(name)) {
                        sEcho = jsonObject.get("value").toString();
                    } else if ("iDisplayStart".equals(name)) {
                        iDisplayStart = jsonObject.getInt("value");
                    } else if ("iDisplayLength".equals(name)) {
                        iDisplayLength = jsonObject.getInt("value");
                    }
                }
            }
            Product product = new Product();
            if (cid != null) {
                product.setCid(cid);
            }
            List<Product> aaData = this.productService.select(product);
            int count = this.productService.selectCount(product);
            if (count > iDisplayLength) {
                if ((count - iDisplayStart) > iDisplayLength) {
                    aaData = aaData.subList(iDisplayStart, iDisplayStart + iDisplayLength);
                } else {
                    aaData = aaData.subList(iDisplayStart, count);
                }
            }
            dataTableJSONResponse.setsEcho(sEcho);
            dataTableJSONResponse.setAaData(aaData);
            dataTableJSONResponse.setITotalDisplayRecords(count);
            dataTableJSONResponse.setITotalRecords(count);
            return ResponseEntity.status(HttpStatus.OK).body(dataTableJSONResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * http://localhost:8180/restful/page/product
     * 实现批量删除
     */
    @RequestMapping(value = "product", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@RequestParam("ids") String ids) {
        try {
            String[] split = ids.split(",");
            List<Object> keys = new ArrayList<>();
            for (String key : split) {
                keys.add(key);
            }
            this.productService.deleteByPrimaryKeys(keys);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }
}
