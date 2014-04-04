// com.yad.report.engine.content.CondtionIfs.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

/**
 * 引擎if条件
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:37:14 
 */
@Deprecated
public class CondtionIfs {
	private int index;
	private String value;
	private String text;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}

