package com.imooc.mall.model.dao;

import com.imooc.mall.model.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    /**
     * 根据订单号，获取订单详情
     * @param orderNo
     * @return
     */
    List<OrderItem> selectByOrderNo(String orderNo);
}