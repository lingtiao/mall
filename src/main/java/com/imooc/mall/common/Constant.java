package com.imooc.mall.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Constant {
    public static final String SALT = "uybY687]TBC9j,[0hkVBHy76";//盐值
    public static final String IMOOC_MALL_USER="imooc_mal1_user";//登录用户信息保存在Session中的，key值；
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    /**
     * 商品状态
     */
    public interface SaleStatus{
        int NOT_SALE = 0;//商品下架状态
        int SALE = 1;//商品上架状态
    }

    /**
     *
     */
    public interface CartIsSelected{
        int UN_CHECKED=0;//末选中
        int CHECKED=1;//选中
    }
}
