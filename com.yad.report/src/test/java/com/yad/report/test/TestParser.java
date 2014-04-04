// com.yad.report.test.TestParser.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.test;

import java.net.URL;

import junit.framework.TestCase;

import org.junit.Test;

import com.yad.report.engine.Engine;
import com.yad.report.engine.content.Content;
import com.yad.report.engine.datasource.DataSourceHelper;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午1:36:03 
 */
public class TestParser extends TestCase {
	@Test public void test() {
		URL url = TestParser.class.getResource("/com/yad/report/test/example.yadrpt");
		Engine engine = new Engine(url.getFile());
		engine.init();
		Content content = engine.getContent();
		System.out.println(content);
	}
	
	@Test public void testQuery() throws Exception {
		DataSourceHelper helper = DataSourceHelper.getHelper();
		URL url = TestParser.class.getResource("/com/yad/report/test/example.yadrpt");
		helper.init(url.getFile());
		
		url = TestParser.class.getResource("/com/yad/report/test/example.yadrpt");
		Engine engine = new Engine(url.getFile());
		engine.init();
		String result = engine.query(TestParser.class.getResourceAsStream("/com/yad/report/test/parameters.xml"));
		System.out.println(result);
	}
	
	public static void main(String[] args) throws Exception {
		junit.textui.TestRunner.run(TestParser.class);
	}
}


