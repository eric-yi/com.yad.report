// com.yad.report.test.TestCallCenter.java
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

import com.yad.report.CallCenter;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午1:35:21 
 */
public class TestCallCenter extends TestCase {
	@Test
	public void testInit() throws Exception {
		URL url = TestCallCenter.class.getResource("/com/yad/report/test/test.yadrptconfig");
		CallCenter.getCenter().init(url.getFile());
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestCallCenter.class);
	}
}

