package com.imooc.mall.service;

import com.imooc.mall.model.request.CreateOrderReq;

public interface OrderService {

    /**
     * 创建订单
     * @param createOrderReq
     * @return
     */
    String create(CreateOrderReq createOrderReq);
}
