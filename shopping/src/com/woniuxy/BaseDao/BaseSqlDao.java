package com.woniuxy.BaseDao;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface BaseSqlDao {
	//��ɾ�Ĺ��÷���
	public int myUpdate(String sql,Object[] os);
	//��������ѯ���ؽ��������
	public ResultSet myQuery(String sql,Object[] os);
	//��ѯ�����ؼ��Ϸ���	����Address��Userʵ�����ѯ
	public <T> ArrayList<T> myQuery(String path);
}
