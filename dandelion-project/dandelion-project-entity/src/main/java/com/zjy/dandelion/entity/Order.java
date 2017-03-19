package com.zjy.dandelion.entity;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单实体
 * Created by zhuangjy on 16/8/17.
 */
public class Order extends BaseEntity {

    private static final long serialVersionUID = 5616181976877555476L;


    /**
     * UNPAY 未付款
     * PAID 已付款
     */
    public enum Status{
        UNPAY,PAID
    }

    @Column
    private String orderNumber;//订单编号

    @Column
    private BigDecimal amount;  //订单金额

    @Column
    private Integer productAllCount;   //产品总数

    @Column
    private String mobile;  //手机号

    @Column
    private String userName;  //客户名

    @Column
    private String postCode;  //邮政编码

    @Column
    private String address;  //详细地址

    @Column
    private String note;  //留言

    @Column
    private Status status;

    private List<OrderDetail> orderDetails; //订单详情

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getProductAllCount() {
        return productAllCount;
    }

    public void setProductAllCount(Integer productAllCount) {
        this.productAllCount = productAllCount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusValue(){
        String statusValue = "";
        switch  (getStatus()){
            case UNPAY:
                statusValue = "未付款";
                break;
            case PAID:
                statusValue = "已付款";
                break;
            default:
                statusValue = "未知状态";
                break;
        }
        return statusValue;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
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
