package com.zjy.dandelion.service.core;


import com.zjy.dandelion.entity.OrderDetail;

import java.util.List;
import java.util.Map;

/**
 * 订单明细服务
 * @author zhuangjy
 *
 */
public interface OrderDetailService {

	List<OrderDetail> findByMap(Map<String, Object> map);

	List<OrderDetail> findByOrderNumber(String orderNumber);

	boolean saveOrderDetail(OrderDetail orderDetail);
	
	boolean updateOrderDetail(OrderDetail orderDetail);

	OrderDetail findOrderDetailById(Long id);

	boolean deleteOrderDetail(String orderNumber, String productCode);
}
