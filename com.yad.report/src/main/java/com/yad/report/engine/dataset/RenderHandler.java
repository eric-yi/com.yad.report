// com.yad.report.engine.dataset.RenderHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.dataset;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 渲染结果集操作处理模板类 
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:52:28 
 */
public abstract class RenderHandler {
	protected List<List> list;
	protected String result;
	protected HttpServletRequest request;
	protected Map<String, Object> parameters;
	public RenderHandler(){
		
	}
	
	public String handle(HttpServletRequest request, List<List> list,Map<String, Object> parameters){
		this.list=list;
		this.request=request;
		this.parameters=parameters;
		convert();
		return result;
	}
	/*
	 * 结果集转换
	 */
	protected abstract void convert();
}

