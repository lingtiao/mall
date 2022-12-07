package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 描述：商品分类Controller
 */
@Controller
public class CategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    /**
     * 增加目录分类
     *
     * @param session
     * @param addCategoryReq
     * @return
     */
    @PostMapping("/admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        //校验入参，如果为空就返回【参数不能为空】的信息
//        if (addCategoryReq.getName() == null ||
//                addCategoryReq.getOrderNum() == null ||
//                addCategoryReq.getParentId() == null ||
//                addCategoryReq.getType() == null) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME.NAME_NOT_NULL);
//        }

        //尝试获取当前登录用户
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        //如果获取不到，说明当前没有用户登录；就返回【用户未登录】的信息
        if (currentUser == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //校验当前用户是否是管理员用户
        boolean isAdminRole = userService.checkAdminRole(currentUser);

        if (isAdminRole) {//如果是管理用户；就去执行【增加目录分类】;
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        } else {//如果不是管理员用户；就返回【无管理员权限】的信息
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }
}