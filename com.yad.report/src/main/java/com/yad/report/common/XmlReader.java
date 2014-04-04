// com.yad.report.engine.common.XmlReader.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.common;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午1:10:46 
 */
public class XmlReader {
	private Document document;			// xml document
	
	public XmlReader(String filename) {
		init(filename); 
	}
	
	public XmlReader(InputStream inputStream) {
		init(inputStream);
	}
	
	/*
	 * 输入文件初始化
	 */
	private void init(String filename) {
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(filename);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 输入流初始化
	 */
	private void init(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(inputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	// 取得单个node节点
	public Node getNode(String selected) {
		return document.selectSingleNode(selected);
	}
	
	// 取得多个node节点列表
	public List<Node> getNodes(String selected) {
		return document.selectNodes(selected);
	}
	
	// 取得单个element
	public Element getElement(String selected) {
		return (Element) getNode(selected);
	}
	
	// 取得多个element列表
	public List<Element> getElements(String selected) {
		return document.selectNodes(selected);
	}
	
	// 得到定义的内容
	public String getText(String selected) {
		Element element = getElement(selected);
		if (null == element)
			return null;
		return element.getText();
	}
	
	// 从给定的element中得到值的定义
	public String getText(Element parent, String xmlSql) {
		Element element = parent.element(xmlSql);
		if (null == element)
			return null;
		return element.getText();
	}
	
	// 从给定的element中得到属性的定义
	public String getAttribute(Element element, String name) {
		Attribute attr = element.attribute(name);
		if (null == attr)
			return null;
		
		return attr.getValue();
	}
	
	// 设置节点内容
	public void setText(String selected, String text) {
		getElement(selected).setText(text);
	}
}

