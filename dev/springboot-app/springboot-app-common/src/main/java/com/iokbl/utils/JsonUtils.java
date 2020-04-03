package com.iokbl.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class) ;

	public static String convertObjToJson(Object obj){
		String json =""; 
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY) ;
			json = mapper.writeValueAsString(obj);
		} catch (Exception e) {
            logger.error("Failed to convert obj to json for obj:"+obj, e);
		}
		return json;
	}
	


	@SuppressWarnings("deprecation")
	public static Object convertJsonToObj(String jsonStr,Class<?> clszz){
		ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
		try {
			mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY) ;
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DeserializationConfig cfg = mapper.getDeserializationConfig();
			cfg.setDateFormat(dateFormat);
			mapper = mapper.setDeserializationConfig(cfg);
			obj = mapper.readValue(jsonStr,clszz);
		} catch (Exception e) {
            logger.error("Failed to convert jsonStr to obj" + jsonStr, e);
		}
		return obj;
	}
	
	public static Object convertJsonToObjList(String jsonStr,Class<?> clszz){
		ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
		try {
			mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY) ;
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clszz);
			obj = mapper.readValue(jsonStr,listType);
		} catch (Exception e) {
            logger.error("Failed to convert jsonStr to obj" + jsonStr, e);
		}
		return obj;
	}
	
	public static Object convertJsonToObjMap(String jsonStr,Class<?> keyClazz, Class<?> valueClazz) {
		ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try{
        	mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY) ;
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, keyClazz, valueClazz);
			obj = mapper.readValue(jsonStr,mapType);
        }catch (Exception e) {
            logger.error("Failed to convert jsonStr to obj" + jsonStr, e);
		}
		return obj;
	}

	/**
	 * 字符串 转换成集合
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> jsonStringToList(String str, String strBean, Class<T> obj) throws Exception{
		if(JSONObject.fromObject(str).has(strBean)){
			return com.alibaba.fastjson.JSON.parseArray(JSONObject.fromObject(str).getJSONArray(strBean).toString(),obj);   
		}
		
		return null;
	}
	  
	/***
	 * 把String 转换成JavaBean
	 * @param str
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T strToJavaBean(String str, String strBean, Class<T> obj) { 
		if(JSONObject.fromObject(str).has(strBean)){
			return (T) JsonUtils.convertJsonToObj(JSONObject.fromObject(JSONObject.fromObject(str).get(strBean).toString()).toString(), obj);
		}
		
		return null;
	}

	/**
	 * 字符串 转换成集合
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> jsonStringToList(String str,Class<T> obj)throws Exception{
		return com.alibaba.fastjson.JSON.parseArray(JSONObject.fromObject(str).getJSONArray("obj").toString(),obj);
	}

	/***
	 * 把String 转换成JavaBean
	 * @param str
	 * @param obj
	 * @return
	 */
	public static <T> T strToJavaBean(String str,Class<T> obj){
		return (T) JsonUtils.convertJsonToObj(JSONObject.fromObject(JSONObject.fromObject(str).get("obj").toString()).toString(), obj);
	}

	public static <T> T getDTO(String jsonString, Class<T> clazz) {
		return new Gson().fromJson(new JsonParser().parse(jsonString), clazz);
	}
	
}
