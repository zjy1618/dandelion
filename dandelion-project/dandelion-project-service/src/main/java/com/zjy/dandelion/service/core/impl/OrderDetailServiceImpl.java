package com.zjy.dandelion.service.core.impl;

import com.zjy.dandelion.dao.mapper.OrderDetailMapper;
import com.zjy.dandelion.entity.OrderDetail;
import com.zjy.dandelion.service.core.OrderDetailService;
import com.zjy.dandelion.service.core.ProductImgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhuangjy on 16/8/18.
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private ProductImgService productImgService;

    @Override
    public List<OrderDetail> findByMap(Map<String, Object> map) {
        return orderDetailMapper.findByMap(map);
    }

    @Override
    public List<OrderDetail> findByOrderNumber(String orderNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNumber", orderNumber);

        List<OrderDetail> list = orderDetailMapper.findByMap(map);
        List<OrderDetail> list_ro = new ArrayList<>();
        for(OrderDetail orderDetail:list){
            orderDetail.setProductImgList(productImgService.findByProductCode(orderDetail.getProductCode()));
            list_ro.add(orderDetail);
        }
        return list_ro;
    }


    @Override
    public boolean saveOrderDetail(OrderDetail orderDetail) {
        orderDetail.setCreateDate(new Date());
        orderDetail.setModifyDate(new Date());
        return orderDetailMapper.insert(orderDetail) > 0;
    }

    @Override
    public boolean updateOrderDetail(OrderDetail orderDetail) {
        orderDetail.setModifyDate(new Date());
        return orderDetailMapper.updateByPrimaryKey(orderDetail) > 0;
    }

    @Override
    public OrderDetail findOrderDetailById(Long id) {
        return orderDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean deleteOrderDetail(String orderNumber, String productCode) {
        return orderDetailMapper.deleteOrderDetail(orderNumber, productCode) > 0;
    }
}
