package com.imooc.mall.model.dao;

import com.imooc.mall.model.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 根据订单号，获取订单详情
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(String orderNo);

    /**
     * 根据userId，查询订单
     * @param userId
     * @return
     */
    List<Order> selectOrderForCustomer(Integer userId);
}