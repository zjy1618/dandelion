package com.zjy.dandelion.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OperateLog {

	private static Logger log=LoggerFactory.getLogger(OperateLog.class);
	
	/**
	 * 记录操作日志
	 * @param operator 操作人
	 * @param operate  操作
	 * @param remark   明细
	 */
	public static void logging(String operator,String operate,String remark){
		log.info("用户：{}，操作：{}，明细：{}",operator,operate,remark);
	}
}
