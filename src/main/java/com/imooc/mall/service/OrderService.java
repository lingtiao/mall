package com.imooc.mall.service;

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
}
