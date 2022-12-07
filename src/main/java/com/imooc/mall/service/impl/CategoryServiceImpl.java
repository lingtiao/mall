package com.imooc.mall.service.impl;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：分类目录CategoryService接口的实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 增加目录分类
     * @param addCategoryReq
     */
    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        //根据目录名去查询，数据库中是否已有这个名字的目录；
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        //如果查到了，说明【我们要增加了这个目录的，目录名】在数据库中已经有叫这个名字的目录了；抛出【不允许重名的业务异常】
        if (categoryOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        //调用方法，插入数据
        int count = categoryMapper.insertSelective(category);
        if (count == 0) {//如果count=0，表示插入没有成功；则抛出【新增失败】的业务异常
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }
    }
}