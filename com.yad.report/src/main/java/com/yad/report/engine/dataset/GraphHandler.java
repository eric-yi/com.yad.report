// com.yad.report.engine.dataset.GraphHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.dataset;

import java.util.List;

import com.yad.report.engine.content.Render;

/**
 * 图形展示handler
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:52:21 
 */
public abstract class GraphHandler {
	protected Render render;
	protected List<List> list;
	protected String resultUrl;
	public GraphHandler(){
		
	}
	public String handler(Render render, List<List> list){
		this.render=render;
		this.list=list;
		convert();
		return resultUrl;
	}
	/*
	 * 结果集转换
	 */
	protected abstract void convert();
}

