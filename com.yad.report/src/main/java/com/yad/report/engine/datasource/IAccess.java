// com.yad.report.engine.datasource.IAccess.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.datasource;

import java.util.List;
import java.util.Map;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:32:40 
 */
public interface IAccess {
	public void init();
	
	public Object getConnection();
	
	public List query(String sql);
	
	
	/**
	 * 按照sql有数据库查询，
	 * replaces为sql语句中直接替换的变量
	 * paramter为sql语句的参数
	 */
	public List query(String sql, Map<String, String> replaces,  List<Object> parameters);
	
	public DataResult queryResult(String sql, Map<String, String> replaces,  List<Object> parameters);
}

