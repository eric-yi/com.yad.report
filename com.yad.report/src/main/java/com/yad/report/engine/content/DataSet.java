// com.yad.report.engine.content.DataSet.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:37:39 
 */
public class DataSet {
	private String name;
	private String datasource;
	private int order = -1;
	private Map<Integer, DataSetParameter> parameters = new HashMap<Integer, DataSetParameter>();
	private Map<String, String> handlers = new HashMap<String, String>();
	private String statement;
	private Map<Integer,String> condtions = new HashMap<Integer,String>(); //查询条件map
	private Map<Integer,ArrayList<CondtionIfs>> ifs = new HashMap<Integer,ArrayList<CondtionIfs>>();
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Map<Integer, DataSetParameter> getParameters() {
		return parameters;
	}

	public void setParameters(Map<Integer, DataSetParameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, String> getHandlers() {
		return handlers;
	}

	public void setHandlers(Map<String, String> handlers) {
		this.handlers = handlers;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public Map<Integer, String> getCondtions() {
		return condtions;
	}

	public void setCondtions(Map<Integer, String> condtions) {
		this.condtions = condtions;
	}

	public Map<Integer, ArrayList<CondtionIfs>> getIfs() {
		return ifs;
	}

	public void setIfs(Map<Integer, ArrayList<CondtionIfs>> ifs) {
		this.ifs = ifs;
	}
}

