package com.imooc.mall.service;

import com.imooc.mall.model.request.AddCategoryReq;

public interface CategoryService {

    /**
     * 增加目录分类
     * @param addCategoryReq
     */
    void add(AddCategoryReq addCategoryReq);
}