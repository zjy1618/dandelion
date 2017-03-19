package com.zjy.dandelion.service.core;


import com.zjy.dandelion.entity.ProductSort;

import java.util.List;

/**
 * 展厅服务
 * @author zhuangjy
 *
 */
public interface ProductSortService {

	List<ProductSort> findAll();

	boolean saveProductSort(ProductSort productSort);
	
	boolean updateProductSort(ProductSort productSort);
	
	ProductSort findProductSortById(Long id);

	ProductSort findProductSortByName(String name);
}
