// com.yad.report.engine.transfer.TransferHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 结果集转换控制器
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:17:20 
 */
public abstract class TransferHandler {
	protected List<List> results;					// 结果集
	protected Map<String, List> sources;		// 来源集
	
	public TransferHandler() {
		results = new ArrayList<List>();
	}


	/**
	 * 主控制处理函数，返回最终的结果集
	 */
	public List<List> handle(Map<String, List> sources) {
		this.sources = sources;
		transfer();		// 转换
		return results;
	}
	
	/*
	 * 来源数据转换
	 */
	protected abstract void transfer();
}


