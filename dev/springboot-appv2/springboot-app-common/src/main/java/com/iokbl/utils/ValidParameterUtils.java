package com.iokbl.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ValidParameterUtils {

	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void validData(Object obj) throws Exception {
		Class clazz = obj.getClass();
		Field[] fields =  clazz.getDeclaredFields();
		// 循环所有属性
		for(Field field:fields){
			// 该属性是否有校验注解
			if(field.isAnnotationPresent(ValidAnnotation.class)){
				// 获取该属性的注解
				ValidAnnotation validAnnotation = field.getAnnotation(ValidAnnotation.class);
				// 是否校验非空
				boolean isNotNull = validAnnotation.isNotNull();
				//是否为数字字段
				boolean isNumeric = validAnnotation.isNumeric();
				
				// 获取属性名
				String fieldName = field.getName();
				// 获取get方法名
				String getMethodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

				// 执行该方法获取属性值
				Method method = clazz.getDeclaredMethod(getMethodName);
				String value = (String) method.invoke(obj, new Object[] {});

				if (isNotNull) {
					// 【判断该属性是否为空】
					if (StringUtils.isBlank(value)) {
						throw new Exception(fieldName + "属性值为空！");
					}
				}
				
				if (isNumeric) {
					if (value != null) {//
						if (StringUtils.isBlank(value)) {
							// 数字字段有值时，校验"","  "
							throw new Exception( fieldName + "属性值为空！");
						}
						if (!StringUtils.isNumeric(value)) {
							throw new Exception(
									fieldName + "属性值不是数字！");
						}
					}
				}
				
			}
		}
	}


}











