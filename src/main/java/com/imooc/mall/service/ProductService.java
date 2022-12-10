package com.imooc.mall.service;

import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;

public interface ProductService {
    void add(AddProductReq addProductReq);

    /**
     * 更新商品
     * @param product
     */
    void update(Product product);
}
