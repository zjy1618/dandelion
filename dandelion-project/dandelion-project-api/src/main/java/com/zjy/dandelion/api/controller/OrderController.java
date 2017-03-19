package com.zjy.dandelion.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zjy.dandelion.entity.Order;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.request.OrderDetailRequest;
import com.zjy.dandelion.request.OrderRequest;
import com.zjy.dandelion.service.core.OrderService;
import com.zjy.dandelion.utils.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Value("#{settings['dev.mode']}")
    private boolean devMode;

    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject list(int pageNumber, int pageSize, String mobile, String callback) {

        Pager<Order> pager = new Pager<>();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);

        Map<String, Object> param_map = new HashMap<>();

        if (StringUtils.isNotBlank(mobile)) {
            param_map.put("mobile", mobile);
        } else {
            return new JSONPObject(callback,"手机号为空");
        }

        orderService.findByPager(param_map, pager);

        return new JSONPObject(callback,pager);
    }

    /**
     * 新增订单
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject add(String orderJson,String callback) {
        HttpResponse response = new HttpResponse();

        OrderRequest orderRequest = new OrderRequest();

        JSONObject orderJSONObject= JSONObject.parseObject(orderJson);
        String mobile = orderJSONObject.getString("mobile");
        String userName = orderJSONObject.getString("userName");
        String postCode = orderJSONObject.getString("postCode");
        String address = orderJSONObject.getString("address");
        String note = orderJSONObject.getString("note");

        String products = orderJSONObject.getString("products");
        List<HashMap> list = JSON.parseArray(products, HashMap.class);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();

        for(int i=0;i<list.size();i++){

            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
            orderDetailRequest.setProductCode(list.get(i).get("productCode").toString());
            orderDetailRequest.setProductCount(Integer.valueOf(list.get(i).get("buyCount").toString()));
            orderDetailRequest.setProductStyleId(Long.valueOf(list.get(i).get("styleId").toString()));

            orderDetails.add(orderDetailRequest);
        }

        orderRequest.setMobile(mobile);
        orderRequest.setUserName(userName);
        orderRequest.setPostCode(postCode);
        orderRequest.setAddress(address);
        orderRequest.setNote(note);

        orderRequest.setOrderDetails(orderDetails);
        orderService.createOrder(orderRequest);

        response.setCode(HttpResponse.CODE_OK);
        response.setStatus(HttpResponse.STATUS_SUCCESS);
        response.setMessage("新增订单成功");

        return new JSONPObject(callback,response);
    }

    public static void main(String[] args) throws IOException {
        String orderJson = "{\n" +
                "            \"products\":[\n" +
                "                {\"productCode\":\"P1608181621494615\", \"styleId\":4, \"buyCount\":4},\n" +
                "                {\"productCode\":\"P1608252068620649\", \"styleId\":4, \"buyCount\":4}\n" +
                "            ],\n" +
                "            \"mobile\":\"1851006580\",\n" +
                "            \"acceptNumber\":\"123456\"\n" +
                "        }";

        OrderRequest orderRequest = new OrderRequest();

        JSONObject orderJSONObject= JSONObject.parseObject(orderJson);
        String mobile = orderJSONObject.getString("mobile");
        String userName = orderJSONObject.getString("userName");
        String postCode = orderJSONObject.getString("postCode");
        String address = orderJSONObject.getString("address");
        String note = orderJSONObject.getString("note");

        String products = orderJSONObject.getString("products");
        List<HashMap> list = JSON.parseArray(products, HashMap.class);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();

        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).get("productCode"));

            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
            orderDetailRequest.setProductCode(list.get(i).get("productCode").toString());
            orderDetailRequest.setProductCount(Integer.valueOf(list.get(i).get("buyCount").toString()));
            orderDetailRequest.setProductStyleId(Long.valueOf(list.get(i).get("styleId").toString()));

            orderDetails.add(orderDetailRequest);
        }

        orderRequest.setMobile(mobile);
        orderRequest.setUserName(userName);
        orderRequest.setPostCode(postCode);
        orderRequest.setAddress(address);
        orderRequest.setNote(note);
        orderRequest.setOrderDetails(orderDetails);

    }

}
