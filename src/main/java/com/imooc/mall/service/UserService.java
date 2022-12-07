package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.pojo.User;

public interface UserService {

    /**
     * 获取用户信息
     * @return
     */
    User getUser();

    void register(String userName,String password) throws ImoocMallException;
}
