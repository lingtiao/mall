package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.CreateOrderReq;
import com.imooc.mall.model.vo.OrderVO;
import com.imooc.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 描述：订单Controller
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 【前台：创建订单】接口；
     * @param createOrderReq
     * @return
     */
    @ApiOperation("创建订单")
    @PostMapping("/order/create")
    public ApiRestResponse createOrder(@Valid @RequestBody CreateOrderReq createOrderReq) {
        String orderNum = orderService.create(createOrderReq);
        return ApiRestResponse.success(orderNum);
    }

    /**
     * 【前台：订单详情】接口；
     * @param orderNo
     * @return
     */
    @ApiOperation("订单详情")
    @GetMapping("/order/detail")
    public ApiRestResponse detail(@RequestParam("orderNo") String orderNo) {
        OrderVO orderVO = orderService.detail(orderNo);
        return ApiRestResponse.success(orderVO);
    }
}