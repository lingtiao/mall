package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
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

    /**
     * 后台的，获取商品的列表
     * @param pageSize
     * @return
     */
    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    /**
     * 查看商品详情
     * @param id
     * @return
     */
    Product detail(Integer id);
}
