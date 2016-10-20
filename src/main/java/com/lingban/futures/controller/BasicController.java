package com.lingban.futures.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lingban.futures.vo.ResultVO;
import com.lingban.futures.vo.StatusCode;

public abstract class BasicController {

	private static SerializerFeature[] feature = { SerializerFeature.DisableCircularReferenceDetect,
			SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteMapNullValue };

	public static final String SUCCESS = buildSuccessResultInfo();
	public static final String Fail = buildFailedResultInfo();

	/**
	 * @description: 构造成功返回结果
	 */
	private static String buildSuccessResultInfo() {
		return success(null);
	}

	protected static <T> String success(T resultData) {
		ResultVO<T> resultVo = new ResultVO<>();
		resultVo.setResult(resultData);
		resultVo.setMsg(StatusCode.SUCCESS.getMsg());
		resultVo.setCode(StatusCode.SUCCESS.getCode());
		return JSON.toJSONString(resultVo, feature);
	}

	private static <T> String buildFailedResultInfo() {
		ResultVO<T> resultVo = new ResultVO<>();
		resultVo.setMsg(StatusCode.FAIL.getMsg());
		resultVo.setCode(StatusCode.FAIL.getCode());
		return JSON.toJSONString(resultVo, feature);
	}

	protected static <T> String buildResultInfo(StatusCode statusCode, T resultData) {
		ResultVO<T> resultVo = new ResultVO<>();
		resultVo.setMsg(statusCode.getMsg());
		resultVo.setCode(statusCode.getCode());
		resultVo.setResult(resultData);
		return JSON.toJSONString(resultVo, feature);
	}

}