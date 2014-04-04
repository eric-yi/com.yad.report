// com.yad.report.test.TestH2.java
/**
 * YADReport Report Engine 
 * For my best lovingness
 * ------------------------------------------------------------------------
 * Copyright (c) 2014,  Eric yi All Rights Reserved.
*/


package com.yad.report.test;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO desc
 * @author   eric
 * @version  0.1
 * @Date	 2014-4-3		下午4:26:24 
 */
public class TestH2 extends TestCase {
	private static String dbDir;
	private static String db;
	private static final String username = "yad";
	private static final String password = "yad";
	private H2Server server;
	
	@Before public void setUp() {
		URL url = TestH2.class.getResource("/com/yad/report/test/");
		dbDir = url.getFile() + File.separator + "db";
		db = dbDir + File.separator + "test";
		delFolder(dbDir);
		server = new H2Server();
		server.start();
	}
	
	private void delFolder(String folderPath) {
		File dir = new File(folderPath);
		if (!dir.exists() || !dir.isDirectory())
			return;
		
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	public void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	
	@Test public void test() throws Exception {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:" + db, username, password);
		Statement stat = conn.createStatement();
		// insert data
		stat.execute("create table test(id int, name varchar)");
		stat.execute("insert into test values(1, 'eric')");

		// use data
		ResultSet result = stat.executeQuery("select * from test");
		System.out.println("id" + "\t" + "name");
		System.out.println("-------------------------");
		while (result.next()) {
			System.out.println(result.getInt("id") + "\t" + result.getString("name"));
		}
		result.close();
		stat.close();
		conn.close();
	}
	
	@After public void tearDown() {
		server.stop();
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestH2.class);
	}
}

