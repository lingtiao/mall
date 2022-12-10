package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述：【商品模块】前台的Controller
 */
@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 查看商品详情
     * @param id
     * @return
     */
    @ApiOperation("商品详情")
    @PostMapping("/product/detail")
    @ResponseBody
    public ApiRestResponse detail(@RequestParam("id") Integer id) {
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

}