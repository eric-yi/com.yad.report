// com.yad.report.test.RunH2.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
 */

package com.yad.report.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

/**
 * TODO desc
 * 
 * @author eric
 * @version 0.1
 * @Date 2014-4-3 下午4:13:18
 */
public class H2Server {
	private Server server;
	private static final int port = 9999;

	public void start() {
		try {
			server = Server.createTcpServer(new String[] { "-tcpPort", String.valueOf(port)}).start();
			System.out.println("h2 started");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		if (server != null) {
			server.stop();
			System.out.println("h2 stoped");
		}
	}
}
