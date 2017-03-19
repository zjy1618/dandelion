package com.zjy.dandelion.service.core;


import com.zjy.dandelion.entity.ProductImg;

import java.util.List;

/**
 * 产品图片服务
 * @author zhuangjy
 *
 */
public interface ProductImgService {

	List<ProductImg> findByProductCode(String productCode);

	boolean saveProductImg(ProductImg productImg);
	
	boolean updateProductImg(ProductImg productImg);

	ProductImg findProductImgById(Long id);

	ProductImg findSmallByProductCode(String productCode);

	/**
	 * 删除该产品所有图片
	 * @param productCode
	 * @param realPath 产品图片的绝对路径（不包含文件名）
     * @return 删除个数
     */
	int deleteByProductCode(String productCode, String realPath);
}
