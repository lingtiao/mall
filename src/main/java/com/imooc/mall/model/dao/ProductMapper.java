package com.imooc.mall.model.dao;

import com.imooc.mall.model.pojo.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 根据商品name，查询商品
     * @param name
     */
    Product selectByName(String name);
}