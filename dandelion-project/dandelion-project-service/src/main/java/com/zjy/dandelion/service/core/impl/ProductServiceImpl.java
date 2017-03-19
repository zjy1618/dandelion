package com.zjy.dandelion.service.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zjy.dandelion.service.core.ProductImgService;
import com.zjy.dandelion.service.core.ProductStyleService;
import com.zjy.dandelion.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.zjy.dandelion.dao.mapper.ProductMapper;
import com.zjy.dandelion.entity.Product;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.service.core.ProductService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService{

	@Resource
	private ProductMapper productMapper;

	@Resource
	private ProductImgService productImgService;

	@Resource
	private ProductStyleService productStyleService;

	@Override
	public boolean saveProduct(Product product) {
		product.setCreateDate(new Date());
		product.setModifyDate(new Date());
		if(StringUtils.isBlank(product.getProductCode())){
			product.setProductCode("P" + IdGenerator.getProductID());
		}
		product.setStatus(Product.Status.CREATE);
		return productMapper.insert(product) > 0;
	}

	@Override
	public boolean updateProduct(Product product) {
		Product product_org = productMapper.selectByPrimaryKey(product.getId());
		BeanUtils.copyProperties(product, product_org);
		product_org.setModifyDate(new Date());
		return productMapper.updateByPrimaryKey(product_org) > 0;
	}


	@Override
	public List<Product> findByPager(Map<String, Object> map, Pager<Product> pager) {
		if(pager != null){
			map.put("startNumber", pager.getStartNumber());
			map.put("pageSize",pager.getPageSize());
		}
		List<Product> list = productMapper.findByMap(map);
		List<Product> list_result = new ArrayList<>();
		for(Product product:list){
			if(StringUtils.isNotBlank(product.getProductCode())){
				product.setProductImgList(productImgService.findByProductCode(product.getProductCode()));
				product.setProductStyleList(productStyleService.findByProductCode(product.getProductCode()));
			}
			list_result.add(product);
		}
		if(pager != null){
			pager.setList(list_result);
			pager.setTotalCount(productMapper.countByMap(map));
		}
		return list_result;
	}

	@Override
	public Product findProductById(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		if(product != null && StringUtils.isNotBlank(product.getProductCode())){
			product.setProductImgList(productImgService.findByProductCode(product.getProductCode()));
			product.setProductStyleList(productStyleService.findByProductCode(product.getProductCode()));
		}
		return product;
	}

	@Override
	public Product findProductByCode(String code) {
		Product product = productMapper.findProductByCode(code);
		if(product != null && StringUtils.isNotBlank(product.getProductCode())){
			product.setProductImgList(productImgService.findByProductCode(product.getProductCode()));
			product.setProductStyleList(productStyleService.findByProductCode(product.getProductCode()));
		}
		return product;
	}

	@Override
	@Transactional
	public boolean deleteProduct(Product product, String realPath) {
		int r = productMapper.deleteByPrimaryKey(product);
		if(r > 0){
			productImgService.deleteByProductCode(product.getProductCode(), realPath);
			productStyleService.deleteByProductCode(product.getProductCode());
			return true;
		}
		return false;
	}

	@Override
	public boolean onSellProduct(String productCode) {
		return productMapper.updateProductStatus(Product.Status.ONSELL, productCode) > 0;
	}

	@Override
	public boolean soldOutProduct(String productCode) {
		return productMapper.updateProductStatus(Product.Status.SOLDOUT, productCode) > 0;
	}

	@Override
	public boolean isExistByShowRoom(Long showRoomId) {
		return productMapper.countByShowRoom(showRoomId) > 0;
	}
}
