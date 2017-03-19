package com.zjy.dandelion.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细请求实体
 * Created by zhuangjy on 16/8/18.
 */
public class OrderDetailRequest implements Serializable {

    private static final long serialVersionUID = -3262652235871313729L;

    private String productCode;//产品编码

    private Integer productCount;//产品数量

    private Long productStyleId;//款式ID

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Long getProductStyleId() {
        return productStyleId;
    }

    public void setProductStyleId(Long productStyleId) {
        this.productStyleId = productStyleId;
    }
}
