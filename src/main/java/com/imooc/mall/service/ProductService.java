package com.imooc.mall.service;

import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;

public interface ProductService {
    /**
     * 添加商品
     * @param addProductReq
     */
    void add(AddProductReq addProductReq);

    /**
     * 更新商品
     * @param product
     */
    void update(Product product);

    /**
     * 删除商品
     * @param id
     */
    void delete(Integer id);

    /**
     * 批量上下架商品
     * @param ids
     * @param sellStatus
     */
    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);
}
