// com.yad.report.engine.parameter.ParameterHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.parameter;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:00:09 
 */
public abstract class ParameterHandler {
	protected Map<String, Object> parameters;
	
	protected Object getParameter(String key) {
		return parameters.get(key);
	}
	
	/*
	 * 取得parameter中的String类型参数,空字符串也返回null
	 */
	protected String getString(String key) {
		if (null == getParameter(key))
			return null;
		String ret = (String) getParameter(key);
		if ("".equals(ret))
			return null;
		return ret;
	}
	
	/*
	 * 取得parameter中的int类型参数
	 */
	protected Integer getInt(String key) {
		if (null == getParameter(key))
			return null;
		String ret = (String) getParameter(key);
		return Integer.valueOf(ret);
	}
	
	public ParameterHandler() {
		parameters = new HashMap<String, Object>();
	}
	
	/**
	 * 参数转换处理函数
	 * @param  @param input
	 * @param  @return
	 * @param  @throws Exception
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> handle(Map<String, InputParameter> input) throws Exception {
		if (input != null)
			convert(input);
		
		return parameters;
	}

	// 转换,由继承类实现
	protected abstract void convert(Map<String, InputParameter> input)  throws Exception;
}

