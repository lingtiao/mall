package com.imooc.mall.service.impl;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import com.imooc.mall.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(4);
    }

    /**
     * 注册方法
     * @param userName
     * @param password
     */
    @Override
    public void register(String userName, String password) throws ImoocMallException{
        User result = userMapper.selectByName(userName);

        if (result != null) { //如果result不为null，这说明已经有这个用户了；此时，就可以直接向上抛异常；
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        //如果上面没有抛异常，那么说明用户名没有重名，那么我们就可以把这个注册信息写到数据库中
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5String(password));
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }
}
