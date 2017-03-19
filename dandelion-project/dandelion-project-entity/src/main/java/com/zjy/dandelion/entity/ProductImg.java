package com.zjy.dandelion.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 产品图片
 * Created by zhuangjy on 16/8/15.
 */
@Table(name = "product_img")
public class ProductImg extends BaseEntity {

    private static final long serialVersionUID = -5183651871229197862L;

    /**
     * SMALL 缩略图
     * FULL 原图
     */
    public enum Type {
        SMALL,FULL
    }

    @Column
    private String productCode;//产品编码

    @Column
    private String url;//相对路径

    @Column
    private Type type;

    @Column
    private String note;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
