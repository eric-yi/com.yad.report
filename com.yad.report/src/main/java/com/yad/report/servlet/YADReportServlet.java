// com.yad.report.servlet.YMDReportServlet.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.servlet;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yad.report.CallCenter;
import com.yad.report.common.Utils;
import com.yad.report.engine.export.ExportResult;

/**
 * 报表引擎servlet的支持
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:22:26 
 */
public class YADReportServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(YADReportServlet.class); 		// log4j
	private CallCenter callCenter = CallCenter.getCenter(); 											// 报表调度中心

	@Override public void init() throws ServletException {
		// log4j初始化
		System.setProperty("webroot", getServletContext().getRealPath(""));
		String log4j = getServletContext().getRealPath("/")
				+ getInitParameter("log4j");
		PropertyConfigurator.configure(log4j); // log4j配置
		// 读取配置文件并初始化
		String configPath = getServletContext().getRealPath("/")
				+ getInitParameter("config");
		try {
			CallCenter.getCenter().init(new File(configPath).getParent(), configPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("enter DMSLibReport Engine...");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		if (request.getParameter("export") != null) {
			try {
				export(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String reportId = request.getParameter("reportId");
			if (null != reportId && !"".equals(reportId)) { // 报表查询
				query(request, response, reportId);
			} else { // 主页
	
			}
		}
	}
	
	private void query(HttpServletRequest request, HttpServletResponse response, String reportId) throws IOException {
		log.debug("start query");
		Document document = DocumentHelper.createDocument();// 建立xml
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement("parameters"); // 根节点
		Map<String, String> requestMap = getParameterMap(request);
		for (String key : requestMap.keySet()) {
			String value = requestMap.get(key);
			Element parameter = root.addElement("parameter");
			parameter.addAttribute("type", judgeType(value));
			parameter.addAttribute("name", key);
			parameter.addAttribute("value", value);
		}
		log.debug("xml = "+document.asXML().toString());
		
		// 执行报表引擎得到报表结果
		String result = "";
		try {
			result = callCenter.call(reportId, 
					new ByteArrayInputStream(document.asXML().getBytes()),request);
		} catch (Exception e) {
			log.error("report engine error", e);
		}
		log.debug(result);
		
		PrintWriter printer = response.getWriter();
		printer.println(result);
		log.debug("end query");
	}
	
	private void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String export_key = request.getParameter("export_key");
		String reportId = request.getParameter("reportId");
		if (null != export_key && !"".equals(export_key) &&
				null != reportId && !"".equals(reportId)) {
			response.reset();
			response.setBufferSize(1024 * 1024 * 1024 * 200);
			File tempfile = File.createTempFile("export", null); 
			OutputStream fileOs = new FileOutputStream(tempfile);
			OutputStream output = new  BufferedOutputStream(fileOs);
			ExportResult result = callCenter.export(reportId, export_key, output);
			output.flush();  
			output.close();
			response.setCharacterEncoding("GBK");  
			response.addHeader( "Content-Disposition", "attachment;filename="+new String(result.getName().getBytes("GBK"), "ISO8859-1"));  
			response.setContentType("application/octet-stream"); 
			FileInputStream fis = new FileInputStream(tempfile);
			byte[] buffer = new byte[1024 * 1024];
			while (fis.read(buffer) != -1) {
				response.getOutputStream().write(buffer);
			}
		}
	}

	/*
	 * 判读字符串符合的类型
	 */
	private String judgeType(String value) {
		if (Utils.isNumber(value))
			return "number";

		if (Utils.isDate(value))
			return "date";

		if (Utils.isTime(value))
			return "datetime";

		return "string";
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
}

