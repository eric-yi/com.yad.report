// com.yad.report.CallType.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report;

/**
 * 调用报表引擎方式：包括查询和导出
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:24:10 
 */
public enum CallType {
	query("query"),
	export("export");
	
	private String name;
	private CallType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static CallType toType(String name) {
		for (CallType t : CallType.values()) {
			if (t.getName().equalsIgnoreCase(name)) return t;
		}
		
		return null;
	}
}

