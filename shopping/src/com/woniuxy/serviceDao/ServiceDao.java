package com.woniuxy.serviceDao;

import com.woniuxy.model.User;
import com.woniuxy.modelDao.FatherDao;

public interface ServiceDao extends FatherDao{
	//�������	����Ϊ��ǰ��¼���û����� ��Ʒ���ͺ�Ҫ�����Ʒid
	public boolean buyGoods(User u,int type,int gId);
	
	
}
