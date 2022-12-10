package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：【购物车】模块的Controller
 */

@RestController
//@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 购物车模块：购物车列表
     * @return
     */
    @ApiOperation("购物车列表")
    @GetMapping("/list")
    public ApiRestResponse list() {
        List<CartVO> cartVOList = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartVOList);
    }

    /**
     * 购物车模块：添加商品到购物车；
     * @param productId
     * @param count
     * @return
     */
    @ApiOperation("添加商品到购物车")
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam("productId") Integer productId, @RequestParam("count") Integer count) {
        List<CartVO> cartVOList = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }
}