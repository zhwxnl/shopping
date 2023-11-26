package com.woniuxy.modelDao;

import java.util.ArrayList;

import com.woniuxy.model.Goods;
import com.woniuxy.model.User;

public interface GoodsDao {
	//传入商品类型 商品id查询商品
	public Goods getById(int type,int gid);
	//查询某品类所有商品 参数为 1.运动类2食品类3.文具类  
	public ArrayList<Goods> getAll(int type);
	//数据库方案中 使用两表(对应商品表+产地表)联合查询 
	//记事本方案 直接读Goods记事本 获得集合 再筛选
	//获取所有商品构成的父类集合
	public ArrayList<Goods> getAll();
	//数据库方案中 要将三张商品表商品全部拿出存入父类集合
	//分页查询每页三个商品信息
	public void show1();
	//状态查询 1.待售 2.已售 选择后 再询问1.价格升序2.价格降序
	public void show2();
	//产地查询 1.价格升序2.价格降序
	public void show3();
	//我的商品
	public void showMy(User u);
	////试用产品
	public void doGoods(int type,int gid);
}
