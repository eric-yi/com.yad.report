// com.yad.report.engine.common.Utils.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.common;

import java.awt.Color;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:08:25 
 */
public class Utils {
	private static final String defaultDatePattern = "yyyyMMdd";						// 默认的日期样式
	private static final String defaultTimePattern = "yyyy-MM-dd HH:mm:ss";		// 默认的时间样式
	
	/**
	 * 类型转换 字符串转日期 精确到天
	 * @param  @param date
	 * @param  @return
	 * @param  @throws ParseException
	 * @return Date
	 * @throws
	 */
	public static Date strToDate(String date) throws ParseException { 
		return new SimpleDateFormat(defaultDatePattern).parse(date);
	}
	
	/**
	 * 类型转换 字符串转日期 精确到天
	 * @param  @param date
	 * @param  @return
	 * @param  @throws ParseException
	 * @return Date
	 * @throws
	 */
	public static Date strToTime(String date) throws ParseException { 
		return new SimpleDateFormat(defaultTimePattern).parse(date);
	}
	
	/**
	 * 验证数字输入
	 * @param  @param str
	 * @param  @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isNumber(String str) {
        String regex = "[+-]?[0-9]*(\\.[0-9]+)?";
        return match(regex, str);
    }
	
	/**
	 * 验证日期
	 * @param  @param str
	 * @param  @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isDate(String str) {
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))";
        return match(regex, str);
    }
	
	private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
	
	/**
	 * 验证时间
	 * @param  @param str
	 * @param  @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isTime(String str) {
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
        return match(regex, str);
    }
	
	/**
	 * 颜色转换成poi定义的下标
	 * @param  @param color
	 * @param  @param wb
	 * @param  @param isAdd
	 * @param  @return
	 * @return short
	 * @throws
	 */
	public static short colorToPoi(Color color, HSSFWorkbook wb, boolean isAdd) {
		HSSFPalette palette = wb.getCustomPalette();
		if (isAdd) {
			HSSFColor hsColor = palette.addColor((byte) color.getRed(),
					(byte) color.getGreen(), (byte) color.getBlue());
			return hsColor.getIndex();
		}
		palette.setColorAtIndex((short) 41, (byte) color.getRed(),
				(byte) color.getGreen(), (byte) color.getBlue());
		return 41;

	}
	
	/**
	 * 得到excel输入流的版本
	 * @param  @param inp
	 * @param  @return
	 * @return Excel_Version
	 * @throws
	 */
	public static EXCEL_VERSION checkVersion(InputStream inp)  {
		try {
		    if (!inp.markSupported()) {
		        return EXCEL_VERSION.OTHER;
		    }
		    if (POIFSFileSystem.hasPOIFSHeader(inp)) {
		    	return EXCEL_VERSION.XLS;
		    }
		    if (POIXMLDocument.hasOOXMLHeader(inp)) {
		       return EXCEL_VERSION.XLSX;
		    }
		} catch (Exception e) {
			
		}
	    
	    return null;
	}
	
	public static String getFilepath(String dir, String path) {
		try {
			URL u;
			if (path.startsWith("classpath:"))
				u = Utils.class.getResource(path.substring("classpath:".length()));
			else
				u = new URL(path);
			if (u != null)
				path = u.getFile();
		} catch (MalformedURLException e) {
			path = dir + path;
		}
		
		return path;
	}
}

