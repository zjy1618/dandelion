package com.zjy.dandelion.service.core;


import com.zjy.dandelion.entity.Order;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.request.OrderRequest;

import java.util.List;
import java.util.Map;

/**
 * 订单服务
 * @author zhuangjy
 *
 */
public interface OrderService {

	List<Order> findByPager(Map<String, Object> map, Pager<Order> pager);

	boolean saveOrder(Order order);
	
	boolean updateOrder(Order order);
	
	Order findOrderById(Long id);

	Order findOrderByOrderNumber(String orderNumber);

	Boolean deleteOrderByOrderNumber(String orderNumber);

	/**
	 * 生成订单
	 * @param orderRequest 订单参数
     */
	boolean createOrder(OrderRequest orderRequest);

	/**
	 * 设置为付款成功
	 * @param id
	 * @return
     */
	boolean setPaid(Long id);

	/**
	 * 统计订单信息
	 * @return
     */
	Map<String, Object> statOrder();
}
