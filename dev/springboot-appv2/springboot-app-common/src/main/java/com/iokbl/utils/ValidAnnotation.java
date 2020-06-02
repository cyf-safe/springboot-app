package com.iokbl.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验注解
 * @author chenyufei
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAnnotation {

	/**
	 * 是否不能为空
	 * @return
	 */
	boolean isNotNull() default false;
	
	/**
	 * 是否不能大于0
	 * @return
	 */
	boolean isGreaterThan0() default false;
	
	/**
	 * 是否不能小于于0
	 * @return
	 */
	boolean isLessThan0() default false;

	/**
	 * 是否不能等于0
	 * @return
	 */
	boolean isNotZero() default false;
	
	/**
	 * 长度校验
	 * @return
	 */
	int maxLength() default 0;
	
	/**
	 * 属性中文名称
	 * @return
	 */
	String comment();

	/**
	 * 属性英文名称
	 * @return
	 */
	String fieldName();

	/**
	 * 金额长度
	 * @return
	 */
	String BigDecimal() default "";
	
	/**
	 * 是否为数字字段
	 * @return
	 */
	boolean isNumeric() default false;

	/**
	 * 是否为时间字段
	 * @return
	 */
	boolean isDate() default false;

	/**
	 * 是否为枚举
	 * @return
	 */
	boolean isEnums() default false;
	
	/**
	 * 枚举值
	 * @return
	 */
	String enums() default "";

	/**
	 * 数据字典表枚举类型
	 * @return
	 */
	String dictType() default "";
	
	/**
	 * 是否小于
	 * 
	 * @return
	 */
	boolean isLessThan1() default false;

}
