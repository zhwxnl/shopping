package com.woniuxy.serviceDao;

import com.woniuxy.model.User;
import com.woniuxy.modelDao.FatherDao;

public interface ServiceDao extends FatherDao{
	//购买宠物	参数为当前登录的用户对象 商品类型和要买的商品id
	public boolean buyGoods(User u,int type,int gId);
	
	
}
