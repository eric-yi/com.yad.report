// com.yad.report.engine.datasource.JdbcAccess.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.engine.datasource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午12:32:47 
 */
public class JdbcAccess implements IAccess {
	private static final Logger log = Logger.getLogger(JdbcAccess.class);	// log4j
	private ComboPooledDataSource DBPool = null;
	private JdbcDataSource datasource;

	public JdbcAccess(JdbcDataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public void init() {
		DBPool = new ComboPooledDataSource();
		try {
			DBPool.setDriverClass(datasource.getDriver());
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // JDBC驱动
		DBPool.setJdbcUrl(datasource.getUrl()); // 数据库URL
		DBPool.setUser(datasource.getUsername()); // 用户名
		DBPool.setPassword(datasource.getPassword()); // 密码

		if (datasource.isUsePool()) {
			int connections = datasource.getConnections();
			DBPool.setInitialPoolSize(connections); // 初始化连接池大小
			DBPool.setMinPoolSize(connections); // 最少连接数
			DBPool.setMaxPoolSize(connections); // 最大连接数
			DBPool.setAcquireIncrement(1); // 连接数的增量
			DBPool.setIdleConnectionTestPeriod(3000); // 测连接有效的时间间隔 3000毫秒
			DBPool.setTestConnectionOnCheckout(true); // 每次连接验证连接是否可用 这里为验证
		} else {
			DBPool.setMaxPoolSize(1); // 连接数
		}
	}

	@Override
	public Connection getConnection() {
		try {
			return DBPool.getConnection();
		} catch (SQLException e) {

		}

		return null;
	}

	@Override
	public List<Map> query(String sql) {
		List<Map> dataList = new ArrayList<Map>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			stmt = con.prepareStatement(sql);
			stmt.execute();
			rs = stmt.getResultSet();
			ResultSetMetaData metaData = rs.getMetaData();	// 取得列信息
			int cols = metaData.getColumnCount();			// 列长度
			while (rs.next()) {
				int n = 1;
				Map<String, Object> entry = new HashMap<String, Object>();		// 每行的数据
				for (; n <= cols; n++) {		// 将从数据库查询到单元格的数据插入entry中 
					entry.put(metaData.getColumnName(n).toLowerCase(), rs.getObject(n));	// map key为小写英文字母
				}
				dataList.add(entry);			// 行数据装载入结果集List中
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			attemptClose(rs);  
            attemptClose(stmt);  
            attemptClose(con);  
			return dataList;
		}
	}
	
	@Override
	public List query(String sql, Map<String, String> replaces, List<Object> parameters) {
		long startTime = System.currentTimeMillis();
		List<List> dataList = new ArrayList<List>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			// 替换sql语句
			Matcher matcher;
			for (String key : replaces.keySet()) {
				matcher = Pattern.compile("#" + key + "#").matcher(sql);
				sql = matcher.replaceAll(replaces.get(key));
			}
			log.info("query sql: \n" + sql);
			
			stmt = con.prepareStatement(sql);
			int count = countParamter(sql);
			int n = 1;
			while (n <= count) {
				// 输入参数
				stmt.setObject(n, parameters.get(n-1));
				log.info("parameter: " + (n-1) + " -> " + parameters.get(n-1));
				n++;
			}
			stmt.execute();
			rs = stmt.getResultSet();
			ResultSetMetaData metaData = rs.getMetaData();	// 取得列信息
			int cols = metaData.getColumnCount();			// 列长度
			while (rs.next()) {
				n = 1;
//				Map<String, Object> entry = new HashMap<String, Object>();		// 每行的数据
//				Map<String, Object> entry = new LinkedHashMap<String, Object>();		// 每行的数据
				List entry = new ArrayList();			// 每行数据改为List
				for (; n <= cols; n++) {		// 将从数据库查询到单元格的数据插入entry中 
//					entry.put(metaData.getColumnName(n).toLowerCase(), rs.getObject(n));	// map key为小写英文字母
					entry.add(rs.getObject(n));
				}
				dataList.add(entry);			// 行数据装载入结果集List中
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			attemptClose(rs);  
            attemptClose(stmt);  
            attemptClose(con);  
            log.info("query  elapsed time : " + (System.currentTimeMillis() - startTime) + "ms");
			return dataList;
		}
	}
	
	@Override
	public DataResult queryResult(String sql, Map<String, String> replaces,  List<Object> parameters) {
		long startTime = System.currentTimeMillis();
		List<List> dataList = new ArrayList<List>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String new_sql = sql;
		try {
			con = getConnection();
			// 替换sql语句
			Matcher matcher;
			for (String key : replaces.keySet()) {
				matcher = Pattern.compile("#" + key + "#").matcher(new_sql);
				new_sql = matcher.replaceAll(replaces.get(key));
			}
			log.info("query sql: \n" + new_sql);
			
			stmt = con.prepareStatement(new_sql);
			int count = countParamter(new_sql);
			int n = 1;
			while (n <= count) {
				// 输入参数
				stmt.setObject(n, parameters.get(n-1));
				log.info("parameter: " + (n-1) + " -> " + parameters.get(n-1));
				n++;
			}
			stmt.execute();
			rs = stmt.getResultSet();
			ResultSetMetaData metaData = rs.getMetaData();	// 取得列信息
			int cols = metaData.getColumnCount();			// 列长度
			while (rs.next()) {
				n = 1;
//				Map<String, Object> entry = new HashMap<String, Object>();		// 每行的数据
//				Map<String, Object> entry = new LinkedHashMap<String, Object>();		// 每行的数据
				List entry = new ArrayList();			// 每行数据改为List
				for (; n <= cols; n++) {		// 将从数据库查询到单元格的数据插入entry中 
//					entry.put(metaData.getColumnName(n).toLowerCase(), rs.getObject(n));	// map key为小写英文字母
					entry.add(rs.getObject(n));
				}
				dataList.add(entry);			// 行数据装载入结果集List中
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			attemptClose(rs);  
            attemptClose(stmt);  
            attemptClose(con);  
            log.info("query  elapsed time : " + (System.currentTimeMillis() - startTime) + "ms");
            return new DataResult(new_sql, dataList);
		}
	}

	private void attemptClose(ResultSet o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void attemptClose(Statement o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void attemptClose(Connection o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 计算sql语句中出现参数的个数
	 */
	private int countParamter(String sql) {
		char[] cs = sql.toCharArray();
		int count = 0;
		int index = 0;
		while (index < cs.length) {
			if (cs[index] == '?') {
				count++;
			}
			index++;
		}
		
		return count;
	}
}

