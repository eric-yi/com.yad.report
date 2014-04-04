// com.yad.report.engine.content.ExcelExporter.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:38:01 
 */
public class ExcelParameter {
	private String template;
	private String filename;

	public ExcelParameter() {
		
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}

