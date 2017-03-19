package com.zjy.dandelion.service.core.impl;

import com.zjy.dandelion.dao.mapper.ProductSortMapper;
import com.zjy.dandelion.entity.ProductSort;
import com.zjy.dandelion.service.core.ProductSortService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProductSortServiceImpl implements ProductSortService{

	@Resource
	private ProductSortMapper productSortMapper;
	
	@Override
	public List<ProductSort> findAll() {
		return productSortMapper.findAll();
	}

	@Override
	public boolean saveProductSort(ProductSort productSort) {
		productSort.setCreateDate(new Date());
		productSort.setModifyDate(new Date());
		return productSortMapper.insert(productSort) > 0;
	}

	@Override
	public boolean updateProductSort(ProductSort productSort) {
		ProductSort productSort_org = productSortMapper.selectByPrimaryKey(productSort.getId());
		BeanUtils.copyProperties(productSort, productSort_org);
		productSort_org.setModifyDate(new Date());
		return productSortMapper.updateByPrimaryKey(productSort_org) > 0;
	}

	@Override
	public ProductSort findProductSortById(Long id) {
		return productSortMapper.selectByPrimaryKey(id);
	}

	@Override
	public ProductSort findProductSortByName(String name) {
		return productSortMapper.findProductSortByName(name);
	}

}
