package com.woniuxy.modelDao;

public interface FatherDao extends UserDao, GoodsDao, AddressDao {
	//更新单个数据
	public <T> boolean updateObject(T t);
}
