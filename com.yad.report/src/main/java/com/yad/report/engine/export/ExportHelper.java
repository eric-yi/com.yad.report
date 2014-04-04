// com.yad.report.engine.export.ExportHelper.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.yad.report.engine.export.model.ExporterBean;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:58:40 
 */
public class ExportHelper {
	
	public static ExportResult exportExcel(String name, List titleList, List<List> dataList, OutputStream out) throws Exception {
		ExporterAdapter adapter = new ExporterAdapter(EXPORT_TYPE.EXCEL);
		List<ExporterBean> inBeanList = new ArrayList<ExporterBean>();
		ExporterBean inBean = new ExporterBean(name, titleList, dataList);
		inBeanList.add(inBean);
		adapter.export(inBeanList, out);
		ExportResult result = new ExportResult();
		result.setName(name+".xls");
		return result;
	}
}

