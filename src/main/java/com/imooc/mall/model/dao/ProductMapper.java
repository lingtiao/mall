package com.imooc.mall.model.dao;

import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.query.ProductListQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    /**
     * 根据出入的【多个商品id】和【商品状态】，实现批量上下架功能；
     * @param ids
     * @param sellStatus
     * @return
     */
    int batchUpdateSellStatus(@Param("ids") Integer[] ids, @Param("sellStatus") Integer sellStatus);

    /**
     * 后台查询商品列表
     * @return
     */
    List<Product> selectListForAdmin();

    /**
     * 根据条件，查询商品
     * @param productListQuery
     * @return
     */

    List<Product> selectList(@org.apache.ibatis.annotations.Param("query")ProductListQuery productListQuery);
}