// com.yad.report.engine.parameter.ParameterManager.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.parameter;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.yad.report.XmlParser;
import com.yad.report.common.DATA_TYPE;
import com.yad.report.engine.content.Parameter;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:00:15 
 */
public class ParameterManager {
	private List<Parameter> parameters;
	private List<String> handlers;
	private InputStream inputStream;
	
	public ParameterManager(InputStream inputStream, List<Parameter> parameters, List<String> handlers) {
		this.inputStream = inputStream;
		this.parameters = parameters;
		this.handlers = handlers;
	}
	
	public Map<String, Object> convert() {
		Map<String, Object> coParameters = new HashMap<String, Object>();
		
		XmlParser parser = new XmlParser(inputStream);
		List<Element> list = parser.getElements("/parameters/parameter");
		Map<String, InputParameter> inputs = new HashMap<String, InputParameter>();
		for (Element element : list) {
			InputParameter parameter = new InputParameter();
			String name = element.attributeValue("name");
			DATA_TYPE type = DATA_TYPE.toType(element.attributeValue("type"));
			String value = element.attributeValue("value");
			parameter.setName(name);
			parameter.setType(type);
			parameter.setValue(value);
			inputs.put(name, parameter);
			
			coParameters.put(name, tranfeValue(type,value));
		}
		
		for (Parameter parameter : parameters) {
			Object value = parameter.getValue();
			DateFormat format;
			switch (parameter.getType()) {
				case STRING:
				default:
					break;
				case NUMBER:
					NumberFormat n = NumberFormat.getInstance();
					try {
						value = n.parse(parameter.getValue());
					} catch (ParseException e) {
					}
					break;
				case DATE:
					format = new SimpleDateFormat("yyyy-MM-dd");
					try {
						value = format.parse(parameter.getValue());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case DATETIME:
					format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					try {
						value = format.parse(parameter.getValue());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
			}
			coParameters.put(parameter.getName(), value);
		}
		
		for (String handler : handlers) {
			try {
				Map<String, Object> map = invoke(handler, inputs);
				for (String key : map.keySet()) {
					coParameters.put(key, map.get(key));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return coParameters;
	}
	private Object tranfeValue(DATA_TYPE type,String value){
		Object objvalue = "";
		DateFormat format;
		switch(type){
			case STRING:
				objvalue = value;
			default:
				break;
			case NUMBER:
				NumberFormat n = NumberFormat.getInstance();
				try {
					objvalue = n.parse(value);
				} catch (ParseException e) {
				}
				break;
			case DATE:
				format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					objvalue = format.parse(value);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case DATETIME:
				format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					objvalue = format.parse(value);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		}
		return objvalue;
	}
	private Map<String, Object> invoke(String className, Map<String, InputParameter> inputs) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		if (className != null && !"".equals(className)) {
			Class c= Class.forName(className);
			if (ParameterHandler.class.isAssignableFrom(c)) {
				ParameterHandler handler = (ParameterHandler) c.newInstance();
				parameterMap = handler.handle(inputs);
			}
		}
		
		return parameterMap;
	}
}	

