// com.yad.report.engine.export.model.ExporterRow.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出行定义
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:47:58 
 */
public class ExporterRow extends ExporterStyle {
	private DATADEFINE_TYPE type = DATADEFINE_TYPE.INDEX;
	private List<ExporterUnit> dataList = new ArrayList<ExporterUnit>();		// 行数据列表

	// getter and setter
	public List<ExporterUnit> getDataList() {
		return dataList;
	}

	public void setDataList(List<ExporterUnit> dataList) {
		this.dataList = dataList;
	}

	public DATADEFINE_TYPE getType() {
		return type;
	}

	public void setType(DATADEFINE_TYPE type) {
		this.type = type;
	}
}

