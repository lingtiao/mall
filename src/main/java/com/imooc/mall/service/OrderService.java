package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.OrderVO;

public interface OrderService {

    /**
     * 创建订单
     * @param createOrderReq
     * @return
     */
    String create(CreateOrderReq createOrderReq);

    /**
     * 根据订单号，获取订单详情
     * @param orderNo
     * @return
     */
    OrderVO detail(String orderNo);

    /**
     * 前台的，获取某用户的订单列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo listForCustomer(Integer pageNum, Integer pageSize);
}
