// com.yad.report.engine.Engine.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.yad.report.cache.CacheContent;
import com.yad.report.cache.CacheHelper;
import com.yad.report.engine.content.CondtionIfs;
import com.yad.report.engine.content.Content;
import com.yad.report.engine.content.DataSet;
import com.yad.report.engine.content.DataSetParameter;
import com.yad.report.engine.content.HEADER_TYPE;
import com.yad.report.engine.content.RANK_TYPE;
import com.yad.report.engine.content.Render;
import com.yad.report.engine.content.Render.Rank;
import com.yad.report.engine.dataset.DatasetProxy;
import com.yad.report.engine.datasource.DataResult;
import com.yad.report.engine.datasource.DataSourceHelper;
import com.yad.report.engine.datasource.IAccess;
import com.yad.report.engine.export.ExportHelper;
import com.yad.report.engine.export.ExportResult;
import com.yad.report.engine.parameter.ParameterManager;
import com.yad.report.engine.render.RenderHelper;
import com.yad.report.engine.transfer.TransferProxy;

/**
 * 报表引擎入口
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:34:13 
 */
public class Engine {
	private static final Logger log = Logger.getLogger(Engine.class);	// log4j
	private Content content = null;
	private String engineFile;
	private CacheHelper cache = CacheHelper.getHelper();
	
	public Content getContent() {
		return content;
	}
	
	public Engine(String engineFile) {
		this.engineFile = engineFile;
	}
	
	/**
	 * 解析报表定义文件并产生content对像
	 */
	public void init() {
		content = Parser.parse(engineFile);
	}
	
	/**
	 * 进行查询
	 * @throws Exception 
	 */
	public String query(InputStream in) throws Exception {
		log.debug("makeParameters");
		Map<String, Object> parameters = makeParameters(in);		// 拼装输入参数
		content.setConvertParameters(parameters);
		log.debug("makeDatasets");
//		Map<String, List> dataMap = makeDatasets(parameters);	// 建立数据集
		KeyDataMap keyDataMap = makeDatasets(parameters);	// 建立数据集
		Map<String, List> dataMap = keyDataMap.getDataMap();
		List<List> transferList = new ArrayList<List>();						// 转换后的结果集 
		if (!dataMap.isEmpty()) {
			log.debug("transfer");
			transferList = transfer(dataMap);			// 数据转换成结果集
		}
		
		String key = String.valueOf(toKeyString(keyDataMap.getKeyList()).hashCode());
		log.debug("render");
		String results = render(key, transferList);
		
		log.debug("insert to cache");
		cache.addCache(key, new CacheContent(content.getId(), transferList));
		
		try {
			if (in != null)
				in.close();
		} catch (IOException e) {
		}
		
		return results;
	}
	
	public ExportResult exportExcel(String key, OutputStream out) throws Exception {
		Render render = content.getRender();
		CacheContent cc = CacheHelper.getHelper().getCache(key);
		Comparator rCom = new Comparator<Rank>() {
			@Override
			public int compare(Rank r1, Rank r2) {
				return r1.getIndex() - r2.getIndex();
			}
		};
		List<Rank> headerColumns = render.getColumns();
		Collections.sort(headerColumns, rCom);
		List<Rank> headerRows = render.getRows();
		Collections.sort(headerRows, rCom);
		// cross report need to add a column 
		if (headerColumns != null && !headerColumns.isEmpty() && render.getType() == HEADER_TYPE.CROSS)
			headerColumns.add(0, new Rank(RANK_TYPE.COLUMN, 0, ""));
		
		List<String> titleList = new ArrayList<String>();
		for (Rank r : headerColumns)
			titleList.add(r.getName());
		
		List<List> dataList = cc.getDataList();
		if (dataList  == null)
			dataList = new ArrayList();
		if (!dataList.isEmpty()) {
			int n = 0;
			for (Rank r : headerRows) {
				if (dataList.size() <= n) break;
				dataList.get(n).add(0, r.getName());
				n++;
			}
		}
		return ExportHelper.exportExcel(content.getName(), titleList, dataList, out);
	}
	
	private String toKeyString(List<String> strList) {
		StringBuffer buf = new StringBuffer();
		for (String str : strList)
			buf.append("{").append(str).append("}");
		return buf.toString();
	}
	
	/*
	 * 拼装输入参数map
	 */
	private Map<String, Object> makeParameters(InputStream in) throws Exception {
		ParameterManager paraManager = 
			new ParameterManager(in, content.getParameters(), content.getParameterHandlers());
		return paraManager.convert();
	}
	
	/*
	 * 拼装数据集
	 * 主要在相应的数据库上进行查询
	 */
	private KeyDataMap makeDatasets(Map<String, Object> parameters) throws Exception {
		List<String> keyList = new ArrayList<String>();
		Map<String, List> dataMap = new HashMap<String, List>();			// 查询后得到的结果集
		List<DataSet> datasets = content.getDatasets();
		// 按照order排序
		Collections.sort(datasets, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				DataSet d1 = (DataSet) o1;
				DataSet d2 = (DataSet) o2;
				return d1.getOrder() - d2.getOrder();
			}
		});
		
		// 调度器控制
		if (null != content.getDatasetHandler() && 
				!"".equals(content.getDatasetHandler())) {
			String schedulerClazz = content.getDatasetHandler(); 
			List<String> datasetNames = DatasetProxy.schedulerHandler(schedulerClazz, parameters);
			List<DataSet> schedulerDatasets = new ArrayList<DataSet>();
			// 调度器取得的数据集执行顺序代替定义的数据集List
			for (String datasetName : datasetNames) {
				if (null == datasetName || "".equals(datasetName))
					continue;
				for (DataSet d : datasets) {
					if (d.getName().equals(datasetName)) {
						schedulerDatasets.add(d);
						break;
					}
				}
			}
			datasets = schedulerDatasets;
		}
		
		// 查询得到相应的结果集
		for (DataSet dataset : datasets) {
			IAccess access = DataSourceHelper.getHelper().getAccess(dataset.getDatasource());	// 取得数据库定义
			log.debug("access = " + access);
			if (null != access) {
				// 得到dataset输入参数
				Map<String, String> replaces = new HashMap<String, String>();			// 替代变更
				Map<Integer, Object> inputParameters = new TreeMap<Integer, Object>();	// 参数, treemap自动排序
				// 前面输入定义的参数
				Map<Integer, DataSetParameter> parameterMap = dataset.getParameters();
				repalceAndParameter(parameterMap, replaces, inputParameters, parameters);
				// 由之前结果集得到的参数
				Map<String, String> handlerMap = dataset.getHandlers();
				for (String handler : handlerMap.keySet()) {
					Map<String, List> inputMap = new HashMap<String, List>();
					inputMap.put(handler, dataMap.get(handlerMap.get(handler)));
					Map<Integer, DataSetParameter> proxyMap = DatasetProxy.datasetHandler(inputMap);
					repalceAndParameter(proxyMap, replaces, inputParameters, parameters);
				}
				
				// 转换成list
				List<Object> parameterList = new ArrayList<Object>();
				String s = dataset.getStatement();
				int sulen = s.indexOf("from");
				String sl = s.substring(0, sulen+4);
				String esl = s.substring(sulen+4,s.length());
				
				String cond = "";
				Map<Integer, ArrayList<CondtionIfs>> ifsmap = dataset.getIfs();
				for (int index : inputParameters.keySet()) {
					if (inputParameters.get(index)!=null && !"".equals(inputParameters.get(index))){
						parameterList.add(inputParameters.get(index));
						//拼装<if></if>条件
						if (ifsmap.get(index)!=null){
							ArrayList<CondtionIfs> ifs = (ArrayList)ifsmap.get(index);
							for (CondtionIfs c:ifs){
								if (c.getValue().equals(inputParameters.get(index).toString())){
									sl += c.getText();
								}
							}
						}
						//拼装condtions条件
						DataSetParameter p = parameterMap.get(index);
						String sfp = parameters.get(p.getName())==null?"null":parameters.get(p.getName()).toString();
						if (dataset.getCondtions().get(index)!=null && !"null".equals(sfp))
							cond += dataset.getCondtions().get(index);
						
					}
				}
				sl =sl+esl+cond;
				dataset.setStatement(sl);
				String name = dataset.getName();
				DataResult result = access.queryResult(dataset.getStatement(), replaces, parameterList);
				keyList.add(result.getQueryStr());
				List list = result.getDataList();
				dataMap.put(name, list);
			}
		}
		
		return new KeyDataMap(keyList, dataMap);
	}
	
	/*
	 * 区分replace和parameter
	 */
	private void repalceAndParameter(
			Map<Integer, DataSetParameter> map, 
			Map<String, String> replaceMap, 
			Map<Integer, Object> parameterMap,
			Map<String, Object> parameters) {
		for (Integer index : map.keySet()) {
			DataSetParameter p = map.get(index);
			switch (p.getType()) {
				case REPLACE:
					replaceMap.put(p.getName(), parameters.get(p.getName()).toString());
					break;
				case PARAMETER:
					parameterMap.put(index, parameters.get(p.getName()));
					break;
			}
		}
	}
	
	/*
	 * 将上面的dataMap，里面是多个list转换后得到一个list
	 */
	private List<List> transfer(Map<String, List> dataMap) throws Exception {
		String handler = content.getTransfer().getHandler();
		if (handler == null || "".equals(handler)) {		// 不需要对结果集转换处理
			for (String key : dataMap.keySet()) {
				return dataMap.get(key);
			}
			return new ArrayList<List>();
		} else {			// 再次对结果集合转换处理
			return TransferProxy.handler(handler, dataMap);
		}
	}
	
	/*
	 * 渲染结果集
	 */
	@Deprecated
	private String render(List<List> transferList) throws Exception {
		int n = 1;
		if (content.getRender().isRownum()) {
			n = 1;
			for (List transfer : transferList) {
				transfer.add(0, n++);
			}
		}
		if (content.getRender().getHandler()!= null && !"".equals(content.getRender().getHandler())){
			return DatasetProxy.renderHandler(content, transferList);
		}
		
		String url_path = null;
		if (content.getRender() != null) {
			HttpServletRequest request = content.getRequest();
			url_path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
		return RenderHelper.createResults(url_path, content.getId(), "", content.getRender(), transferList);
	}
	
	private String render(String key, List<List> transferList) throws Exception {
		int n = 1;
		if (content.getRender().isRownum()) {
			n = 1;
			for (List transfer : transferList) {
				transfer.add(0, n++);
			}
		}
		if (content.getRender().getHandler()!= null && !"".equals(content.getRender().getHandler())){
			return DatasetProxy.renderHandler(content, transferList);
		}
		Render render = content.getRender();
		render.setExport_support(true);
		
		String url_path = null;
		if (content.getRender() != null) {
			HttpServletRequest request = content.getRequest();
			url_path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		}
		return RenderHelper.createResults(url_path, content.getId(), key, render, transferList);
	}
	
	private static class KeyDataMap {
		private List<String> keyList;
		private Map<String, List> dataMap;
		public KeyDataMap(List<String> keyList, Map<String, List> dataMap) {
			super();
			this.keyList = keyList;
			this.dataMap = dataMap;
		}
		public List<String> getKeyList() {
			return keyList;
		}
		public Map<String, List> getDataMap() {
			return dataMap;
		}
	}
}

