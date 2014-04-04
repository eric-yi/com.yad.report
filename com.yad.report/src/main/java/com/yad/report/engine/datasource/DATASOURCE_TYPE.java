// com.yad.report.engine.datasource.DATASOURCE_TYPE.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.datasource;


/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:33:49 
 */
public enum DATASOURCE_TYPE {
	JDBC("jdbc");

	private String name;

	private DATASOURCE_TYPE(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override public String toString() {
		return name;
	}
	public static DATASOURCE_TYPE toType(String name) {
		for (DATASOURCE_TYPE dt : DATASOURCE_TYPE.values())
			if (dt.getName().equalsIgnoreCase(name))
				return dt;

		return null;
	}
}

