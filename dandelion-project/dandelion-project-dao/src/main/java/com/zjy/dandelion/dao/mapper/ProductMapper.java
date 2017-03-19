package com.zjy.dandelion.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import com.zjy.dandelion.dao.mapper.base.BaseMapper;
import com.zjy.dandelion.entity.Product;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProductMapper extends BaseMapper<Product> {

	@ResultMap("DetailMap")
	Product selectByPrimaryKey(Long id);
	
	public List<Product> findByMap(Map<String, Object> map);
	
	public Integer countByMap(Map<String, Object> map);

	@Select("select * from `product` where productCode=#{productCode}")
	@ResultMap("DetailMap")
	public Product findProductByCode(@Param("productCode") String productCode);

	/**
	 * 修改状态
	 * @param productCode 产品编码
	 */
	@Update(" update `product` set status = #{status} ,modifyDate = now() where productCode=#{productCode} ")
	public int updateProductStatus(@Param("status") Product.Status status, @Param("productCode") String productCode);

	@Select("select count(1) from `product` where showRoom_id = #{showRoomId} ")
	public Integer countByShowRoom(@Param("showRoomId") Long showRoomId);
}
