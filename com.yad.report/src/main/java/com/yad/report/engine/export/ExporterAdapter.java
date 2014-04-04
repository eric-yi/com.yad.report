// com.yad.report.engine.export.ExporterAdapter.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.yad.report.engine.export.model.ExporterBean;

/**
 * 导出适配器
 * 所有的导出功能统一由这个适配器来统一建立
 * 并执行相应的导出功能
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:42:25 
 */
public class ExporterAdapter {
private AbstractExporter exporter;		// 导出的实例
	
	// 构造函数 通过类型建立各个导出实例
	public ExporterAdapter(EXPORT_TYPE type) {
		switch (type) {
			case CSV:
				break;
			case EXCEL:
				exporter = new ExcelExporter();
				break;
			case IMAGE:
				break;
			case PDF:
				break;
			case WORD:
				break;
			case XML:
				break;
		}
		
	}

	/**
	 * 导出
	 * 先解析再导出
	 * @param  @param in
	 * @param  @param out
	 * @param  @throws Exception
	 * @return void
	 * @throws
	 */
	public void export(InputStream in, OutputStream out) throws Exception {
		if (exporter == null)		// 导出实例不存在，直接返回
			return;
		
		exporter.export(in, out);
	}
	
	/**
	 * 导出
	 * 输入为定义的bean list
	 * 将bean list转换成xml数据流
	 * 再做解析
	 * 之后做导出
	 * @param  @param inBeanList
	 * @param  @param out
	 * @param  @throws Exception
	 * @return void
	 * @throws
	 */
	public void export(List<ExporterBean> inBeanList, OutputStream out) throws Exception {
		if (exporter == null)		// 导出实例不存在，直接返回
			return;
		
		exporter.export(inBeanList, out);
	}
}



