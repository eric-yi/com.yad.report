// com.yad.report.Config.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.yad.report.common.Utils;
import com.yad.report.engine.Engine;
import com.yad.report.engine.content.Content;
import com.yad.report.engine.datasource.DataSourceHelper;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:24:22 
 */
class Config {
	private static final Logger log = Logger.getLogger(Config.class);	// log4j
	
	private static final Config config = new Config();
	public static final Config getConfig() {
		return config;
	}
	
	private Config() {
		
	}
	
	private Map<String, Engine> engineMap = new HashMap<String, Engine>();	// 报表引擎集合
	public Map<String, Engine> getEngineMap() {
		return engineMap;
	}

	private LOAD load;		// 加载方式  init，初始化加载 run，运行时加载
	
	public LOAD getLoad() {
		return load;
	}
	
	private String dir = "";		// 文件路径前缀
	
	void setDir(String dir) {
		this.dir = dir;
	}
	
	
	void config(String configPath) {
		if (dir != null && !"".equals(dir))
			dir += System.getProperty("file.separator");
		
		XmlParser configParser = new XmlParser(configPath);
		// 初始化数据源池
		DataSourceHelper helper = DataSourceHelper.getHelper();
		String yads =Utils.getFilepath(dir, configParser.getText("/yadrptconfig/yads"));
		helper.init(yads);
		// 初始化各个报表定义
		load = LOAD.toLoad(configParser.getText("/yadrptconfig/reports/load"));		// 报表加载方式 init，初始化时加载 run，运行时加载s
		List<Element> pathsEle = configParser.getElements("/yadrptconfig/reports/report/path");
		for (Element pathEle : pathsEle) {
			String path =Utils.getFilepath(dir, pathEle.getTextTrim());
			if (!"".equals(path)) {
				Engine engine = new Engine(path);
				engine.init();
				Content content = engine.getContent();
				engineMap.put(content.getId(), engine);
				log.debug("load report \"" + content.getName() + "\"");
			}
		}
	}
}

