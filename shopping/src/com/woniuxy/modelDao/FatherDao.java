package com.woniuxy.modelDao;

public interface FatherDao extends UserDao, GoodsDao, AddressDao {
	//���µ�������
	public <T> boolean updateObject(T t);
}
