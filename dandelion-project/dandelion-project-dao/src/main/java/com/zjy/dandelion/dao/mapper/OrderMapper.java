package com.zjy.dandelion.dao.mapper;

import com.zjy.dandelion.dao.mapper.base.BaseMapper;
import com.zjy.dandelion.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {

	@ResultMap("DetailMap")
	Order selectByPrimaryKey(Long id);
	
	public List<Order> findByMap(Map<String, Object> map);
	
	public Integer countByMap(Map<String, Object> map);

	@Select("select * from `order` where orderNumber=#{orderNumber}")
	@ResultMap("DetailMap")
	public Order findOrderByOrderNumber(@Param("orderNumber") String orderNumber);

	Map<String, Object> statOrder();
}
