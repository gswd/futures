package com.lingban.futures.vo;

public enum StatusCode {
	SUCCESS(0, "success"), 
	FAIL(-1, "fail");
	
	private Integer code;
	private String msg;
	
	private StatusCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
