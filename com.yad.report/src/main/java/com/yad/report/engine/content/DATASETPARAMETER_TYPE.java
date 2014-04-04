// com.yad.report.engine.content.DATASETPARAMETER_TYPE.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

/**
 * 数据集输入参数类型
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:37:54 
 */
public enum DATASETPARAMETER_TYPE {
	REPLACE("replace"),	
	PARAMETER("parameter");
	
	private String name;
	private DATASETPARAMETER_TYPE(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	
	public static DATASETPARAMETER_TYPE toType(String name) {
		for (DATASETPARAMETER_TYPE dt : DATASETPARAMETER_TYPE.values())
			if (dt.getName().equalsIgnoreCase(name))
					return dt;
		
		return PARAMETER;
	}
}

