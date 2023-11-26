package com.woniuxy.modelDao;

import java.util.ArrayList;

import com.woniuxy.model.User;

public interface UserDao {
	//注册
	public User addUser();
	//查询全部用户	使用注解反射整表查询
	public ArrayList<User> getUsers();
	//卡号查找用户
	public User getByName(String cardId);
	//登录 卡号+密码
	public User login(String cardId,String pwd);
	//上传商品 参数为卖家卡号
	public boolean upGoods(String cardId);
	//充值 (可用于给自己充值 可用于购买宠物给卖家充值)	参数为当前登录者 充值卡号 充值金额
	public boolean addMoney(User u,String cardId,double money);
	//扣款(买商品时使用) 参数位当前登录者 扣款金额
	public boolean moveMoney(User u,double money);
}
