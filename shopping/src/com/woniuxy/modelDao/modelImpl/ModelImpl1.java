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

//���ݿⷽ��
public class ModelImpl1 implements FatherDao {

	private BaseSqlImpl bsi = new BaseSqlImpl();
	Scanner sc = new Scanner(System.in);

	@Override
	public User addUser() {
		// TODO Auto-generated method stub
		System.out.println("����������");
		String name = sc.next();
		System.out.println("����������");
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
		System.out.println("��ѡ���ϴ�����1.�˶���2.ʳƷ��3.�ľ���");
		int choose = InputNumber.getNum(1, 3);
		System.out.println("��������Ʒ����");
		String name = sc.next();
		System.out.println("��ѡ����أ�\n���ر��\t����");
		ArrayList<Address> as = getAddress();
		as.stream().sorted((o1, o2) -> o1.getTid() - o2.getTid()).forEach(System.out::println);
		int[] address = new int[1];
		while (true) {
			address[0] = InputNumber.getIntNum();
			if (as.stream().anyMatch(o -> o.getTid() == address[0])) {
				break;
			}
			System.out.println("�밴���ر�ѡ��");
		}
		System.out.println("������" + (choose == 1 ? "����" : choose == 2 ? "����" : "Ʒ��"));
		String s = sc.next();
		System.out.println("�������ۼ�");
		double money;
		while (true) {
			money = InputNumber.getDoubleNum();
			if (money < 0) {
				System.out.println("����������������ȷ�ļ۸�");
			}else {
				break;
			}
		}

		String tableName = choose == 1 ? "sport" : choose == 2 ? "food" : "study";
		String sql = "insert " + tableName + " values(null,?,default,?,?,?,?,null)";
		Object[] os = { name, address[0], s, money, cardId };
		boolean bool = bsi.myUpdate(sql, os) > 0 ? true : false;
		if (!bool) {
			System.out.print(name + "�Ѵ���,");
		}
		return bool;
	}

	@Override
	public boolean addMoney(User u, String cardId, double money) {
		// TODO Auto-generated method stub
		if (money < 0) {
			System.out.print("�����������,");
			return false;
		}
		if (!u.getCardId().equals(cardId)) {
			u = getByName(cardId);
			if (u == null) {
				System.out.print("�˺Ų�����,");
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
			System.out.print("�����������,");
			return false;
		}
		if (u.getMoney() < money) {
			System.out.print("����,");
			return false;
		}
		// �����û����ѡ����ѵȼ�
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
			System.out.println("δ��ѯ�����ݣ�");
			return;
		}
		int sum = all.size() / 3 + (all.size() % 3 == 0 ? 0 : 1);
		System.out.println("һ����" + sum + "ҳ��Ϣ��������չʾҳ��");
		int number = InputNumber.getNum(1, sum);
		
		if(all.stream().sorted((o1, o2) -> o1.getGid() - o2.getGid()).skip(3 * (number - 1)).limit(3).filter(o->o.getState().equals("����")).count()>0) {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t\t\t\t\t��������");
			all.stream().sorted((o1, o2) -> o1.getGid() - o2.getGid()).skip(3 * (number - 1)).limit(3)
			.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��")));
		}else {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
			all.stream().sorted((o1, o2) -> o1.getGid() - o2.getGid()).skip(3 * (number - 1)).limit(3)
			.map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��"))
				.forEach(System.out::println);
		}
	}

	@Override
	public void show2() {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all.isEmpty()) {
			System.out.println("δ��ѯ�����ݣ�");
			return;
		}
		System.out.println("��ѡ���ѯ״̬1.���� 2.����");
		int c1 = InputNumber.getNum(1, 2);
		
		String sel = c1 == 1 ? "����" : "����";
		if (all.stream().filter(o -> o.getState().equals(sel)).count() == 0) {
			System.out.println("û��" + sel + "����Ʒ");
			return;
		}
		System.out.println("��ѡ������ʽ1.�۸�����2.�۸���");
		int c2 = InputNumber.getNum(1, 2);
		if(sel.equals("����")) {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
			all.stream().filter(o -> o.getState().equals(sel))	
				.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
					.map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��"))
						.forEach(System.out::println);
		}else {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t\t\t\t\t��������");
			all.stream().filter(o -> o.getState().equals(sel))	
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��")));
		}
	}

	@Override
	public void show3() {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all.isEmpty()) {
			System.out.println("δ��ѯ�����ݣ�");
			return;
		}
		System.out.println("��ѡ���ѯ���أ�\n���ر��\t����");
		ArrayList<Address> as = getAddress();
		as.stream().sorted((o1, o2) -> o1.getTid() - o2.getTid()).forEach(System.out::println);
		int[] c1 = new int[1];
		while (true) {
			c1[0] = InputNumber.getIntNum();
			if (as.stream().anyMatch(o -> o.getTid() == c1[0])) {
				break;
			}
			System.out.println("�밴���ر�ѡ��");
		}
		String address = getAddress().stream().filter(o -> o.getTid() == c1[0]).map(o -> o.getAddress())
				.collect(Collectors.toList()).get(0);

		if (all.stream().filter(o -> o.getAddress().equals(address)).count() == 0) {
			System.out.println("û��" + address + "����Ʒ");
			return;
		}
		System.out.println("��ѡ������ʽ1.�۸�����2.�۸���");
		int c2 = InputNumber.getNum(1, 2);

		if(all.stream().filter(o->o.getAddress().equals(address)&&o.getState().equals("����")).count()>0) {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t\t\t\t\t��������");
			all.stream().filter(o -> o.getAddress().equals(address))
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��")));
		}else {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
			all.stream().filter(o -> o.getAddress().equals(address))
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��"))
					.forEach(System.out::println);
		}
	}

	@Override
	public void showMy(User u) {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all.isEmpty()) {
			System.out.println("δ��ѯ�����ݣ�");
			return;
		}
		if (all.stream().filter(o -> (o.getState().equals("����") && o.getSellCard().equals(u.getCardId()))
				|| (o.getBuyCard() != null && o.getBuyCard().equals(u.getCardId()))).count() == 0) {
			System.out.println("δ��ѯ����Ʒ��Ϣ");
			return;
		}
		if(all.stream().filter(o->o.getState().equals("����")&&o.getBuyCard().equals(u.getCardId())).count()>0) {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t\t\t\t\t��������");
			all.stream()
			.filter(o -> (o.getState().equals("����") && o.getSellCard().equals(u.getCardId()))
					|| (o.getBuyCard() != null && o.getBuyCard().equals(u.getCardId())))
			.sorted((o1, o2) -> o1.getGid() - o2.getGid())
				.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��")));
		}else {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
			all.stream()
			.filter(o -> (o.getState().equals("����") && o.getSellCard().equals(u.getCardId()))
					|| (o.getBuyCard() != null && o.getBuyCard().equals(u.getCardId())))
			.sorted((o1, o2) -> o1.getGid() - o2.getGid()).map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��"))
				.forEach(System.out::println);
		}
	}

	@Override
	public void doGoods(int type, int gid) {
		// TODO Auto-generated method stub
		Goods g = getById(type, gid);
		if (g == null) {
			System.out.println("��Ʒ�����ڣ�");
			return;
		}
		if(g.getState().equals("����")) {
			System.out.println("��Ʒ���۳���");
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
		System.out.println("��������ӵ�ַ");
		String address = sc.next();
		String sql = "insert address values(null,?)";
		Object[] os = { address };
		int result = bsi.myUpdate(sql, os);
		System.out.print(result > 0 ? "" : address + "�Ѵ��ڣ�");
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
