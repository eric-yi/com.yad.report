// com.yad.report.engine.dataset.DatasetProxy.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yad.report.engine.content.Content;
import com.yad.report.engine.content.DataSetParameter;
import com.yad.report.engine.content.Render;

/**
 * 结果处理代理
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:52:15 
 */
public class DatasetProxy {
	/**
	 * 结果集处理函数，各个handler的代理
	 * @param  @param inputMap
	 * @param  @return
	 * @param  @throws ClassNotFoundException
	 * @param  @throws InstantiationException
	 * @param  @throws IllegalAccessException
	 * @return Map<Integer,DataSetParameter>
	 * @throws
	 */
	public static Map<Integer, DataSetParameter> datasetHandler(Map<String, List> inputMap) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map<Integer, DataSetParameter> datasetParameters = new HashMap<Integer, DataSetParameter>();
		for (String className : inputMap.keySet()) {
			if (className != null && !"".equals(className)) {
				Class c = Class.forName(className);
				if (DatasetHandler.class.isAssignableFrom(c)) {
					DatasetHandler handler = (DatasetHandler) c.newInstance();
					datasetParameters = handler.handler(inputMap.get(className));
				}
			}
		}
		return datasetParameters;
	}
	
	/**
	 * 执行数据集策略调度器
	 * @param  @param clazz
	 * @param  @param parameters
	 * @param  @return
	 * @param  @throws ClassNotFoundException
	 * @param  @throws InstantiationException
	 * @param  @throws IllegalAccessException
	 * @return List<String>
	 * @throws
	 */
	public static List<String> schedulerHandler(String clazz, Map<String, Object> parameters) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class c = Class.forName(clazz);
		if (SchedulerHandler.class.isAssignableFrom(c)) {
			SchedulerHandler handler = (SchedulerHandler) c.newInstance();
			return handler.handle(parameters);
		}
			
		return new ArrayList<String>();
	}
	/**
	 * 执行渲染结果集调度
	 */
	public static String renderHandler(Content content, List<List> list)throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class c = Class.forName(content.getRender().getHandler());
		if (RenderHandler.class.isAssignableFrom(c)){
			RenderHandler handler = (RenderHandler)c.newInstance();
			return handler.handle(content.getRequest(), list,content.getConvertParameters());
		}
		return "";
	}
	/**
	 * 画图形调度
	 */
	public static String graphHandler(Render render, List<List> list)throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class c = Class.forName(render.getGraph().getHandler());
		if (GraphHandler.class.isAssignableFrom(c)){
			GraphHandler handler = (GraphHandler)c.newInstance();
			return handler.handler(render, list);
		}
		return "";
	}
}

