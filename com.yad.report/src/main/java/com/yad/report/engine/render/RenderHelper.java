// com.yad.report.engine.render.RenderHelper.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.render;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.yad.report.engine.content.HEADER_TYPE;
import com.yad.report.engine.content.Render;
import com.yad.report.engine.content.Render.Rank;

/**
 * 渲染处理类
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:15:57 
 */
public class RenderHelper {
	/**
	 * 渲染生成最终的结果集
	 */
	public static String createResults(String url_path, String reportId, String key, Render render, List<List> list) {
		Document document = DocumentHelper.createDocument();// 建立xml
		document.setXMLEncoding("UTF-8");  
		Element root = document.addElement("results");		// 根节点
		root.addElement("title").setText(render.getTitle());			// title节点
		if (render.isExport_support()) 
			addExport(root, url_path, reportId, key);
		
		Comparator headerCom = new HeaderComparator();
		List<Rank> headerCols = render.getColumns();
		List<Rank> headerRows = render.getRows();
		Collections.sort(headerCols, headerCom);		// 头列排序
		Collections.sort(headerRows, headerCom);		// 头行排序
		Element table = table(root);		// table节点
		
		// 第一行标题
		HEADER_TYPE type = render.getType();
		Element tr = tr(table);
		if (type == HEADER_TYPE.CROSS)
			titleColumn(tr, "");
		for (Rank hcr : headerCols) {	// 每一行标题
			titleColumn(tr, hcr.getName());
		}
		
		// 每一行数据
		int n = 0;
		boolean isEven = true;		// 是否为偶数行
		for (List l : list) {
			isEven = n % 2 == 0 ? true : false;
			tr = table.addElement("tr");
			if (type == HEADER_TYPE.CROSS) {
				String rowTitle = "";
				if (n < headerRows.size())
					rowTitle = headerRows.get(n).getName();
				titleColumn(tr, rowTitle);
			}
			for (Object o : l) {
				if (isEven) {
					tdEven(tr, o);		// 偶数行数据
				} else {
					tdOdd(tr,  o);		// 奇数行数据
				}
			}
			n++;
		}
		
		StringWriter sw = new StringWriter();
		XMLWriter writer = new XMLWriter(sw);
		try {
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			String results = sw.toString();
			try {
				sw.close();
				writer.close();
			} catch (IOException e) {
			}
			return results;
		}
		
	}
	
	/*
	 * export节点
	 */
	private static void addExport(Element parent, String url_path, String reportId, String key) {
		Element export = parent.addElement("input");
		export.addAttribute("type", "button");
		String url = "window.location.href='" + (url_path==null?"":url_path) + "/reportServlet?reportId=" + reportId + "&export=1&export_key=" + key + "';";
		export.addAttribute("onClick", url);
		export.addAttribute("value", "导出");
	}
	
	/*
	 * table节点
	 */
	private static Element table(Element parent) {
		Element table = parent.addElement("table");		// table节点
		table.addAttribute("width", "98%");
		table.addAttribute("id", "table");
		table.addAttribute("cellpadding", "2");
		table.addAttribute("cellspacing", "0");
		table.addAttribute("border", "1");
		
		return table;
	}
	
	/*
	 * tr节点
	 */
	private static Element tr(Element parent) {
		Element tr =  parent.addElement("tr");	
		return tr;
	}
	
	/*
	 * 列标题节点
	 */
	private static Element titleColumn(Element parent, String text) {
		return th(parent, text);
	}
	
	/*
	 * 行标题节点
	 */
	private static Element titleRow(Element parent, String text) {
		return th(parent, text);
	}
	
	/*
	 * th节点
	 */
	private static Element th(Element parent, String text) {
		Element th =  parent.addElement("th");	
		th.addAttribute("rowspan", "1");
		th.addAttribute("colspan", "1");
		th.addAttribute("style", "font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;" +
				"font-size: 0.8em;;" +
				"color : Black;text-align: left;" +
				"vertical-align: top;" +
				"background-color : #EEF3FF;");
		th.addAttribute("nowrap", "nowrap");
		th.addAttribute("nowrap", "nowrap");
		th.addText(text);
		
		return th;
	}
	
	/*
	 * 偶数行节点 
	 */
	private static Element tdEven(Element parent, Object value) {
		return td(parent, "#f0f0f0", value);
	}
	
	/*
	 * 奇数行节点
	 */
	private static Element tdOdd(Element parent, Object value) {
		return td(parent, "#ffffff", value);
	}
	
	/*
	 * td节点
	 */
	private static Element td(Element parent, String background, Object value) {
		String align = "right";
		if (value instanceof String) {
			if (value == null || "".equals(value)) {
				align = "left";
			} else {
				String s = ((String) value).trim();
				String last = s.substring(s.length()-1, s.length());
				if (!"%".equals(last))
					align = "left";
			}
		}
		
		Element td =  parent.addElement("td");	
		td.addAttribute("width", "20%");
		td.addAttribute("style", "font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;" +
				"font-size: 0.8em;;" +
				"color : Black;" +
				"text-align: " + align + ";" +
				"background-color : " + background + ";");
		
		addValue(td, value);		// 显示指标项
		
		return td;
	}
	
	/**
	 * 
	 * 头排序
	 */
	private static class HeaderComparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			Rank r1 = (Rank) o1;
			Rank r2 = (Rank) o2;
			return r1.getIndex() - r2.getIndex();
		}
		
	}
	
	/*
	 * 将对像转换成指标值
	 */
	private static void addValue(Element parent, Object value) {
		if (value == null || "".equals(value.toString().trim())) {
			parent.addText(" ");
		} else {
			parent.addText(value.toString().trim());
		}
	}
}


