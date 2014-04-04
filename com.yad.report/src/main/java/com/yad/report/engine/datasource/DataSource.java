// com.yad.report.engine.datasource.DataSource.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
 */

package com.yad.report.engine.datasource;

/**
 * TODO desc
 * 
 * @author eric
 * @version 0.1
 * @Date 2014-4-3 下午12:32:27
 */
public class DataSource {
	protected String id; // id 标识
	protected DATASOURCE_TYPE type; // 数据源类型

	// getter and setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DATASOURCE_TYPE getType() {
		return type;
	}

	public void setType(DATASOURCE_TYPE type) {
		this.type = type;
	}
}
