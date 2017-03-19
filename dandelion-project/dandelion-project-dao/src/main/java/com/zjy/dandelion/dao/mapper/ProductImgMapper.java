package com.zjy.dandelion.dao.mapper;

import com.zjy.dandelion.dao.mapper.base.BaseMapper;
import com.zjy.dandelion.entity.ProductImg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductImgMapper extends BaseMapper<ProductImg> {

	@ResultMap("DetailMap")
	ProductImg selectByPrimaryKey(Long id);

	public List<ProductImg> findByProductCode(@Param("productCode") String productCode);

	public ProductImg findSmallByProductCode(@Param("productCode") String productCode);

	@Delete("delete from `product_img` where productCode = #{productCode} ")
	public int deleteByProductCode(@Param("productCode") String productCode);
}
