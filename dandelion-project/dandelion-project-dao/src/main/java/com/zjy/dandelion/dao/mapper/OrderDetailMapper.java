package com.zjy.dandelion.dao.mapper;

import com.zjy.dandelion.dao.mapper.base.BaseMapper;
import com.zjy.dandelion.entity.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;
import java.util.Map;

public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

	@ResultMap("DetailMap")
	OrderDetail selectByPrimaryKey(Long id);
	
	public List<OrderDetail> findByMap(Map<String, Object> map);
	
	public Integer countByMap(Map<String, Object> map);

	public int deleteOrderDetail(@Param("orderNumber") String orderNumber, @Param("productCode")  String productCode);
}
