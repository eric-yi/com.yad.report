// com.yad.report.test.engine.TestExport.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.yad.report.CallCenter;
import com.yad.report.cache.CacheHelper;
/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午1:33:40 
 */
public class TestExport extends TestCase {
	@Before
	protected void setUp() throws Exception {
		URL url = TestExport.class.getResource("/com/yad/report/test/test.yadrptconfig");
		CallCenter.getCenter().init(url.getFile());
	}

	@Test
	public void test() throws Exception {
		CallCenter.getCenter().call(
				"test1", 
				TestExport.class.getResourceAsStream("/com/yad/report/test/parameters.xml"),
				null);
		String key = CacheHelper.getHelper().getCacheKeys()[0];
		OutputStream out = new FileOutputStream("export.xls");
		CallCenter.getCenter().export("test1", key, out);
		
		out.close();
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestExport.class);
	}
}

