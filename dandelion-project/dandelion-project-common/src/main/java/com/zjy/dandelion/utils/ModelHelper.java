package com.zjy.dandelion.utils;

import com.zjy.dandelion.exception.BusinessExceptionCode;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * Model帮助类，用于往Model注入通用信息
 * 
 * 
 * 
 *
 * Created by chars on 2015 下午4:48:57.
 *
 * Copyright © mizhuanglicai
 */
public class ModelHelper {

	private static final String ERROR_MSG = "errorMsg";
	private static final String STATUS = "status";

	/**
	 * 往Model注入异常警告信息
	 * 
	 * @param result
	 * @param ex
	 *            异常
	 */
	public static void addValidateInfoForMap(Map<String, Object> result, BusinessExceptionCode ex) {
		result.put(ERROR_MSG, ex.getDescription());
		result.put(STATUS, ex.getCode());
	}

	/**
	 * 往Model注入异常警告信息
	 * 
	 * @param model
	 * @param ex
	 *            异常
	 */
	public static void addValidateInfo(Model model, BusinessExceptionCode ex) {
		model.addAttribute(ERROR_MSG, ex.getDescription());
		model.addAttribute(STATUS, ex.getCode());
	}

	/**
	 * 往Model注入异常警告信息
	 * 
	 * @param model
	 *            异常
	 */
	public static void addValidateInfo(Model model, String msg, int code) {
		model.addAttribute(ERROR_MSG, msg);
		model.addAttribute(STATUS, code);
	}

	/**
	 * 
	 * 往Model注入异常警告信息
	 * 
	 * @param model
	 * @param ex
	 * @param args
	 *            其他参数
	 */
	public static void addValidateInfo(Model model, BusinessExceptionCode ex, Object... args) {
		model.addAttribute(ERROR_MSG, String.format(ex.getDescription(), args));
		model.addAttribute(STATUS, ex.getCode());
	}

}
