// com.yad.report.engine.parameter.DateParameterHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.parameter;

import java.util.Date;
import java.util.Map;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:59:56 
 */
public class DateParameterHandler extends ParameterHandler {
	/**
	 * 
	 * (non-Javadoc)
	 * @see com.yamu.dms.lib.report.engine.parameter.ParameterHandler#convert(java.util.Map)
	 */
	@Override
	protected void convert(Map<String, InputParameter> input) {
		parameters.put("date", new Date());
	}
}

