package com.iokbl.model.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 响应信息格式化工具类
 * @author chenyufei
 * @date 2018年10月18日16:15:04
 */
public class ResultUtil {

	public static Map<String,Object> creatSuccessResult(Object obj){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.SUCCESS.getResultCode());
		resultMap.put("message", ResponseCode.ResultCode.SUCCESS.getMessage());
		resultMap.put("obj", obj);
		return resultMap;
	}

	public static Map<String,Object> creatSuccessResult(Object obj,String message){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.SUCCESS.getResultCode());
		resultMap.put("message", message);
		resultMap.put("obj", obj);
		return resultMap;
	}

	public static Map<String,Object> creatSuccessResult(String message){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.SUCCESS.getResultCode());
		resultMap.put("message", message);
		return resultMap;
	}

	public static Map<String,Object> creatSuccessResult(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.SUCCESS.getResultCode());
		resultMap.put("message", ResponseCode.ResultCode.SUCCESS.getMessage());
		return resultMap;
	}

	public static Map<String,Object> creatErrorResult(String message){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.ERROR.getResultCode());
		resultMap.put("message", message);
		return resultMap;
	}

	public static Map<String,Object> creatErrorResult(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.ERROR.getResultCode());
		resultMap.put("message", ResponseCode.ResultCode.ERROR.getMessage());
		return resultMap;
	}

	public static Map<String,Object> creatFailResult(){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.FAIL.getResultCode());
		resultMap.put("message", ResponseCode.ResultCode.FAIL.getMessage());
		return resultMap;
	}

	public static Map<String,Object> creatFailResult(String message){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("resCode", ResponseCode.ResultCode.FAIL.getResultCode());
		resultMap.put("message", message);
		return resultMap;
	}
}
