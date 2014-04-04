// com.yad.report.engine.content.CALL.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:37:08 
 */
public enum CALL {
	TOTAL("total"),		// 一页显示
	PAGE("page");		// 分页显示
	
	private String name;
	private CALL(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	
	public static CALL toCall(String name) {
		for (CALL call : CALL.values())
			if (call.getName().equalsIgnoreCase(name))
				return call;
		
		return TOTAL;
	}
}

