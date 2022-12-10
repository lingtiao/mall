package com.imooc.mall.service;

import com.imooc.mall.model.vo.CartVO;

import java.util.List;

public interface CartService {
    /**
     * 根据前端要求，根据cart表和product表，获取并组织购物车列表数据
     * @return
     */
    List<CartVO> list(Integer userId);

    /**
     * 添加商品到购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    List<CartVO> add(Integer userId, Integer productId, Integer count);

    /**
     * 更新购物车中，某商品的数量；
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    List<CartVO> update(Integer userId, Integer productId, Integer count);
}
