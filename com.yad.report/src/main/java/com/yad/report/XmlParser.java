// com.yad.report.XmlParser.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:24:40 
 */
public class XmlParser {
	private Document document;
	
	public XmlParser(String filename) {
		init(filename); 
	}
	
	public XmlParser(InputStream inputStream) {
		init(inputStream);
	}
	
	private void init(String filename) {
		try {
			FileInputStream in = new FileInputStream(filename);
			init(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void init(InputStream inputStream) {
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(inputStream);
			System.out.println(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public Node getNode(String xmlSql) {
		return document.selectSingleNode(xmlSql);
	}
	
	
	public List<Node> getNodes(String xmlSql) {
		return document.selectNodes(xmlSql);
	}
	
	public Element getElement(String xmlSql) {
		return (Element) getNode(xmlSql);
	}
	
	public List<Element> getElements(String xmlSql) {
		return document.selectNodes(xmlSql);
	}
	
	public String getText(String xmlSql) {
		Element element = getElement(xmlSql);
		if (element == null)
			return null;
		return element.getText();
	}
	
}
