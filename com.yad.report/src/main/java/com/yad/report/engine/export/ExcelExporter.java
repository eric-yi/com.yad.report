// com.yad.report.engine.export.excel.ExcelExporter.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;

import com.yad.report.common.Utils;
import com.yad.report.engine.export.model.ExporterRow;
import com.yad.report.engine.export.model.ExporterTable;
import com.yad.report.engine.export.model.ExporterText;
import com.yad.report.engine.export.model.ExporterUnit;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:56:27 
 */
public class ExcelExporter extends AbstractExporter {
	/**
	 * 导出成excel
	 * (non-Javadoc)
	 * @see com.yamu.dms.lib.imexp.AbstractExporter#export(java.io.OutputStream)
	 */
	@Override
	public void export(OutputStream out) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		CellStyle rowStyle = initCellStyle(workbook);	// 初始化行样式
		CellStyle cellStyle = initCellStyle(workbook);	// 初始化单元格样式
		HSSFRow textRow;
		HSSFCell textCell;
		HSSFRow dataRow;
		HSSFCell dataCell;
		int n = 1;
		for (ExporterTable table : tableList) {		// 处理每个sheet
			List<Integer> widthList = new ArrayList<Integer>();		// 列宽
			String title = table.getTitle();
			if (null == title || "".equals(title)) {
				title = "sheet" + n;
			} else {
				try {
					title = URLEncoder.encode(title, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					title = "sheet" + n;
				}
			}
			title = "sheet" + n;			// 乱码问题，暂不支持中文
			HSSFSheet  sheet = workbook.createSheet(); 
	        sheet.createFreezePane(0, 2);   //冻结前两行   zf update
			workbook.setSheetName(n-1, title);
			ExporterText text = table.getText();
			int x = 0;
			if (null != text.getText() && !"".equals(text.getText())) {
				textRow = sheet.createRow(x);				//创建信息行
				
				textRow.setHeight((short) 2400); 			// 设置行高   zf create
				CellRangeAddress range = new CellRangeAddress(0, 0, 0, 8); //参数1：行号   参数2：起始列号   参数3：行号   参数4：终止列号
				sheet.addMergedRegion(range);
				
				textCell = textRow.createCell(x);
				textCell.setCellValue(null==text.getText()?"":text.getText());
				if (null != text.getBackColor()) {
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
					cellStyle.setFillForegroundColor(Utils.colorToPoi(text.getBackColor(), workbook, false));
				}
				
				//强制换行  zf create
				cellStyle.setWrapText(true);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);//左右
				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//上下
				
				textCell.setCellStyle(cellStyle) ;
				x++;
			}
			// 每行数据
			List<ExporterRow> rowList = table.getRowList();
			for (ExporterRow row : rowList) {
				dataRow = sheet.createRow(x);	//数据行
				if (null != row.getBackColor()) {
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
					cellStyle.setFillForegroundColor(Utils.colorToPoi(row.getBackColor(), workbook, false));
				}
				rowStyle.setAlignment(row.getPosition().toExcel());
				dataRow.setRowStyle(rowStyle);			// 行样式
				dataRow.setHeightInPoints(row.getHeight());		// 行高
				// 单元格数据
				List<ExporterUnit> dataList = row.getDataList();
				int y = 0;
				for (ExporterUnit data : dataList) {
					dataCell = dataRow.createCell(y);
					Object d = data.getData();		// 设置单元格数据
					if (null == d) {
						dataCell.setCellValue(" ");
					} else {
						if (Number.class.isAssignableFrom(d.getClass())) {
							dataCell.setCellValue(Double.valueOf(d.toString()));
							dataCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						} else if (Date.class.isAssignableFrom(d.getClass())) {
							dataCell.setCellValue((Date) d);
						} else {
							dataCell.setCellValue(d.toString());
							dataCell.setCellType(HSSFCell.CELL_TYPE_STRING);
						}
					}
					if (null != data.getBackColor()) {
						cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置前景填充样式
						cellStyle.setFillForegroundColor(Utils.colorToPoi(data.getBackColor(), workbook, false));
					}
					cellStyle.setAlignment(data.getPosition().toExcel());
					dataCell.setCellStyle(cellStyle) ;
					// 设置列宽
					int width = data.getWidth();
					if (widthList.size() <= y || null == widthList.get(y)) {	// 宽度列表长度小于列下标或为空		
						widthList.add(y, width);
					} else if (width > widthList.get(y)) {	// 或小于当前宽度设置
						widthList.set(y, width);
					}
					sheet.setColumnWidth(y, widthList.get(y)*256);
					y++;
				}
				x++;
			}
			n++;
		}
		
		try {
			workbook.write(out);
		} catch (IOException e) {
			
		} 
	}

	/*
	 * 初始化单元格样式
	 */
	private CellStyle initCellStyle(HSSFWorkbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();	// 单元格样式
		// 设置边框 
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);  
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        
        // 设置字体
        HSSFFont font = workbook.createFont();  
        font.setFontHeightInPoints((short) 11);  
        font.setFontName("宋体");  
        cellStyle.setFont(font);  
        
        return cellStyle;
	}
}
