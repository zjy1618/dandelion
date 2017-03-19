package com.zjy.dandelion.service.core;


import java.util.List;
import java.util.Map;

import com.zjy.dandelion.entity.Product;
import com.zjy.dandelion.page.Pager;

/**
 * 产品服务
 * @author zhuangjy
 *
 */
public interface ProductService {

	List<Product> findByPager(Map<String, Object> map, Pager<Product> pager);

	boolean saveProduct(Product product);
	
	boolean updateProduct(Product product);
	
	Product findProductById(Long id);

	Product findProductByCode(String code);

	boolean deleteProduct(Product product, String realPath);

	/**
	 * 开售
	 * @param productCode 产品编码
	 * @return 成功:true
     */
	boolean onSellProduct(String productCode);

	/**
	 * 停售
	 * @param productCode 产品编码
	 * @return 成功:true
	 */
	boolean soldOutProduct(String productCode);

	/**
	 * 展厅是否存商品
	 * @param showRoomId 展厅id
	 * @return 存在true
     */
	boolean isExistByShowRoom(Long showRoomId);
}
