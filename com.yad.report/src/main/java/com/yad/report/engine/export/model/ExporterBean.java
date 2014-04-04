// com.yad.report.engine.export.model.ExporterBean.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export.model;

import java.util.List;

/**
 * 导出bean，
 * 使用此bean定义输入数据及格式，
 * 由导出生成xml输入文件
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:47:52 
 */
public class ExporterBean {
	private String mark = "";				// 标示 做为excel的sheet或称
	private String title = "";					// 标题	
	private boolean orderkey = false;	// 是否加上序列号
	private List Header;						// 头数据
	private List dataTypeListe;				// 列数据类型列表
	private List<List> dataList;				// 数据集
	
	
	public ExporterBean(String title, List header, List<List> dataList) {
		super();
		this.title = title;
		Header = header;
		this.dataList = dataList;
	}

	public ExporterBean(String title, List header, List dataTypeListe,
			List<List> dataList) {
		super();
		this.title = title;
		Header = header;
		this.dataTypeListe = dataTypeListe;
		this.dataList = dataList;
	}
	
	public ExporterBean(String mark, String title, List header,
			List dataTypeListe, List<List> dataList) {
		super();
		this.mark = mark;
		this.title = title;
		Header = header;
		this.dataTypeListe = dataTypeListe;
		this.dataList = dataList;
	}

	// getter and setter
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List getHeader() {
		return Header;
	}
	public void setHeader(List header) {
		Header = header;
	}
	public List getDataTypeListe() {
		return dataTypeListe;
	}
	public void setDataTypeListe(List dataTypeListe) {
		this.dataTypeListe = dataTypeListe;
	}
	public List<List> getDataList() {
		return dataList;
	}
	public void setDataList(List<List> dataList) {
		this.dataList = dataList;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}

