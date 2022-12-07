package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.pojo.User;

public interface UserService {

    /**
     * 获取用户信息
     * @return
     */
    User getUser();

    /**
     * 注册方法
     * @param userName
     * @param password
     * @throws ImoocMallException
     */
    void register(String userName,String password) throws ImoocMallException;

    /**
     * 登录方法
     * @param userName
     * @param password
     * @return
     * @throws ImoocMallException
     */
    User login(String userName, String password) throws ImoocMallException;

    /**
     * 更新个性签名
     * @param user
     */
    void updateInformation(User user) throws ImoocMallException;

    /**
     * 判断当前用户是否为管理员身份
     * @param user
     * @return
     */
    boolean checkAdminRole(User user);
}
