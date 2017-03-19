package com.zjy.dandelion.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 产品款式
 * Created by zhuangjy on 16/8/15.
 */
@Table(name = "product_style")
public class ProductStyle extends BaseEntity {


    private static final long serialVersionUID = 8740839702082039010L;


    @Column
    private String productCode;//产品编码

    @Column
    private String name; //款式名称

    @Column
    private BigDecimal unitAmount;//单价金额

    @Column
    private String unitName;//单位名称

    public ProductStyle() {
    }

    public ProductStyle(String productCode, String name, BigDecimal unitAmount, String unitName) {
        this.productCode = productCode;
        this.name = name;
        this.unitAmount = unitAmount;
        this.unitName = unitName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getProductStyleInfo(){
        String productStyleInfo = getName() + ": " + getUnitAmount() + " " + getUnitName();
        return productStyleInfo;
    }
}
