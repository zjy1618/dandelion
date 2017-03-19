package com.zjy.dandelion.request;

import java.io.Serializable;
import java.util.List;

/**
 * 订单生成请求实体
 * Created by zhuangjy on 16/8/18.
 */
public class OrderRequest implements Serializable{

    private static final long serialVersionUID = -8651144689634774898L;

    private String mobile;

    private String userName;  //客户名

    private String postCode;  //邮政编码

    private String address;  //详细地址

    private String note;  //留言


    private List<OrderDetailRequest> orderDetails;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public List<OrderDetailRequest> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailRequest> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
