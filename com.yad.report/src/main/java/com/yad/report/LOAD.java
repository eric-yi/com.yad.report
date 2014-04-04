// com.yad.report.LOAD.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report;

/**
 * 加载报表引擎方式，系统启动时(init) 运行时(run)
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:24:33 
 */
public enum LOAD {
	INIT("init"),
	RUN("run");
	
	private String name;
	private LOAD(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	
	// default is init
	public static LOAD toLoad(String name) {
		for (LOAD load : LOAD.values())
			if (name.equalsIgnoreCase(load.getName()))
				return load;

		return INIT;
	}
}

