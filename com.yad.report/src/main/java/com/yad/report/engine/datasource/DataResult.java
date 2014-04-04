// com.yad.report.engine.datasource.DataResult.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.datasource;

import java.util.List;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:28:39 
 */
public class DataResult {
	private String queryStr;
	private List dataList;
	
	public DataResult(String queryStr, List dataList) {
		super();
		this.queryStr = queryStr;
		this.dataList = dataList;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public List getDataList() {
		return dataList;
	}
}

