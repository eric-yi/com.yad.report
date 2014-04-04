// com.yad.report.engine.Parser.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
 */

package com.yad.report.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.yad.report.XmlParser;
import com.yad.report.common.DATA_TYPE;
import com.yad.report.engine.content.CALL;
import com.yad.report.engine.content.CondtionIfs;
import com.yad.report.engine.content.Content;
import com.yad.report.engine.content.DATASETPARAMETER_TYPE;
import com.yad.report.engine.content.DataSet;
import com.yad.report.engine.content.DataSetParameter;
import com.yad.report.engine.content.ExcelParameter;
import com.yad.report.engine.content.GRAPH_TYPE;
import com.yad.report.engine.content.HEADER_TYPE;
import com.yad.report.engine.content.Parameter;
import com.yad.report.engine.content.RANK_TYPE;
import com.yad.report.engine.content.Render;
import com.yad.report.engine.content.Render.Graph;
import com.yad.report.engine.content.Render.Rank;
import com.yad.report.engine.content.Transfer;

/**
 * TODO desc
 * @author eric
 * @version 0.1
 * @Date 2014-4-3 上午11:34:22
 */
class Parser {
	public static Content parse(String filename) {
		XmlParser parser = new XmlParser(filename);

		String id = parser.getText("/yadrpt/id");
		String name = parser.getText("/yadrpt/name");
		CALL call = CALL.toCall(parser.getText("/yadrpt/call"));
		String outpage = parser.getText("/yadrpt/outpage");

		// parameters
		List<Element> parametersEle = parser
				.getElements("/yadrpt/parameters/replace");
		List<Parameter> parameters = new ArrayList<Parameter>();
		List<String> parameterHandlers = new ArrayList<String>();
		for (Element parameterEle : parametersEle) {
			String parameterHandler = null;
			if ((parameterHandler = parameterEle.attributeValue("handler")) != null) {
				parameterHandlers.add(parameterHandler);
			} else {
				Parameter parameter = new Parameter();
				String parameterName = parameterEle.attributeValue("name");
				parameter.setName(parameterName);
				parameter.setValue(parameterEle.attributeValue("value"));
				parameter.setType(DATA_TYPE.toType(parameterEle.attributeValue("type")));
				parameters.add(parameter);
			}
		}

		// datasets
		String datasetHandler = null;
		Element schedulerEle = parser.getElement("/yadrpt/datasets/scheduler");
		if (null != schedulerEle) {
			if (null != schedulerEle.attributeValue("handler")
					&& !"".equals(schedulerEle.attributeValue("handler"))) {
				datasetHandler = schedulerEle.attributeValue("handler");
			}
		}
		List<Element> datasetsEle = parser
				.getElements("/yadrpt/datasets/dataset");
		List<DataSet> datasets = new ArrayList<DataSet>();
		for (Element datasetEle : datasetsEle) {
			DataSet dataset = new DataSet();
			String datasetName = datasetEle.attributeValue("name");
			dataset.setName(datasetName);
			String datasource = datasetEle.attributeValue("datasource");
			dataset.setDatasource(datasource);
			if (datasetEle.attributeValue("order") != null)
				dataset.setOrder(Integer.valueOf(datasetEle
						.attributeValue("order")));

			List list = datasetEle.elements("parameter");
			Map<Integer, DataSetParameter> datasetParameters = new HashMap<Integer, DataSetParameter>();
			Map<String, String> datasetHandlers = new HashMap<String, String>();
			for (Object o : list) {
				if (o instanceof Element) {
					Element e = (Element) o;
					String parameterHandler = null;
					if ((parameterHandler = e.attributeValue("handler")) != null) {
						datasetHandlers.put(parameterHandler,
								e.attributeValue("dataset"));

					} else {
						DataSetParameter datasetParameter = new DataSetParameter(
								DATASETPARAMETER_TYPE.toType(e
										.attributeValue("type")),
								e.attributeValue("name"));
						datasetParameters.put(
								Integer.valueOf(e.attributeValue("index")),
								datasetParameter);
					}
				}
			}
			dataset.setParameters(datasetParameters);

			Map<Integer, String> datasetCondtions = new HashMap<Integer, String>();

			List conds = datasetEle.elements("condtion");
			for (Object o : conds) {
				if (o instanceof Element) {
					Element e = (Element) o;
					datasetCondtions.put(
							Integer.valueOf(e.attributeValue("index")),
							e.getText());
				}
			}
			dataset.setCondtions(datasetCondtions);

			Map<Integer, ArrayList<CondtionIfs>> ifsmap = new HashMap<Integer, ArrayList<CondtionIfs>>();
			List ifse = datasetEle.elements("statement");
			for (Object o : ifse) {
				if (o instanceof Element) {
					Element e = (Element) o;
					List ifs = e.elements("if");
					ArrayList<Integer> inlist = new ArrayList<Integer>();

					for (Object fo : ifs) {
						if (fo instanceof Element) {
							Element fe = (Element) fo;
							inlist.add(Integer.valueOf(fe
									.attributeValue("index")));
						}
					}
					removeSameItem(inlist);
					for (Integer lis : inlist) {
						ArrayList<CondtionIfs> coli = new ArrayList<CondtionIfs>();
						for (Object fo : ifs) {
							if (fo instanceof Element) {
								Element fe = (Element) fo;
								Integer idx = Integer.valueOf(fe
										.attributeValue("index"));
								if (lis.equals(idx)) {
									CondtionIfs ifcon = new CondtionIfs();
									ifcon.setIndex(idx);
									ifcon.setValue(fe.attributeValue("value"));
									ifcon.setText(fe.getText());
									coli.add(ifcon);
								}
							}
						}
						ifsmap.put(lis, coli);
					}
				}
			}

			dataset.setIfs(ifsmap);

			dataset.setStatement(datasetEle.element("statement").getText());

			datasets.add(dataset);
		}

		// transfer
		Transfer transfer = new Transfer();
		transfer.setHandler(parser.getText("/yadrpt/transfer/handler"));

		// render
		Render render = new Render();
		Element renderEle = parser.getElement("/yadrpt/render");
		render.setTitle(renderEle.attributeValue("title"));
		if (null != renderEle.attributeValue("rownum")) {
			if ("open".equalsIgnoreCase(renderEle.attributeValue("rownum"))) {
				render.setRownum(true);
			} else {
				render.setRownum(false);
			}
		}
		Element headerEle = parser.getElement("/yadrpt/render/header");
		render.setType(HEADER_TYPE.toType(headerEle.attributeValue("type")));
		render.setHandler(headerEle.attributeValue("handler"));
		List<Rank> columns = new ArrayList<Rank>();
		List<Element> columnsEle = parser
				.getElements("/yadrpt/render/header/columns/column");
		for (Element columnEle : columnsEle) {
			Rank rank = new Rank();
			rank.setIndex(Integer.valueOf(columnEle.attributeValue("index")));
			rank.setName(columnEle.attributeValue("name"));
			rank.setType(RANK_TYPE.COLUMN);
			columns.add(rank);
		}
		render.setColumns(columns);
		List<Rank> rows = new ArrayList<Rank>();
		List<Element> rowsEle = parser
				.getElements("/yadrpt/render/header/rows/row");
		for (Element rowEle : rowsEle) {
			Rank rank = new Rank();
			rank.setIndex(Integer.valueOf(rowEle.attributeValue("index")));
			rank.setName(rowEle.attributeValue("name"));
			rank.setType(RANK_TYPE.COLUMN);
			rows.add(rank);
		}
		render.setRows(rows);
		List<String> dataHandles = new ArrayList<String>();
		List<Element> handlersEle = parser
				.getElements("/yadrpt/render/datas/handler");
		for (Element handlerEle : handlersEle) {
			dataHandles.add(handlerEle.getText());
		}
		render.setDataHandles(dataHandles);
		Graph graph = new Graph();
		Element graphEle = parser.getElement("/yadrpt/render/graph");
		graph.setType(GRAPH_TYPE.toType(graphEle.attributeValue("type")));
		graph.setTitle(graphEle.attributeValue("title"));
		graph.setHandler(graphEle.attributeValue("handler"));
		Element xGraphEle = parser.getElement("/yadrpt/render/graph/x");
		graph.setX_title(xGraphEle.attributeValue("title"));
		graph.setX_element(xGraphEle.attributeValue("element"));
		Element yGraphEle = parser.getElement("/yadrpt/render/graph/y");
		graph.setY_title(xGraphEle.attributeValue("title"));
		graph.setY_element(xGraphEle.attributeValue("element"));
		render.setGraph(graph);

		// excel
		Element excelEle = parser.getElement("/yadrpt/export/excel");
		ExcelParameter excel = new ExcelParameter();
		excel.setTemplate(excelEle.elementText("template"));
		excel.setFilename(excelEle.elementText("filename"));

		// 合并成content
		Content content = new Content();
		content.setId(id);
		content.setName(name);
		content.setCall(call);
		content.setOutpage(outpage);
		content.setParameters(parameters);
		content.setParameterHandlers(parameterHandlers);
		content.setDatasets(datasets);
		content.setDatasetHandler(datasetHandler);
		content.setTransfer(transfer);
		content.setRender(render);
		content.setExcel(excel);

		return content;
	}

	/**
	 * 根据参数list，找出相同的元素，使数组里面的值，保存唯一
	 */
	public static void removeSameItem(ArrayList<Integer> item) {
		for (int i = 0; i < item.size(); i++) {
			for (int j = i + 1; j < item.size(); j++) {
				if (item.get(i).equals(item.get(j))) {
					item.remove(j);
					removeSameItem(item);
				}
			}
		}
	}
}
