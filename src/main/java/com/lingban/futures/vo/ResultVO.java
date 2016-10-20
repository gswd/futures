package com.lingban.futures.vo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = { "code", "msg", "result" })
public class ResultVO<T> implements Serializable{

	private static final long serialVersionUID = -4463682147741357133L;
	
	private Integer code;
	private String msg;
	private T result;


	public ResultVO() {
		super();
	}

	public ResultVO(Integer code, String msg, T result) {
		super();
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResultVo [code=" + code + ", msg=" + msg + ", result=" + result + "]";
	}

}