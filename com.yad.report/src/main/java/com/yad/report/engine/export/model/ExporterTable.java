// com.yad.report.engine.export.model.ExporterTable.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export.model;

import java.util.List;

/**
 * 导出的表定义
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:48:13 
 */
public class ExporterTable {
	private String title;								// 标题
	private ExporterText text;					// 内容提示
	private List<ExporterRow> rowList;		// 所有的数据行
	
	// getter and setter
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ExporterText getText() {
		return text;
	}
	public void setText(ExporterText text) {
		this.text = text;
	}
	public List<ExporterRow> getRowList() {
		return rowList;
	}
	public void setRowList(List<ExporterRow> rowList) {
		this.rowList = rowList;
	}

}
