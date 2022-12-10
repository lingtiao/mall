package com.imooc.mall.service.impl;

import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CartMapper;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Cart;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：购物车模块的Service实现类
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;


    /**
     * 根据前端要求，根据cart表和product表，获取并组织购物车列表数据
     * @return
     */
    @Override
    public List<CartVO> list(Integer userId) {
        List<CartVO> cartVOList = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOList.size(); i++) {
            CartVO cartVO =  cartVOList.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOList;
    }

    /**
     * 添加商品到购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        //调用工具方法，先看下【我们传的这个productId，在数据库中究竟有没有这个id的商品】；
        //然后，再看下商品是否是上架状态、商品库存是否足够；
        validProduct(productId, count);

        //然后，在看下购物车中，是否已经添加过了这个商品
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {//如果该商品之前，不在购物车里；那么，我们就创建一个cart记录，添加到cart表中；
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.CartIsSelected.CHECKED);//默认情况下，商品被添加到购物车后，就是选中状态
            cartMapper.insertSelective(cart);
        } else {//如果该商品之前，就在购物车里了；那么，我们就去更新购物车中的这个商品，主要是对商品数量的叠加
            int countNew = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(countNew);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            //至于商品是否设为选中，需要看具体的项目业务要求；
            // 我们这儿的策略，只要我们增加购物车的某个商品的数量了，就认为用户想买了，就把其设为选中状态;
            cartNew.setSelected(Constant.CartIsSelected.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }

        return this.list(userId);
    }

    /**
     * 工具方法；判断商品是否存在、商品是否是上架状态、商品库存是否足够；
     * @param productId
     * @param count
     */
    private void validProduct(Integer productId, Integer count) {
        //判断商品是否存在、商品是否是上架状态；如果不行，就抛出“商品不可售”的异常；
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || !product.getStatus().equals(Constant.SaleStatus.SALE)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存，如果库存不足，也不能把商品添加到购物车，抛出“商品库存不足异常；
        if (count > product.getStock()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NOT_ENOUGH);
        }
    }
}