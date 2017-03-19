package com.zjy.dandelion.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;


/**
 * 产品
 * @author zhuangjy
 *
 */
public class Product extends BaseEntity {


	private static final long serialVersionUID = 574521535167410131L;

	/**
	 * CREATE
	 * ONSELL 在售
	 * SOLDOUT 停售
	 */
	public enum Status{
		CREATE,ONSELL,SOLDOUT;

	}
    @Column
	private String name;//产品名称

    @Column
   	private String productCode;//产品编码

    @Column
    private ProductSort productSort;//类别

    @Column
    private String description;//产品描述

    @Column
	private Status status;	//状态

	private List<ProductImg> productImgList;	//商品图片

	private List<ProductStyle> productStyleList;	//商品款式

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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getStatusValue() {
		String statusValue = "未匹配";
		switch (getStatus()) {
		case ONSELL:
			statusValue = "在售";
			break;
		case SOLDOUT:
			statusValue = "停售";
			break;
		case CREATE:
			statusValue = "创建状态";
			break;
		default :
			statusValue = "未知";
		}
		return statusValue;
	}

	public ProductSort getProductSort() {
		return productSort;
	}

	public void setProductSort(ProductSort productSort) {
		this.productSort = productSort;
	}


	public List<ProductImg> getProductImgList() {
		return productImgList;
	}

	public void setProductImgList(List<ProductImg> productImgList) {
		this.productImgList = productImgList;
	}

	public List<ProductStyle> getProductStyleList() {
		return productStyleList;
	}

	public void setProductStyleList(List<ProductStyle> productStyleList) {
		this.productStyleList = productStyleList;
	}
}
