package com.zjy.dandelion.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;


/**
 * 订单明细
 * @author zhuangjy
 *
 */
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

	private static final long serialVersionUID = 6966748964927798105L;
	@Column
	private String orderNumber;//订单号

	@Column
	private Integer productCount;//产品数量

    @Column
	private String name;//产品名称

    @Column
   	private String productCode;//产品编码

    @Column
    private String productSortName;//分类名称

	@Column
	private String styleName; //款式名称

	@Column
	private BigDecimal unitAmount;//单价金额

	@Column
	private String unitName;//单位名称

    @Column
    private String description;//产品描述

	private List<ProductImg> productImgList;


	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductSortName() {
		return productSortName;
	}

	public void setProductSortName(String productSortName) {
		this.productSortName = productSortName;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
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

	public List<ProductImg> getProductImgList() {
		return productImgList;
	}

	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}

	public String getProductStyleInfo(){
		String productStyleInfo = getStyleName() + ": " + getUnitAmount() + " " + getUnitName();
		return productStyleInfo;
	}
}
