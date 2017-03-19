package com.zjy.dandelion.utils;

import java.util.Date;
import java.util.UUID;

/**
 * ID生成器，用于生成唯一ID，主要用于订单ID生成
 * Created by zhuangjy on 16/8/17.
 */
public class IdGenerator {

    public static String getOrderID() {
        final String id = DateTimeUtil.getDate(new Date(), "yyMMdd");
        final String code = String.format("%010d",
                Math.abs(getUUID().hashCode()));
        return id + code;
    }

    public static String getProductID() {
        final String id = DateTimeUtil.getDate(new Date(), "yyMMdd");
        final String code = String.format("%010d",
                Math.abs(getUUID().hashCode()));
        return id + code;
    }

    public static String getUUID() {
        final String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13)
                + uuid.substring(14, 18) + uuid.substring(19, 23)
                + uuid.substring(24);
    }
}
