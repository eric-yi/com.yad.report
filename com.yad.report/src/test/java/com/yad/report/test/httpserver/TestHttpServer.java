// com.yad.report.test.httpserver.TestHttpServer.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.test.httpserver;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-4		上午11:06:33 
 */
public class TestHttpServer extends TestCase {
	private HttpServerConfig config;
	
	@Before public void setUp() {
		config = new HttpServerConfig();
		config.setPort(6666);
		config.setPath(TestHttpServer.class.getResource("/com/yad/report/test/web/").getFile());
	}

	@Test public void test() throws Exception {
		HttpServer server = new HttpServer(config);
		server.start();
	}
	
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestHttpServer.class);
	}
}

