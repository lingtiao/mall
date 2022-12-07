package com.imooc.mall.model.request;

/**
 * 描述：【增加目录分类】接口的参数类；
 */
public class AddCategoryReq {
    private String name;//目录名
    private Integer type;//目录级别
    private Integer parentId;//上一级目录的id
    private Integer orderNum;//同级目录的排序顺序


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}