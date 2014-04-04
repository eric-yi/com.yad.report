// com.yad.report.cache.CacheContent.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.cache;

import java.util.List;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:20:01 
 */
public class CacheContent {
	private String reportId;
	private List<List> dataList;
	
	public CacheContent() {
		super();
	}
	public CacheContent(String reportId, List<List> dataList) {
		super();
		this.reportId = reportId;
		this.dataList = dataList;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public List<List> getDataList() {
		return dataList;
	}
	public void setDataList(List<List> dataList) {
		this.dataList = dataList;
	}
}
