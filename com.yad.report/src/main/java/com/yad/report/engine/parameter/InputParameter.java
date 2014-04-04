// com.yad.report.engine.parameter.InputParameter.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.parameter;

import com.yad.report.common.DATA_TYPE;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:00:03 
 */
public class InputParameter {
	private String name;
	private DATA_TYPE type;
	private String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DATA_TYPE getType() {
		return type;
	}
	public void setType(DATA_TYPE type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

