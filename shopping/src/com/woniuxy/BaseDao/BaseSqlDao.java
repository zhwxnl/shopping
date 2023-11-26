package com.woniuxy.BaseDao;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface BaseSqlDao {
	//增删改共用方法
	public int myUpdate(String sql,Object[] os);
	//有条件查询返回结果集方法
	public ResultSet myQuery(String sql,Object[] os);
	//查询整表返回集合方法	用于Address、User实体类查询
	public <T> ArrayList<T> myQuery(String path);
}
