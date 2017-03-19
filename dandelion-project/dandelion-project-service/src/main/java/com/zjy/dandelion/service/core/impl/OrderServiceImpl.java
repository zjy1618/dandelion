package com.zjy.dandelion.service.core.impl;

import com.zjy.dandelion.dao.mapper.OrderMapper;
import com.zjy.dandelion.entity.Order;
import com.zjy.dandelion.entity.OrderDetail;
import com.zjy.dandelion.entity.Product;
import com.zjy.dandelion.entity.ProductStyle;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.request.OrderDetailRequest;
import com.zjy.dandelion.request.OrderRequest;
import com.zjy.dandelion.service.core.*;
import com.zjy.dandelion.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{

	@Resource
	private OrderMapper orderMapper;

	@Resource
	private OrderDetailService orderDetailService;

	@Resource
	private ProductService productService;

	@Resource
	private ProductStyleService productStyleService;

	@Override
	public boolean saveOrder(Order order) {
		order.setCreateDate(new Date());
		order.setModifyDate(new Date());
		return orderMapper.insert(order) > 0;
	}

	@Override
	public boolean updateOrder(Order order) {
		Order order_org = orderMapper.selectByPrimaryKey(order.getId());
		BeanUtils.copyProperties(order, order_org);
		order_org.setModifyDate(new Date());
		return orderMapper.updateByPrimaryKey(order_org) > 0;
	}


	@Override
	public List<Order> findByPager(Map<String, Object> map, Pager<Order> pager) {
		map.put("startNumber", pager.getStartNumber());
		map.put("pageSize",pager.getPageSize());
		List<Order> list = orderMapper.findByMap(map);
		List<Order> list_re = new ArrayList<>();
		for(Order order:list){
			if(order != null && StringUtils.isNotBlank(order.getOrderNumber())){
				order.setOrderDetails(orderDetailService.findByOrderNumber(order.getOrderNumber()));
			}
			list_re.add(order);
		}
		pager.setList(list_re);
		pager.setTotalCount(orderMapper.countByMap(map));
		return list_re;
	}

	@Override
	public Order findOrderById(Long id) {
		Order order = orderMapper.selectByPrimaryKey(id);
		if(order != null && StringUtils.isNotBlank(order.getOrderNumber())){
			order.setOrderDetails(orderDetailService.findByOrderNumber(order.getOrderNumber()));
		}
		return order;
	}

	@Override
	public Order findOrderByOrderNumber(String orderNumber) {
		Order order = orderMapper.findOrderByOrderNumber(orderNumber);
		if(order != null && StringUtils.isNotBlank(order.getOrderNumber())){
			order.setOrderDetails(orderDetailService.findByOrderNumber(order.getOrderNumber()));
		}
		return order;
	}

	@Override
	public Boolean deleteOrderByOrderNumber(String orderNumber) {
		Order order = orderMapper.findOrderByOrderNumber(orderNumber);
		if(order != null && StringUtils.isNotBlank(order.getOrderNumber())){
			orderDetailService.deleteOrderDetail(orderNumber, null);
		}
		orderMapper.deleteByPrimaryKey(order);
		return true;
	}

	@Override
	@Transactional
	public boolean createOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(IdGenerator.getOrderID());
		order.setStatus(Order.Status.UNPAY);
		order.setMobile(orderRequest.getMobile());
		order.setUserName(orderRequest.getUserName());
		order.setPostCode(orderRequest.getPostCode());
		order.setAddress(orderRequest.getAddress());
		order.setNote(orderRequest.getNote());

		BigDecimal amount = BigDecimal.ZERO;
		Integer productAllCount = 0;

		Long showRoomId = null;
		List<OrderDetailRequest> orderDetails = orderRequest.getOrderDetails();
		for(OrderDetailRequest orderDetailRequest:orderDetails){
			Product product = productService.findProductByCode(orderDetailRequest.getProductCode());
			if(product != null){
				ProductStyle productStyle = productStyleService.findProductStyleById(orderDetailRequest.getProductStyleId());
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrderNumber(order.getOrderNumber());
				orderDetail.setName(product.getName());
				orderDetail.setProductCode(product.getProductCode());
				orderDetail.setDescription(product.getDescription());
				orderDetail.setProductCount(orderDetailRequest.getProductCount());
				orderDetail.setProductSortName(product.getProductSort().getName());


				orderDetail.setStyleName(productStyle.getName());
				orderDetail.setUnitAmount(productStyle.getUnitAmount());
				orderDetail.setUnitName(productStyle.getUnitName());

				orderDetailService.saveOrderDetail(orderDetail);

				BigDecimal unitAmount = orderDetail.getUnitAmount();

				amount = amount.add(unitAmount.multiply(BigDecimal.valueOf(orderDetailRequest.getProductCount())));
				productAllCount += orderDetailRequest.getProductCount();
			}
		}

		order.setAmount(amount);
		order.setProductAllCount(productAllCount);

		this.saveOrder(order);

		return true;
	}

	@Override
	public boolean setPaid(Long id) {
		Order order_org = orderMapper.selectByPrimaryKey(id);
		order_org.setStatus(Order.Status.PAID);
		order_org.setModifyDate(new Date());
		return orderMapper.updateByPrimaryKey(order_org) > 0;
	}

	@Override
	public Map<String, Object> statOrder() {
		return orderMapper.statOrder();
	}


}
