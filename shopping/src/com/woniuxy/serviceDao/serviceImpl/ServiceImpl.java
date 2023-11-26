package com.woniuxy.serviceDao.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.woniuxy.BaseDao.BaseImpl.JDBCHelper;
import com.woniuxy.model.Address;
import com.woniuxy.model.Goods;
import com.woniuxy.model.Level;
import com.woniuxy.model.User;
import com.woniuxy.modelDao.FatherDao;
import com.woniuxy.modelDao.modelImpl.ModelImpl1;
import com.woniuxy.serviceDao.ServiceDao;

public class ServiceImpl implements ServiceDao {
	private FatherDao f;

	public ServiceImpl(FatherDao f) {
		super();
		this.f = f;
	}

	@Override
	public User addUser() {
		// TODO Auto-generated method stub
		ArrayList<Address> address = f.getAddress();
		if(address==null||address.isEmpty()) {
			System.out.print("未查询到地址，请先添加地址,");
			return null;
		}
		 return f.addUser();
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		return f.getUsers();
	}

	@Override
	public User getByName(String cardId) {
		// TODO Auto-generated method stub
		return f.getByName(cardId);
	}

	@Override
	public User login(String cardId, String pwd) {
		// TODO Auto-generated method stub
		return f.login(cardId, pwd);
	}

	@Override
	public boolean upGoods(String cardId) {
		// TODO Auto-generated method stub
		return f.upGoods(cardId);
	}

	@Override
	public boolean addMoney(User u, String cardId, double money) {
		// TODO Auto-generated method stub
		boolean bool = f.addMoney(u, cardId, money);
		JDBCHelper.close();
		return bool;
	}

	@Override
	public boolean moveMoney(User u, double money) {
		// TODO Auto-generated method stub
		boolean bool = f.moveMoney(u, money);
		JDBCHelper.close();
		return bool;
	}

	@Override
	public Goods getById(int type, int gid) {
		// TODO Auto-generated method stub
		return f.getById(type, gid);
	}

	@Override
	public ArrayList<Goods> getAll(int type) {
		// TODO Auto-generated method stub
		return f.getAll(type);
	}

	@Override
	public ArrayList<Goods> getAll() {
		// TODO Auto-generated method stub
		return f.getAll();
	}

	@Override
	public void show1() {
		// TODO Auto-generated method stub
		f.show1();
	}

	@Override
	public void show2() {
		// TODO Auto-generated method stub
		f.show2();
	}

	@Override
	public void show3() {
		// TODO Auto-generated method stub
		f.show3();
	}

	@Override
	public void showMy(User u) {
		// TODO Auto-generated method stub
		f.showMy(u);
	}

	@Override
	public void doGoods(int type, int gid) {
		// TODO Auto-generated method stub
		f.doGoods(type, gid);
	}

	@Override
	public ArrayList<Address> getAddress() {
		// TODO Auto-generated method stub
		return f.getAddress();
	}

	@Override
	public boolean addAddress() {
		// TODO Auto-generated method stub
		return f.addAddress();
	}

	@Override
	public boolean buyGoods(User u, int type, int gId) {
		// TODO Auto-generated method stub
		Goods g = null;
		if ((g = getById(type, gId)) == null) {
			System.out.print("商品不存在，");
			return false;
		}
		if (g.getState().equals("已售")) {
			System.out.print("商品已售出,");
			return false;
		}
		if (u.getCardId().equals(g.getSellCard())) {
			System.out.print("当前商品是你的商品，无需购买，");
			return false;
		}
		Level[] ls = Level.values();
		int index = 70 - u.getLevel().hashCode();
		double money = g.getMoney() * ls[index].getDiscount();
		if (u.getMoney() < money) {
			System.out.print("余额不足,");
			return false;
		}

		// 用户
		// 设置消费
		u.setAllMoney(u.getAllMoney() + money);
		// 设置消费等级
		if (index == ls.length - 1) {
			System.out.println(
					"你已经是最高级会员，本次购物享受最大折扣" + ls[index].getDiscount() * 10 + "折，消费"+money+"元，优惠金额" + (g.getMoney() - money) + "元");
		} else if (u.getAllMoney() >= ls[ls.length - 1].getMoney()) {
			System.out.println(
					"恭喜升到最高级会员,下次购物可享受最大折扣" + ls[ls.length - 1].getDiscount() * 10 + "折," + (index == 0 ? "本次购物不享受折扣，消费"+money+"元"
							: "本次享受" + ls[index].getDiscount() * 10 + "折，消费"+money+"元，优惠金额" + (g.getMoney() - money) + "元"));
			u.setLevel(ls[ls.length - 1].getLevelNum());
		} else {
			for (int i = index + 1; i < ls.length; i++) {
				if ((u.getAllMoney()) < ls[i].getMoney()) {
					if (index == i - 1) {
						System.out.print("本次消费未升级，");
					} else {
						System.out.print(
								"恭喜升到" + ls[i - 1].getLevelNum() + "级会员，下次购物可享受" + ls[i - 1].getDiscount() * 10 + "折,");
					}
					System.out.println((index == 0 ? "本次购物不享受折扣，消费"+money+"元，"
							: "本次享受" + ls[index].getDiscount() * 10 + "折，消费"+money+"元，优惠金额" + (g.getMoney() - money) + "元") + ",还需消费"
							+ (ls[i].getMoney() - u.getAllMoney()) + "元升到下一级");
					u.setLevel(ls[i - 1].getLevelNum());
					break;
				}
			}
		}
		// 商品
		// 设置买家
		g.setBuyCard(u.getCardId());
		// 设置售卖状态
		g.setState("已售");
		
		// 数据库方案
		if (f instanceof ModelImpl1) {
			// 获取连接
			Connection conn = JDBCHelper.getConn();
			try {
				conn.setAutoCommit(false);
				// 买家扣费
				// 卖家收钱
				boolean bool=f.moveMoney(u, money)&&f.addMoney(u, g.getSellCard(), g.getMoney())&&f.updateObject(u) && f.updateObject(g);
				if (!(bool)) {
					conn.rollback();
					return false;
				} else {
					conn.commit();
					JDBCHelper.close();
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//更新数据
			if (!(f.updateObject(u) && f.updateObject(g))) {
				return false;
			}
			return	f.moveMoney(u, money)&&f.addMoney(u, g.getSellCard(), g.getMoney());
		}
		return false;
	}

	@Override
	public <T> boolean updateObject(T t) {
		// TODO Auto-generated method stub
		return f.updateObject(t);
	}

}
