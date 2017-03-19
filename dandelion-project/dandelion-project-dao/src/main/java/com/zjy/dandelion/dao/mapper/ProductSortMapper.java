package com.zjy.dandelion.dao.mapper;

import com.zjy.dandelion.dao.mapper.base.BaseMapper;
import com.zjy.dandelion.entity.ProductSort;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductSortMapper extends BaseMapper<ProductSort> {
	
	@ResultMap("DetailMap")
	ProductSort selectByPrimaryKey(Long id);

	@Select("select * from `product_sort` where name=#{name}")
	public ProductSort findProductSortByName(@Param("name") String name);
	
	List<ProductSort> findAll();
}
