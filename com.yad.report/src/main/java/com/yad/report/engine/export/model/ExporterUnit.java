// com.yad.report.engine.export.model.ExporterUnit.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export.model;

import com.yad.report.common.DATA_TYPE;

/**
 * 导出单元格定义
 * 继承于样式定义类 ExporterStyle
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:48:25 
 */
public class ExporterUnit extends ExporterStyle {
	private DATADEFINE_TYPE dfineType = DATADEFINE_TYPE.INDEX;		// 标题数据还是指标数据
	private DATA_TYPE type = DATA_TYPE.STRING;		// 类型
	private Object data;							// 数据

	// getter and setter
	public void setType(DATA_TYPE type) {
		this.type = type;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}

	public DATA_TYPE getType() {
		return type;
	}

	public DATADEFINE_TYPE getDfineType() {
		return dfineType;
	}

	public void setDfineType(DATADEFINE_TYPE dfineType) {
		this.dfineType = dfineType;
	}
}

