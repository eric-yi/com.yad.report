// com.yad.report.engine.dataset.DatasetHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.dataset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yad.report.engine.content.DataSetParameter;

/**
 * 结果集操作处理模板类 
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:52:09 
 */
public abstract class DatasetHandler {
	protected Map<Integer, DataSetParameter> dataset;
	
	public DatasetHandler() {
		dataset = new HashMap<Integer, DataSetParameter>();
	}
	
	/**
	 * 对于结果集处理得到相应的参数map 
	 */
	public Map<Integer, DataSetParameter> handler(List list) {
		if (list != null && !list.isEmpty())
			convert();
		
		return dataset;
	}

	/*
	 * 结果集转换
	 */
	protected abstract void convert();
}

