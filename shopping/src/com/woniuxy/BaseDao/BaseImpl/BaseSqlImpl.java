package com.woniuxy.BaseDao.BaseImpl;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.woniuxy.BaseDao.BaseSqlDao;
import com.woniuxy.model.ConsAnno;
import com.woniuxy.model.TypeAnno;

public class BaseSqlImpl implements BaseSqlDao {

	@Override
	public int myUpdate(String sql, Object[] os) {
		// TODO Auto-generated method stub
		Connection conn = JDBCHelper.getConn();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			if (os != null) {
				for (int i = 0; i < os.length; i++) {
					ps.setObject(i + 1, os[i]);
				}
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	@Override
	public ResultSet myQuery(String sql, Object[] os) {
		// TODO Auto-generated method stub
		Connection conn = JDBCHelper.getConn();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			if (os != null) {
				for (int i = 0; i < os.length; i++) {
					ps.setObject(i + 1, os[i]);
				}
			}
			return ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public <T> ArrayList<T> myQuery(String path) {
		// TODO Auto-generated method stub
		ArrayList<T> list = new ArrayList<T>();
		try {
			Class<?> c = Class.forName(path);
			// 获取表名
			String tableName = c.getDeclaredAnnotation(TypeAnno.class).value();
			String sql = "select * from " + tableName;
			Connection conn = JDBCHelper.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			// 获取参数个数
			Constructor<?>[] cs = c.getConstructors();
			ConsAnno ca = null;
			for (Constructor<?> cons : cs) {
				if ((ca = cons.getAnnotation(ConsAnno.class)) != null) {
					int num = ca.value();
					// 获取参数集
					Object[] os = new Object[num];
					
					while (rs.next()) {
						for (int i = 0; i < num; i++) {
							os[i] = rs.getObject(i + 1);
						}
						list.add((T) cons.newInstance(os));
					}
					return list;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return list;
		} 
		return list;
	}

}
