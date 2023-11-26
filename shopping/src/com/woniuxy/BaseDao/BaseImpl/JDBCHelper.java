package com.woniuxy.BaseDao.BaseImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//�������ݿ�
public class JDBCHelper {

	// ����ģʽ
	private static Connection conn = null;

	// ��ȡ����
	public static Connection getConn() {
		if(conn!=null) {
			return conn;
		}
		// ��������
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// ��ȡ�����ļ�
			Properties pr = new Properties();
			pr.load(new FileInputStream(new File("src\\conn.properties")));
			//��ȡ����ֵ
			String url=pr.getProperty("url");
			String user=pr.getProperty("user");
			String password=pr.getProperty("password");
			//��ȡ����
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

	//�ر�����
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
