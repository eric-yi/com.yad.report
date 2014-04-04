// com.yad.report.engine.datasource.DataSourceHelper.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.yad.report.XmlParser;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:32:34 
 */
public class DataSourceHelper {
	private static final DataSourceHelper helper = new DataSourceHelper();
	public static final DataSourceHelper getHelper() {
		return helper;
	}
	
	private DataSourceHelper() {
		
	}
	
	private Map<String, IAccess> dsMap = new HashMap<String, IAccess>();
	
	public void init(String filename) {
		XmlParser parser = new XmlParser(filename);
		
		List<Element> elements = parser.getElements("/yads/datasource");
		for (Element element : elements) {
			String id = element.attributeValue("id");
			DATASOURCE_TYPE type = DATASOURCE_TYPE.toType(element.attributeValue("type"));
			IAccess access = null;
			switch (type) {
				case JDBC:
					JdbcDataSource datasource = parseJdbc(element);
					access = new JdbcAccess(datasource);
					access.init();
					break;
			}
			
			dsMap.put(id, access);
		}
	}
	
	private JdbcDataSource parseJdbc(Element element) {
		boolean usePool = Boolean.valueOf(element.attributeValue("usepool"));
		String driver = element.element("driver").attributeValue("value");
		String url = element.element("url").attributeValue("value");
		String username = element.element("username").attributeValue("value");
		String password = element.element("password").attributeValue("value");
		
		JdbcDataSource datasource = new JdbcDataSource();
		datasource.setUsePool(usePool);
		datasource.setDriver(driver);
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		if (usePool) { 
			int connections = Integer.valueOf(element.element("connections").attributeValue("value"));
			datasource.setConnections(connections);
		}
		
		return datasource;
	}
	
	public IAccess getAccess(String id) {
		return dsMap.get(id);
	}
}

