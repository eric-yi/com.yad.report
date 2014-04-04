// com.yad.report.engine.content.Content.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:37:31 
 */
public class Content {
	private String id;
	private String name;
	private CALL call;
	private String outpage;
	private List<Parameter> parameters;
	private List<String> parameterHandlers;
	private List<DataSet> datasets;
	private String datasetHandler;		// 数据集的调度器，决定数据集的执行与否及其它控制
	private Transfer transfer;
	private Render render;
	private ExcelParameter excel;
	private HttpServletRequest request;
	private Map<String, Object> convertParameters;
	
	public Map<String, Object> getConvertParameters() {
		return convertParameters;
	}
	public void setConvertParameters(Map<String, Object> convertParameters) {
		this.convertParameters = convertParameters;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CALL getCall() {
		return call;
	}
	public void setCall(CALL call) {
		this.call = call;
	}
	public String getOutpage() {
		return outpage;
	}
	public void setOutpage(String outpage) {
		this.outpage = outpage;
	}
	public List<Parameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	public List<String> getParameterHandlers() {
		return parameterHandlers;
	}
	public void setParameterHandlers(List<String> parameterHandlers) {
		this.parameterHandlers = parameterHandlers;
	}

	public List<DataSet> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DataSet> datasets) {
		this.datasets = datasets;
	}
	public Transfer getTransfer() {
		return transfer;
	}
	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
	public Render getRender() {
		return render;
	}
	public void setRender(Render render) {
		this.render = render;
	}
	public ExcelParameter getExcel() {
		return excel;
	}
	public void setExcel(ExcelParameter excel) {
		this.excel = excel;
	}
	public String getDatasetHandler() {
		return datasetHandler;
	}
	public void setDatasetHandler(String datasetHandler) {
		this.datasetHandler = datasetHandler;
	}
}


