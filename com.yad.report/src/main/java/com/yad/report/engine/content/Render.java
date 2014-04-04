// com.yad.report.engine.content.Render.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.content;

import java.util.List;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		上午11:38:53 
 */
public class Render {
	private String title;
	private boolean rownum = false;
	private HEADER_TYPE type;
	private String handler;
	private List<Rank> columns;
	private List<Rank> rows;
	private List dataHandles;
	private Graph graph;
	private boolean export_support;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HEADER_TYPE getType() {
		return type;
	}

	public void setType(HEADER_TYPE type) {
		this.type = type;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public List<Rank> getColumns() {
		return columns;
	}

	public void setColumns(List<Rank> columns) {
		this.columns = columns;
	}

	public List<Rank> getRows() {
		return rows;
	}

	public void setRows(List<Rank> rows) {
		this.rows = rows;
	}

	public List getDataHandles() {
		return dataHandles;
	}

	public void setDataHandles(List dataHandles) {
		this.dataHandles = dataHandles;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public boolean isRownum() {
		return rownum;
	}

	public void setRownum(boolean rownum) {
		this.rownum = rownum;
	}
	
	public boolean isExport_support() {
		return export_support;
	}

	public void setExport_support(boolean export_support) {
		this.export_support = export_support;
	}

	public static class Rank {
		private RANK_TYPE type;
		private int index;
		private String name;
		public Rank() {
			
		}
		
		public Rank(RANK_TYPE type, int index, String name) {
			super();
			this.type = type;
			this.index = index;
			this.name = name;
		}
		public RANK_TYPE getType() {
			return type;
		}
		public void setType(RANK_TYPE type) {
			this.type = type;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public static class Graph {
		private GRAPH_TYPE type;
		private String title;
		private String handler = "";
		private String x_title;
		private String x_element;
		private String y_title;
		private String y_element;
		public Graph() {
			
		}
		public GRAPH_TYPE getType() {
			return type;
		}
		public void setType(GRAPH_TYPE type) {
			this.type = type;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getHandler() {
			return handler;
		}
		public void setHandler(String handler) {
			this.handler = handler;
		}
		public String getX_title() {
			return x_title;
		}
		public void setX_title(String x_title) {
			this.x_title = x_title;
		}
		public String getX_element() {
			return x_element;
		}
		public void setX_element(String x_element) {
			this.x_element = x_element;
		}
		public String getY_title() {
			return y_title;
		}
		public void setY_title(String y_title) {
			this.y_title = y_title;
		}
		public String getY_element() {
			return y_element;
		}
		public void setY_element(String y_element) {
			this.y_element = y_element;
		}
		
	}
}


