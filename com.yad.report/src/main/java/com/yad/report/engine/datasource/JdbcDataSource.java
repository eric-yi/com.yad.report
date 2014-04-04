// com.yad.report.engine.datasource.JdbcDataSource.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.datasource;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:32:54 
 */
public class JdbcDataSource extends DataSource {
	private boolean usePool;			// 是否使用连接池
	private String driver;					// 驱动
	private String url;						// 连接url
	private String username;			// 用户名
	private String password;			// 密码
	private int connections;				// 连接数
	
	
	// getter and setter
	public boolean isUsePool() {
		return usePool;
	}
	public void setUsePool(boolean usePool) {
		this.usePool = usePool;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getConnections() {
		return connections;
	}
	public void setConnections(int connections) {
		this.connections = connections;
	}
}

