// com.yad.report.engine.export.model.ExporterText.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
 */

package com.yad.report.engine.export.model;

/**
 * 标题定义
 * @author eric
 * @version 0.1
 * @Date 2014-4-3 下午12:48:19
 */
public class ExporterText extends ExporterStyle {
	private String text; // 标题

	// getter and setter
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
