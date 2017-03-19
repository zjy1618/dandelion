package com.zjy.dandelion.exception;

/**
 * 业务异常类
 * 
 */
public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(BusinessExceptionCode beCode) {
		super(beCode.getDescription());
		code = beCode.getCode();
	}

	public BusinessException(BusinessExceptionCode beCode, Object... args) {
		super(String.format(beCode.getDescription(), args));
		code = beCode.getCode();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
