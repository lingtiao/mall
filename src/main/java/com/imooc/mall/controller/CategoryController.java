package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
    @ApiOperation("后台添加商品分类目录")
    @PostMapping("/admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {

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

    @ApiOperation("后台更新商品分类目录")
    @PostMapping("/admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody UpdateCategoryReq updateCategoryReq) {
        //尝试获取当前登录用户
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        //如果获取不到，说明当前没有用户登录；就返回【用户未登录】的信息
        if (currentUser == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //校验当前用户是否是管理员用户
        boolean isAdminRole = userService.checkAdminRole(currentUser);

        if (isAdminRole) {//如果是管理用户；就去执行【更新目录分类】;
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq, category);
            categoryService.update(category);
            return ApiRestResponse.success();
        } else {//如果不是管理员用户；就返回【无管理员权限】的信息
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }
    }
}