// com.yad.report.engine.content.GRAPH_TYPE.java
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
 * @Date	 2014-4-3		上午11:38:10 
 */
public enum GRAPH_TYPE {
	CURVE("curve"),	
	PIE("pie");
	
	private String name;
	private GRAPH_TYPE(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	
	public static GRAPH_TYPE toType(String name) {
		for (GRAPH_TYPE gt : GRAPH_TYPE.values())
			if (gt.getName().equalsIgnoreCase(name))
				return gt;
		
		return null;
	}
}

