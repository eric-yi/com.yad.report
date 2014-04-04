// com.yad.report.engine.export.model.EXPORTER_POSITION.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export.model;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:50:32 
 */
public enum EXPORTER_POSITION {
	LEFT, RIGHT, CENTER;

	// 位置转换成excel表示的数字
	public short toExcel() {
		switch (this) {
		case LEFT:
			return 0;
		case CENTER:
			return 1;
		case RIGHT:
			return 2;
		}

		return 0;
	}
}
