// com.yad.report.engine.content.DataSetParameter.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

/**
 * 数据集的输入参数
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:37:46 
 */
public class DataSetParameter {
	private DATASETPARAMETER_TYPE type;
	private String name;
	
	public DataSetParameter(DATASETPARAMETER_TYPE type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	// getter and setter
	public DATASETPARAMETER_TYPE getType() {
		return type;
	}
	public void setType(DATASETPARAMETER_TYPE type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

