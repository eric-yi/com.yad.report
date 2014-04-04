// com.yad.report.engine.export.model.ExporterStyle.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
 */

package com.yad.report.engine.export.model;

import java.awt.Color;
import java.awt.Font;

/**
 * 导出样式定义
 * @author eric
 * @version 0.1
 * @Date 2014-4-3 下午12:48:07
 */
public class ExporterStyle {
	protected Color backColor; // 颜色　背景色
	protected Color foreColor; // 数据颜色　前景色
	protected Font font; // 字体
	protected EXPORTER_POSITION position = EXPORTER_POSITION.LEFT; // 位置
	protected int height = 15; // 高度
	protected int width = 10; // 宽度 10个字符

	// getter and setter
	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public EXPORTER_POSITION getPosition() {
		return position;
	}

	public void setPosition(EXPORTER_POSITION position) {
		this.position = position;
	}

	public void setPosition(String position) {
		if ("left".equalsIgnoreCase(position))
			this.position = EXPORTER_POSITION.LEFT;
		if ("center".equalsIgnoreCase(position))
			this.position = EXPORTER_POSITION.CENTER;
		if ("right".equalsIgnoreCase(position))
			this.position = EXPORTER_POSITION.RIGHT;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Color getForeColor() {
		return foreColor;
	}

	public void setForeColor(Color foreColor) {
		this.foreColor = foreColor;
	}
}
