// com.yad.report.engine.DATA_TYPE.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.common;

import java.text.ParseException;

/**
 * 数据类型定义
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:05:23 
 */
public enum DATA_TYPE {
	STRING("string"),
	NUMBER("number"),
	DATE("date"),
	DATETIME("datetime");
	
	private String name;		// 名称表示
	private DATA_TYPE(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	
	public static DATA_TYPE toType(String name) {
		for (DATA_TYPE dt : DATA_TYPE.values())
			if (dt.getName().equalsIgnoreCase(name))
				return dt;
		
		return STRING;
	}
	
	public static Object toJavaType(String type, String value) {
		if ("string".equalsIgnoreCase(type))
			return value;
		
		if ("number".equalsIgnoreCase(type))
			return Double.parseDouble(value);
		
		if ("date".equalsIgnoreCase(type))
			try {
				return Utils.strToDate(value);
			} catch (ParseException e) {
				
			}
		
		if ("datetime".equalsIgnoreCase(type))
			try {
				return Utils.strToTime(value);
			} catch (ParseException e) {
				
			}
		
		return value;
	}
}

