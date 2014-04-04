// com.yad.report.engine.transfer.TransferProxy.java
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
 * 数据集转换到最终结果集proxy
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:17:26 
 */
public class TransferProxy {
	/**
	 * 代理处理函数
	 */
	public static List<List> handler(String className, Map<String, List> sources) throws Exception {
		if (className != null && !"".equals(className)) {
			Class c = Class.forName(className);
			if (TransferHandler.class.isAssignableFrom(c)) {
				TransferHandler handler = (TransferHandler) c.newInstance();
				return handler.handle(sources);
			}
		}
		
		return new ArrayList<List>();
	}
}

