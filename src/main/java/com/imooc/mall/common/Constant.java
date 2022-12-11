package com.imooc.mall.common;

import com.google.common.collect.Sets;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
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

    /**
     * 枚举类，来说明订单状态
     */
    public enum OrderStatusEnum {
        CANCELED(0, "用户已取消"),
        NOT_PAY(10, "未付款"),
        PAID(20,"已付款"),
        DELIVERED(30,"已发货"),
        FINISHED(40,"交易完成");

        private int code;
        private String value;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEnum codeOf(int code) {
            for (OrderStatusEnum orderStatusEnum:values() ) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ENUM);

        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
