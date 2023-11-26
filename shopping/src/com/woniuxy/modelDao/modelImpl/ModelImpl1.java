package com.woniuxy.modelDao.modelImpl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.woniuxy.BaseDao.BaseImpl.BaseSqlImpl;
import com.woniuxy.BaseDao.BaseImpl.InputNumber;
import com.woniuxy.model.Address;
import com.woniuxy.model.Food;
import com.woniuxy.model.Goods;
import com.woniuxy.model.Sport;
import com.woniuxy.model.Study;
import com.woniuxy.model.TypeAnno;
import com.woniuxy.model.User;
import com.woniuxy.modelDao.FatherDao;

//数据库方案
public class ModelImpl1 implements FatherDao {

	private BaseSqlImpl bsi = new BaseSqlImpl();
	Scanner sc = new Scanner(System.in);

	@Override
	public User addUser() {
		// TODO Auto-generated method stub
		System.out.println("请输入姓名");
		String name = sc.next();
		System.out.println("请输入密码");
		String pwd = sc.next();
		User u = new User(0, name, pwd);
		String sql = "insert user values(null,?,?,?,?,?,?)";
		Object[] os = { u.getLevel(), u.getCardId(), name, pwd, u.getMoney(), u.getAllMoney() };
		return bsi.myUpdate(sql, os) > 0 ? u : null;
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		return bsi.myQuery(User.class.getName());
	}

	@Override
	public User getByName(String cardId) {
		// TODO Auto-generated method stub
		String sql = "select * from user where cardId=?";
		Object[] os = { cardId };
		ResultSet r = bsi.myQuery(sql, os);
		try {
			if (r.next()) {
				return new User(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
						r.getDouble(6), r.getDouble(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User login(String cardId, String pwd) {
		// TODO Auto-generated method stub
		String sql = "select * from user where cardId=? and pwd=?";
		Object[] os = { cardId, pwd };
		ResultSet r = bsi.myQuery(sql, os);
		try {
			if (r.next()) {
				return new User(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
						r.getDouble(6), r.getDouble(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean upGoods(String cardId) {
		// TODO Auto-generated method stub
		System.out.println("请选择上传类型1.运动类2.食品类3.文具类");
		int choose = InputNumber.getNum(1, 3);
		System.out.println("请输入商品名称");
		String name = sc.next();
		System.out.println("请选择产地：\n产地编号\t产地");
		ArrayList<Address> as = getAddress();
		as.stream().sorted((o1, o2) -> o1.getTid() - o2.getTid()).forEach(System.out::println);
		int[] address = new int[1];
		while (true) {
			address[0] = InputNumber.getIntNum();
			if (as.stream().anyMatch(o -> o.getTid() == address[0])) {
				break;
			}
			System.out.println("请按产地表选择！");
		}
		System.out.println("请输入" + (choose == 1 ? "材质" : choose == 2 ? "热量" : "品牌"));
		String s = sc.next();
		System.out.println("请输入售价");
		double money;
		while (true) {
			money = InputNumber.getDoubleNum();
			if (money < 0) {
				System.out.println("输入有误，请输入正确的价格");
			}else {
				break;
			}
		}

		String tableName = choose == 1 ? "sport" : choose == 2 ? "food" : "study";
		String sql = "insert " + tableName + " values(null,?,default,?,?,?,?,null)";
		Object[] os = { name, address[0], s, money, cardId };
		boolean bool = bsi.myUpdate(sql, os) > 0 ? true : false;
		if (!bool) {
			System.out.print(name + "已存在,");
		}
		return bool;
	}

	@Override
	public boolean addMoney(User u, String cardId, double money) {
		// TODO Auto-generated method stub
		if (money < 0) {
			System.out.print("金额输入有误,");
			return false;
		}
		if (!u.getCardId().equals(cardId)) {
			u = getByName(cardId);
			if (u == null) {
				System.out.print("账号不存在,");
				return false;
			}
		}
		String sql = "update user set money=? where cardId=?";
		Object[] os = { u.getMoney() + money, u.getCardId() };
		int r = bsi.myUpdate(sql, os);
		if (r > 0) {
			u.setMoney(u.getMoney() + money);
		}
		return r > 0 ? true : false;
	}

	@Override
	public boolean moveMoney(User u, double money) {
		// TODO Auto-generated method stub
		if (money < 0) {
			System.out.print("金额输入有误,");
			return false;
		}
		if (u.getMoney() < money) {
			System.out.print("余额不足,");
			return false;
		}
		// 更新用户消费、消费等级
		u.setMoney(u.getMoney() - money);
		String sql = "update user set money=? where cardid=?";
		Object[] os = { u.getMoney(), u.getCardId() };
		return bsi.myUpdate(sql, os) > 0 ? true : false;
	}

	@Override
	public Goods getById(int type, int gid) {
		// TODO Auto-generated method stub
		String tableName = type == 1 ? "sport" : type == 2 ? "food" : "study";
		String str = type == 1 ? "material" : type == 2 ? "caloric" : "brand";
		String sql = "select gid, name,state,b.address,money,sellCard,buyCard," + str + " from " + tableName
				+ " a,address b where a.address=b.tid and gid=?";
		Object[] os = { gid };
		ResultSet r = bsi.myQuery(sql, os);
		try {
			if (r.next()) {
				return type == 1
						? new Sport(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getDouble(5),
								r.getString(6), r.getString(7), r.getString(8))
						: type == 2
								? new Food(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getDouble(5),
										r.getString(6), r.getString(7), r.getString(8))
								: new Study(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getDouble(5),
										r.getString(6), r.getString(7), r.getString(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Goods> getAll(int type) {
		// TODO Auto-generated method stub
		ArrayList<Goods> list = new ArrayList<Goods>();
		String tableName = type == 1 ? "sport" : type == 2 ? "food" : "study";
		String str = type == 1 ? "material" : type == 2 ? "caloric" : "brand";
		String sql = "select gid, name,state,b.address,money,sellCard,buyCard," + str + " from " + tableName
				+ " a,address b where a.address=b.tid order by gid";
		ResultSet r = bsi.myQuery(sql, null);
		try {

			while (r.next()) {
				list.add(type == 1
						? new Sport(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getDouble(5),
								r.getString(6), r.getString(7), r.getString(8))
						: type == 2
								? new Food(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getDouble(5),
										r.getString(6), r.getString(7), r.getString(8))
								: new Study(r.getInt(1), r.getString(2), r.getString(3), r.getString(4), r.getDouble(5),
										r.getString(6), r.getString(7), r.getString(8)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ArrayList<Goods> getAll() {
		// TODO Auto-generated method stub
		ArrayList<Goods> list = new ArrayList<Goods>();
		list.addAll(getAll(1));
		list.addAll(getAll(2));
		list.addAll(getAll(3));
		return list;
	}

	@Override
	public void show1() {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all.isEmpty()) {
			System.out.println("未查询到数据！");
			return;
		}
		int sum = all.size() / 3 + (all.size() % 3 == 0 ? 0 : 1);
		System.out.println("一共有" + sum + "页信息，请输入展示页码");
		int number = InputNumber.getNum(1, sum);
		
		if(all.stream().sorted((o1, o2) -> o1.getGid() - o2.getGid()).skip(3 * (number - 1)).limit(3).filter(o->o.getState().equals("已售")).count()>0) {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t\t\t\t\t特有属性");
			all.stream().sorted((o1, o2) -> o1.getGid() - o2.getGid()).skip(3 * (number - 1)).limit(3)
			.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类")));
		}else {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
			all.stream().sorted((o1, o2) -> o1.getGid() - o2.getGid()).skip(3 * (number - 1)).limit(3)
			.map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类"))
				.forEach(System.out::println);
		}
	}

	@Override
	public void show2() {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all.isEmpty()) {
			System.out.println("未查询到数据！");
			return;
		}
		System.out.println("请选择查询状态1.待售 2.已售");
		int c1 = InputNumber.getNum(1, 2);
		
		String sel = c1 == 1 ? "待售" : "已售";
		if (all.stream().filter(o -> o.getState().equals(sel)).count() == 0) {
			System.out.println("没有" + sel + "的商品");
			return;
		}
		System.out.println("请选择排序方式1.价格升序2.价格降序");
		int c2 = InputNumber.getNum(1, 2);
		if(sel.equals("待售")) {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
			all.stream().filter(o -> o.getState().equals(sel))	
				.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
					.map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类"))
						.forEach(System.out::println);
		}else {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t\t\t\t\t特有属性");
			all.stream().filter(o -> o.getState().equals(sel))	
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类")));
		}
	}

	@Override
	public void show3() {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all.isEmpty()) {
			System.out.println("未查询到数据！");
			return;
		}
		System.out.println("请选择查询产地：\n产地编号\t产地");
		ArrayList<Address> as = getAddress();
		as.stream().sorted((o1, o2) -> o1.getTid() - o2.getTid()).forEach(System.out::println);
		int[] c1 = new int[1];
		while (true) {
			c1[0] = InputNumber.getIntNum();
			if (as.stream().anyMatch(o -> o.getTid() == c1[0])) {
				break;
			}
			System.out.println("请按产地表选择！");
		}
		String address = getAddress().stream().filter(o -> o.getTid() == c1[0]).map(o -> o.getAddress())
				.collect(Collectors.toList()).get(0);

		if (all.stream().filter(o -> o.getAddress().equals(address)).count() == 0) {
			System.out.println("没有" + address + "的商品");
			return;
		}
		System.out.println("请选择排序方式1.价格升序2.价格降序");
		int c2 = InputNumber.getNum(1, 2);

		if(all.stream().filter(o->o.getAddress().equals(address)&&o.getState().equals("已售")).count()>0) {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t\t\t\t\t特有属性");
			all.stream().filter(o -> o.getAddress().equals(address))
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类")));
		}else {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
			all.stream().filter(o -> o.getAddress().equals(address))
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类"))
					.forEach(System.out::println);
		}
	}

	@Override
	public void showMy(User u) {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all.isEmpty()) {
			System.out.println("未查询到数据！");
			return;
		}
		if (all.stream().filter(o -> (o.getState().equals("待售") && o.getSellCard().equals(u.getCardId()))
				|| (o.getBuyCard() != null && o.getBuyCard().equals(u.getCardId()))).count() == 0) {
			System.out.println("未查询到商品信息");
			return;
		}
		if(all.stream().filter(o->o.getState().equals("已售")&&o.getBuyCard().equals(u.getCardId())).count()>0) {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t\t\t\t\t特有属性");
			all.stream()
			.filter(o -> (o.getState().equals("待售") && o.getSellCard().equals(u.getCardId()))
					|| (o.getBuyCard() != null && o.getBuyCard().equals(u.getCardId())))
			.sorted((o1, o2) -> o1.getGid() - o2.getGid())
				.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类")));
		}else {
			System.out.println("商品类型\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
			all.stream()
			.filter(o -> (o.getState().equals("待售") && o.getSellCard().equals(u.getCardId()))
					|| (o.getBuyCard() != null && o.getBuyCard().equals(u.getCardId())))
			.sorted((o1, o2) -> o1.getGid() - o2.getGid()).map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"运动类":o.getClass().getSimpleName().equals("Study")?"学习类":"食品类"))
				.forEach(System.out::println);
		}
	}

	@Override
	public void doGoods(int type, int gid) {
		// TODO Auto-generated method stub
		Goods g = getById(type, gid);
		if (g == null) {
			System.out.println("商品不存在！");
			return;
		}
		if(g.getState().equals("已售")) {
			System.out.println("商品已售出！");
			return;
		}
		g.gotIt();
	}

	@Override
	public ArrayList<Address> getAddress() {
		// TODO Auto-generated method stub
		return bsi.myQuery(Address.class.getName());
	}

	@Override
	public boolean addAddress() {
		// TODO Auto-generated method stub
		System.out.println("请输入添加地址");
		String address = sc.next();
		String sql = "insert address values(null,?)";
		Object[] os = { address };
		int result = bsi.myUpdate(sql, os);
		System.out.print(result > 0 ? "" : address + "已存在，");
		return result > 0 ? true : false;
	}

	@Override
	public <T> boolean updateObject(T t) {
		// TODO Auto-generated method stub
		String tableName = t.getClass().getDeclaredAnnotation(TypeAnno.class).value();
		StringBuilder sql = new StringBuilder("update " + tableName + " set ");
		Field[] fields;
		if(t instanceof Goods) {
			Field[] fs = t.getClass().getSuperclass().getDeclaredFields();
			int num=0;
			List<String> fname=Arrays.asList("gid","state","buyCard");
			fields=new Field[fname.size()];
			for(Field f:fs) {
				if(fname.contains(f.getName())) {
					fields[num++]=f;
				}
			}
		}else {
			fields= t.getClass().getDeclaredFields();
			fields=Arrays.copyOfRange(fields, 1, fields.length);
		}
		Object[] os = new Object[fields.length+1];
		for (int i = 0; i < fields.length; i++) {
			sql.append(fields[i].getName() + "=?,");
			try {
				fields[i].setAccessible(true);
				os[i] = fields[i].get(t);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where " + fields[0].getName() + "=?");
		try {
			os[os.length - 1] = fields[0].get(t);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = bsi.myUpdate(sql.toString(), os);
		return result > 0 ? true : false;
	}

}
