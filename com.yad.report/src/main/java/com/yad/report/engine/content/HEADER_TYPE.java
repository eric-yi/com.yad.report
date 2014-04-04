// com.yad.report.engine.content.HEADER_TYPE.java
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
 * @Date	 2014-4-3		上午11:38:18 
 */
public enum HEADER_TYPE {
	COMMON("common"),	
	CROSS("cross");
	
	private String name;
	private HEADER_TYPE(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	
	public static HEADER_TYPE toType(String name) {
		for (HEADER_TYPE ht : HEADER_TYPE.values())
			if (ht.getName().equalsIgnoreCase(name))
				return ht;
		
		return COMMON;
	}
}

