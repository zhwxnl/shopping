package com.woniuxy.BaseDao.BaseImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//连接数据库
public class JDBCHelper {

	// 单例模式
	private static Connection conn = null;

	// 获取连接
	public static Connection getConn() {
		if(conn!=null) {
			return conn;
		}
		// 加载驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 读取配置文件
			Properties pr = new Properties();
			pr.load(new FileInputStream(new File("src\\conn.properties")));
			//获取属性值
			String url=pr.getProperty("url");
			String user=pr.getProperty("user");
			String password=pr.getProperty("password");
			//获取连接
			conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//关闭连接
	public static void close() {
		if(conn!=null) {
			try {
				conn.close();
				conn=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
