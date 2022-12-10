package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.ProductMapper;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：商品Service实现类;
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    /**
     * 新增商品
     * @param addProductReq
     */
    @Override
    public void add(AddProductReq addProductReq) {
        //因为，我们是通过Product这个类，和数据库打交道的;
        //所以，我们把addProductReq的内容，复制成一个product;
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);

        //首先，我们要看下，商品时候重名
        Product productOld = productMapper.selectByName(product.getName());
        //如果查出来了，表示数据库中已经有叫这个名字的商品了；那么我们就抛出一个名字已存在的异常
        if (productOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //如果上面没问题，就去调用方法，向数据库中插入数据；
        int count = productMapper.insertSelective(product);
        //如果count=0，表示插入失败了；我们就抛出一个新增失败的异常；
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }

    /**
     * 更新商品
     * @param product
     */
    @Override
    public void update(Product product) {
        //先根据商品名，去数据库中查，看是否有叫这个名字的商品数据
        Product productOld = productMapper.selectByName(product.getName());
        //如果上面查到了
        //而且【上面查到的商品的id】和【我们前台穿过来的商品id不一样】，那么不允许更新，抛出不允许重名异常；
        // 很显然，这一条也是为了防止商品名重名；
        if (productOld != null && productOld.getId() != product.getId()) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //然后调用Dao层的更新方法，去更新数据
        int count = productMapper.updateByPrimaryKeySelective(product);
        //如果更新失败，就抛出更新失败异常；
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 删除商品
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //先查查，看表里是否有这条数据
        Product productOld = productMapper.selectByPrimaryKey(id);
        //如果查不到数据，就抛出删除失败异常；（很容理解，都查不到这条数据，当然无法删除了）
        if (productOld == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_ERROR);
        }
        //如果一切OK，就调用【mybatis-generator帮我们生成的】删除方法去删除；
        int count = productMapper.deleteByPrimaryKey(id);
        //如果没有删除成功，就抛出删除失败异常；
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_ERROR);
        }
    }

    /**
     * 批量上下架商品
     * @param ids
     * @param sellStatus
     */
    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }

    /**
     * 后台的，获取商品的列表
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        //设置分页的：当前页，每一页的记录数；
        PageHelper.startPage(pageNum, pageSize);
        //调用Dao层的方法，去查询
        List<Product> productList = productMapper.selectListForAdmin();
        //得到PageInfo对象
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }

    /**
     * 根据id，查询商品
     * @param id
     * @return
     */
    @Override
    public Product detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }
}