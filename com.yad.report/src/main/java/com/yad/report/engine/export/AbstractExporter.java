// com.yad.report.engine.export.AbstractExporter.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.yad.report.common.DATA_TYPE;
import com.yad.report.common.XmlReader;
import com.yad.report.engine.export.model.DATADEFINE_TYPE;
import com.yad.report.engine.export.model.ExporterBean;
import com.yad.report.engine.export.model.ExporterRow;
import com.yad.report.engine.export.model.ExporterTable;
import com.yad.report.engine.export.model.ExporterText;
import com.yad.report.engine.export.model.ExporterUnit;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:42:13 
 */
public abstract class AbstractExporter {
	protected List<ExporterTable> tableList = new ArrayList<ExporterTable>();		// 导出表列表
	
	/**
	 * 导出
	 * 解析来源即输入流
	 * 导出并生成相应的输出流
	 * @param  @param in
	 * @param  @param out
	 * @param  @throws YMException
	 * @return void
	 * @throws
	 */
	public void export(InputStream in, OutputStream out) throws Exception {
		parse(in);			// 解析输入源
		export(out);		// 导出
	}
	
	/**
	 * 
	 * 导出
	 * 将定义的输入bean列表导出
	 * 并生成相应的输出流
	 *
	 * @param  @param inBeanList
	 * @param  @param out
	 * @param  @throws YMException
	 * @return void
	 * @throws 
	 * @since  DMS3.1
	 */
	public void export(List<ExporterBean> inBeanList, OutputStream out) throws Exception {
		InputStream in = convertXml(inBeanList);
		parse(in);			// 解析输入源
		export(out);		// 导出		
	}
	
	/*
	 * 将定义的导出bean转换成xml数据流
	 */
	protected InputStream convertXml(List<ExporterBean> inBeanList) {
		Document document = DocumentHelper.createDocument();  
		document.setXMLEncoding("utf-8");  
		Element root = document.addElement("input"); 		// 根节点
		for (ExporterBean inBean : inBeanList) {
			Element trEle;				// 行
			Element tdEle;			// 单元格
			Element table = root.addElement("table");
			// sheet名称
			if (null != inBean.getMark())
				table.addElement("title").setText(inBean.getMark());
			// 标题
			Element textEle = table.addElement("text");
			textEle.addAttribute("backcolor", "ffffff");
			if (null != inBean.getTitle())
				textEle.setText(inBean.getTitle());
			// 头数据
			trEle = table.addElement("tr");
			trEle.addAttribute("backcolor", "ffffff");
			trEle.addAttribute("align", "center");
			trEle.addAttribute("height", "30");
			for (Object header : inBean.getHeader()) {
				tdEle = trEle.addElement("td");
				tdEle.addAttribute("align", "center");
				tdEle.setText(header.toString());
			}
			// 数据集合
			String oddBackcolor = "f4f4f4";
			String evenBackcolor = "ffffff";
			String backcolor = oddBackcolor;
			int n = 0;
			if (null != inBean.getDataList()) {
				for (List entry : inBean.getDataList()) {
					// 当前行的背景色
					if (n%2 == 0) {
						backcolor = oddBackcolor;
					} else {
						backcolor = evenBackcolor;
					}
					trEle = table.addElement("tr");
					trEle.addAttribute("backcolor", backcolor);
					int m = 0;
					for (Object data : entry) {
						String dataType = "string";			// 数据类型
						if (null != inBean.getDataTypeListe() && inBean.getDataTypeListe().size() > m) {
							dataType = inBean.getDataTypeListe() .get(m).toString();
						}
						tdEle = trEle.addElement("td");
						tdEle.addAttribute("type", dataType);
						tdEle.setText(null==data?"":data.toString());
						m++;
					}
					n++;
				}
			}
		}
		
		StringWriter sw = new StringWriter();
		XMLWriter writer = new XMLWriter(sw);
		try {
			writer.write(document);
		} catch (IOException e) {
			
		} finally {
			InputStream in = null;
			try {
				in = new ByteArrayInputStream(sw.toString().getBytes("UTF-8") );
				sw.close();
				writer.close();
			} catch (IOException e) {
			}
			return in;
		}
	}
	
	/*
	 * 解析xml输入流
	 */
	protected void parse(InputStream in) throws Exception {
		XmlReader xmlReader = new XmlReader(in);		// 读取xml
		
		List<Element> tableEles = xmlReader.getElements("input/table");
		String backColor;
		String font;
		for (Element tableEle : tableEles) {
			ExporterTable table = new ExporterTable();
			table.setTitle(xmlReader.getText(tableEle, "title"));	// 标题
			Element textEle = tableEle.element("text");
			ExporterText text = new ExporterText();				// 信息
			if (null != textEle) {
				backColor = xmlReader.getAttribute(textEle, "backcolor");
				font = xmlReader.getAttribute(textEle, "font");
				text.setText(xmlReader.getText(tableEle, "text"));
				if (!isEmpty(backColor))
					text.setBackColor(new Color(Integer.parseInt(backColor, 16)));
				if (!isEmpty(font))
					text.setFont(Font.decode(font));
			}
			table.setText(text);
			
			List<ExporterRow> rowList = new ArrayList<ExporterRow>();	
			
			// 行标题
			Element  thEle = tableEle.element("th");
			if (null != thEle) {
				addHTitleRow(rowList, xmlReader, thEle);
			}
			
			// 列标题
			Element  tvEle = tableEle.element("tv");
			List<ExporterUnit> tvList = new ArrayList<ExporterUnit>();
			if (null != tvEle) {
				tvList = genUnitList(xmlReader, tvEle, true);
			}
			
			// 实际数据
			for (Object trObj : tableEle.elements("tr")) {	
				addDataRow(rowList, xmlReader, (Element) trObj);
			}
			table.setRowList(rowList);	// 每行的数据
			
			// 插入列标题
			if (!tvList.isEmpty()) {
				rowList = table.getRowList();
				if (rowList.isEmpty()) {			// 只有列标题
					rowList = new ArrayList<ExporterRow>();
					for (ExporterUnit tvUnit : tvList) {
						ExporterRow row = new ExporterRow();
						List<ExporterUnit> tvUnitList = new ArrayList<ExporterUnit>();
						tvUnitList.add(tvUnit);
						row.setDataList(tvUnitList);
						rowList.add(row);
					}
				} else {
					int index = 0;			// 数据列表下标
					if (DATADEFINE_TYPE.TITLE == rowList.get(0).getType()) {		// 每一行数据为行标题
						ExporterUnit tvUnit = new ExporterUnit();
						tvUnit.setDfineType(DATADEFINE_TYPE.TITLE);
						tvUnit.setType(DATA_TYPE.STRING);
						tvUnit.setData("");
						rowList.get(0).getDataList().add(0, tvUnit);
						index++;
					}
					for (ExporterUnit tvUnit : tvList) {
						if (index < rowList.size()) {
							rowList.get(index).getDataList().add(0, tvUnit);
						} else {
							ExporterRow row = new ExporterRow();
							row.getDataList().add(tvUnit);
							rowList.add(row);
						}
						
						index++;
					}
				}
			}
			

			tableList.add(table);
		}
	}
	
	/*
	 * 产生行标题
	 */
	private void addHTitleRow(List<ExporterRow> rowList, XmlReader xmlReader, Element rowEle) throws Exception {
		ExporterRow row = new ExporterRow();		// 行对像定义
		row.setType(DATADEFINE_TYPE.TITLE);
		String backColor = xmlReader.getAttribute(rowEle, "backcolor");
		String font = xmlReader.getAttribute(rowEle, "font");
		String align = xmlReader.getAttribute(rowEle, "align");
		String width = xmlReader.getAttribute(rowEle, "width");
		if (!isEmpty(backColor))
			row.setBackColor(new Color(Integer.parseInt(backColor, 16)));
		if (!isEmpty(font))
			row.setFont(Font.decode(font));
		if (!isEmpty(align))
			row.setPosition(align);
		if (!isEmpty(width))
			row.setWidth(Integer.valueOf(width));
		
		// 单元数据
		List<ExporterUnit> unitList = genUnitList(xmlReader, rowEle, true);
		row.setDataList(unitList);		// 单元数据列表
		rowList.add(row);				// 增加到行列表中
	}
	
	/*
	 * 加入数据行
	 */
	private void addDataRow(List<ExporterRow> rowList, XmlReader xmlReader, Element rowEle) throws Exception {
		ExporterRow row = new ExporterRow();		// 行对像定义
		String backColor = xmlReader.getAttribute(rowEle, "backcolor");
		String font = xmlReader.getAttribute(rowEle, "font");
		String align = xmlReader.getAttribute(rowEle, "align");
		String width = xmlReader.getAttribute(rowEle, "width");
		if (!isEmpty(backColor))
			row.setBackColor(new Color(Integer.parseInt(backColor, 16)));
		if (!isEmpty(font))
			row.setFont(Font.decode(font));
		if (!isEmpty(align))
			row.setPosition(align);
		if (!isEmpty(width))
			row.setWidth(Integer.valueOf(width));
		
		// 单元数据
		List<ExporterUnit> unitList = genUnitList(xmlReader, rowEle, false);
		row.setDataList(unitList);		// 单元数据列表
		rowList.add(row);				// 增加到行列表中
	}
	
	/*
	 * 产生单元数据列表
	 */
	private List<ExporterUnit> genUnitList(XmlReader xmlReader, Element rowEle, boolean isTitle) throws Exception {
		// 单元数据
		List<ExporterUnit> unitList = new ArrayList<ExporterUnit>();
		for (Object tdObj : rowEle.elements("td")) {
			ExporterUnit unit = new ExporterUnit();		// 单元对像定义
			Element tdEle = (Element) tdObj;
			String backColor = xmlReader.getAttribute(tdEle, "backcolor");
			String foreColor = xmlReader.getAttribute(tdEle, "forecolor");
			String font = xmlReader.getAttribute(tdEle, "font");
			String align = xmlReader.getAttribute(tdEle, "align");
			String height = xmlReader.getAttribute(tdEle, "height");
			if (!isEmpty(backColor))
				unit.setBackColor(new Color(Integer.parseInt(backColor, 16)));
			if (!isEmpty(foreColor))
				unit.setForeColor(new Color(Integer.parseInt(foreColor, 16)));
			if (!isEmpty(font))
				unit.setFont(Font.decode(font));
			if (!isEmpty(align))
				unit.setPosition(align);
			if (!isEmpty(height))
				unit.setHeight(Integer.valueOf(height));
			Object data;
			if (isTitle) {
				unit.setDfineType(DATADEFINE_TYPE.TITLE);
				data = tdEle.getText();					// 单元格为String类型
			} else {
				data = DATA_TYPE.toJavaType(
							xmlReader.getAttribute(tdEle, "type"), tdEle.getText());	// 设置单元格数据，包括数据类型
			}
			unit.setData(data);
			unitList.add(unit);
		}
		
		return unitList;
	}
	
	/*
	 * 字符串是否为空，null及无内容
	 */
	private boolean isEmpty(String s) {
		if (null != s && !"".equals(s))
			return false;
		
		return true;
	}
	
	
	/*
	 * 导出的实际功能
	 */
	protected abstract void export(OutputStream out) throws Exception;
}

