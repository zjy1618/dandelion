package com.zjy.dandelion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Created by aone on 2015 上午10:17:56.
 * <p/>
 * Copyright © mizhuanglicai
 */
public class BaseController {
	protected Logger log=LoggerFactory.getLogger(BaseController.class);
	
    protected String STATUS_SUCCESS = "success";
    protected String STATUS_FAILD = "faild";

    @InitBinder
    // 此处的参数也可以是ServletRequestDataBinder类型
    public void initBinder(WebDataBinder binder) throws Exception {
        // 注册自定义的属性编辑器
        // 1、日期属性编辑器
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        // 表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
        binder.registerCustomEditor(Date.class, dateEditor);

    }
}
