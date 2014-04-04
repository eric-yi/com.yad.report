// com.yad.report.engine.dataset.SchedulerHandler.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据集调度器
 * 决定调度哪些数据集及顺序
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:52:35 
 */
public abstract class SchedulerHandler {
	/**
	 * 控制器，调度的入口
	 * @param  @param parameters
	 * @param  @return
	 * @return List<String>
	 * @throws
	 */
	public List<String> handle(Map<String, Object> parameters) {
		List<String> list = scheduler(parameters);
		if (null == list) 
			return new ArrayList<String>();
		return list;
	}

	/**
	 * 具体调度策略
	 * 这里由各个继续类来实现
	 * @param  @param parameters
	 * @param  @return
	 * @return List<String>
	 * @throws
	 */
	protected abstract List<String> scheduler(Map<String, Object> parameters);
}

