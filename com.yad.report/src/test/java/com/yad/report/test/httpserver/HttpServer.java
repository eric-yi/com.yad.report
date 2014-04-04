// com.yad.report.test.httpserver.HttpServer.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.test.httpserver;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-4		上午10:56:25 
 */
public class HttpServer {
	private HttpServerConfig config;
	
	public HttpServer(HttpServerConfig config) {
		this.config = config;
	}
	
	public  void start() throws Exception {
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
	    connector.setPort(config.getPort());
	    server.addConnector(connector);
		
		WebAppContext webapp = new WebAppContext();
		webapp.setResourceBase(config.getPath());
//		ResourceHandler resourceHandler = new ResourceHandler();
//		resourceHandler.setResourceBase(config.getPath());
		server.setHandler(webapp);
		
		server.start();
		server.join();
	}
}

