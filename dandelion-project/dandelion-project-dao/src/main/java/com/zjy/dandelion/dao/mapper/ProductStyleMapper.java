package com.zjy.dandelion.dao.mapper;

import com.zjy.dandelion.dao.mapper.base.BaseMapper;
import com.zjy.dandelion.entity.ProductStyle;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;

public interface ProductStyleMapper extends BaseMapper<ProductStyle> {

	@ResultMap("DetailMap")
	ProductStyle selectByPrimaryKey(Long id);

	public List<ProductStyle> findByProductCode(@Param("productCode") String productCode);

	@Delete("delete from `product_style` where productCode = #{productCode} ")
	public int deleteByProductCode(@Param("productCode") String productCode);

	@Delete("delete from `product_style` where id = #{id} ")
	public int deleteById(@Param("id") Long id);
}
