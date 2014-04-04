// com.yad.report.engine.content.PARAMETER_SRC.java
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
 * @Date	 2014-4-3		上午11:38:30 
 */
public enum PARAMETER_SRC {
	PARAMETERS("parameters"),
	DATASET("dataset");
	
	private String name;
	private PARAMETER_SRC(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	
	public static PARAMETER_SRC toSrc(String name) {
		for (PARAMETER_SRC ps : PARAMETER_SRC.values())
			if (ps.getName().equalsIgnoreCase(name))
				return ps;
		
		return PARAMETERS;
	}
}

