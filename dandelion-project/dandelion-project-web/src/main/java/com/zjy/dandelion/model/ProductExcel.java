package com.zjy.dandelion.model;


import java.util.List;

public class ProductExcel
{

	private String productName;//产品名称

	private String daYang;//大样

	private String diaoKa;//吊卡

	private String mianLiao;//面料

	private String commpany;//公司名称

	private String showName ;//展厅名字

	public ProductExcel()
	{
		super();
	}

	public ProductExcel(List<String> values) {
		this.productName = values.get(0);
		this.daYang = values.get(1);
		this.diaoKa = values.get(2);
		this.mianLiao = values.get(3);
		this.commpany = values.get(4);
		this.showName = values.get(5);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDaYang() {
		return daYang;
	}

	public void setDaYang(String daYang) {
		this.daYang = daYang;
	}

	public String getDiaoKa() {
		return diaoKa;
	}

	public void setDiaoKa(String diaoKa) {
		this.diaoKa = diaoKa;
	}

	public String getMianLiao() {
		return mianLiao;
	}

	public void setMianLiao(String mianLiao) {
		this.mianLiao = mianLiao;
	}

	public String getCommpany() {
		return commpany;
	}

	public void setCommpany(String commpany) {
		this.commpany = commpany;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
}
