package com.zjy.dandelion.exception;

/**
 * 
 */
public enum BusinessExceptionCode {
	EXCEPTION_0(0, "OK"),
	
	EXCEPTION_1(1, "%s"),
	
	
	EXCEPTION_1000(1000, "未知错误"),

	EXCEPTION_1001(1001, "请输入11位手机号码"),
	EXCEPTION_1002(1002, "请输入正确的11位手机号码"),
	EXCEPTION_1003(1003, "请输入手机号码"),
	EXCEPTION_1004(1004, "该号码已经被注册"),
	EXCEPTION_1005(1005, "该手机号码还未注册"),

	

	EXCEPTION_1010(1010, "请输入6～16位长度的密码"), 
	EXCEPTION_1011(1011, "请输登录密码"),
	EXCEPTION_1012(1012, "请输新登录密码"),
	EXCEPTION_1013(1013, "新密码不能与当前密码相同"),
	EXCEPTION_1014(1014, "手机号或者密码不正确"),
	EXCEPTION_1015(1015, "请输入手机号和密码"),
	EXCEPTION_1016(1016, "当前密码错误"),
	EXCEPTION_1017(1017, "openid不能为空"),
	EXCEPTION_1018(1018, "账户不存在，请前往注册页面注册"),
	EXCEPTION_1019(1019, "该用户不存在"),
	

	

	EXCEPTION_1021(1021, "请输入验证码"),
	EXCEPTION_1022(1022, "验证码错误，请重新输入"),
//	EXCEPTION_1023(1023, "验证码超时，请重新获取"),
	EXCEPTION_1024(1024, "请输入校验码"),
	EXCEPTION_1025(1025, "校验码错误，请重新输入"),
	EXCEPTION_1026(1026, "验证码超时，请重新获取"),


	
	EXCEPTION_10001(10001,"收货人姓名不能为空"),
	EXCEPTION_10002(10002,"收货人手机不能为空"),
	EXCEPTION_10003(10003,"收货人地址不能为空"),
	EXCEPTION_10004(10004,"姓名不能为空"),
	EXCEPTION_10005(10005,"手机不能为空"),
	
	
	
	EXCEPTION_10100(10100,"还没设置交易密码"),
	EXCEPTION_10101(10101,"交易密码不能为空"),
	EXCEPTION_10102(10102, "请输新交易密码"),
	EXCEPTION_10103(10103, "新密码不能和原密码相同"),
	EXCEPTION_10104(10104, "交易密码不正确,请重新输入"),
	EXCEPTION_10108(10108, "原交易密码错误"),
	EXCEPTION_10109(10109, "请输原交易密码"),

	EXCEPTION_20001(20001,"密码错误！"),
	EXCEPTION_20002(20002,"没有此用户！"),
	EXCEPTION_20003(20003,"权限不足！"),
	EXCEPTION_20004(20004,"联系管理员初始化密码!"),
	EXCEPTION_20005(20005,"登录遇到问题!"),


	EXCEPTION_21000(21000, "账号过期，请重新登录"),


    EXCEPTION_50000(50000, "请联系管理员设置手机号码"),

    EXCEPTION_60000(60000, "原登录密码错误!"),
	EXCEPTION_60001(60001, "2次输入的新密码不同!"),
	EXCEPTION_60002(60002, "旧密码与新密码相同!"),

	 EXCEPTION_70012(70012, "请求超时,请稍后再试"),
    ;

	private int code;
	private String description;

	private BusinessExceptionCode(int code, String description) {
		setCode(code);
		setDescription(description);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static BusinessExceptionCode getBusinessExceptionCodeFromCode(
			Integer code) {
		BusinessExceptionCode excode = null;
		if (code == null)
			code = 1000;
		for (BusinessExceptionCode item : BusinessExceptionCode.values()) {
			if (item.getCode() == code.intValue()) {
				excode = item;
				break;
			}
		}
		if (excode == null)
			excode = EXCEPTION_1000;
		return excode;
	}
	
	public static void main(String[] args) {
		try {
			throw new BusinessException(
					BusinessExceptionCode.EXCEPTION_1001,100);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
