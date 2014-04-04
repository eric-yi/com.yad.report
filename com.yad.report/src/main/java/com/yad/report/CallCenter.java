// com.yad.report.CallCenter.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.yad.report.cache.CacheHelper;
import com.yad.report.engine.Engine;
import com.yad.report.engine.export.ExportResult;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:22:27 
 */
public class CallCenter {
	private static final Logger log = Logger.getLogger(CallCenter.class);	// log4j
	private static final CallCenter center = new CallCenter();
	public static final CallCenter getCenter() {
		return center;
	}
	
	
	private CallCenter() {
		
	}
	
	private Config config;
	
	/**
	 * 设置所有配置文件的顶级目录并初始化报表
	 * @param  @param dir
	 * @param  @param configPath
	 * @return void
	 * @throws Exception 
	 */
	public void init(String dir, String configPath) throws Exception {
		config = Config.getConfig();
		config.setDir(dir);
		config.config(configPath);
		CacheHelper.getHelper().loopCheck();
	}
	
	public void init(String configPath) throws Exception {
		config = Config.getConfig();
		config.config(configPath);
		CacheHelper.getHelper().loopCheck();
	}
	
	/**
	 * 调用具体报表
	 * @param  @param id
	 * @param  @param in
	 * @param  @param request
	 * @param  @return
	 * @param  @throws Exception
	 * @return String
	 * @throws
	 */
	public String call(String id, InputStream in, HttpServletRequest request) throws Exception {
		log.debug("reportId = " + id);
		Engine engine = config.getEngineMap().get(id);
		if (engine != null) {
			log.debug("Report Engine Exist!");
			if (config.getLoad() == LOAD.RUN) 	// 加载方式为run模式，每次刷新配置文件
				engine.init();
			if (request != null) engine.getContent().setRequest(request);
			return engine.query(in);
		}
		log.debug("Warnning, Report engine not exist!");
		return "";
	}
	
	public ExportResult export(String id, String key, OutputStream out) throws Exception {
		log.debug("reportId = " + id);
		Engine engine = config.getEngineMap().get(id);
		if (engine != null)
			return engine.exportExcel(key, out);
		
		log.debug("Warnning, Report engine not exist!");
		return null;
	}
}

