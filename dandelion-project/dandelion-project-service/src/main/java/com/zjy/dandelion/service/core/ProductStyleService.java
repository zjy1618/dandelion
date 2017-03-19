package com.zjy.dandelion.service.core;


import com.zjy.dandelion.entity.ProductStyle;

import java.util.List;

/**
 * 产品款式服务
 * @author zhuangjy
 *
 */
public interface ProductStyleService {

	List<ProductStyle> findByProductCode(String productCode);

	boolean saveProductStyle(ProductStyle productStyle);
	
	boolean updateProductStyle(ProductStyle productStyle);

	ProductStyle findProductStyleById(Long id);

	/**
	 * 删除该产品所有款式
	 * @param productCode
     * @return 删除个数
     */
	int deleteByProductCode(String productCode);

	boolean deleteById(Long id);
}
