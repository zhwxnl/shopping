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
			System.out.print("δ��ѯ����ַ��������ӵ�ַ,");
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
			System.out.print("��Ʒ�����ڣ�");
			return false;
		}
		if (g.getState().equals("����")) {
			System.out.print("��Ʒ���۳�,");
			return false;
		}
		if (u.getCardId().equals(g.getSellCard())) {
			System.out.print("��ǰ��Ʒ�������Ʒ�����蹺��");
			return false;
		}
		Level[] ls = Level.values();
		int index = 70 - u.getLevel().hashCode();
		double money = g.getMoney() * ls[index].getDiscount();
		if (u.getMoney() < money) {
			System.out.print("����,");
			return false;
		}

		// �û�
		// ��������
		u.setAllMoney(u.getAllMoney() + money);
		// �������ѵȼ�
		if (index == ls.length - 1) {
			System.out.println(
					"���Ѿ�����߼���Ա�����ι�����������ۿ�" + ls[index].getDiscount() * 10 + "�ۣ�����"+money+"Ԫ���Żݽ��" + (g.getMoney() - money) + "Ԫ");
		} else if (u.getAllMoney() >= ls[ls.length - 1].getMoney()) {
			System.out.println(
					"��ϲ������߼���Ա,�´ι������������ۿ�" + ls[ls.length - 1].getDiscount() * 10 + "��," + (index == 0 ? "���ι��ﲻ�����ۿۣ�����"+money+"Ԫ"
							: "��������" + ls[index].getDiscount() * 10 + "�ۣ�����"+money+"Ԫ���Żݽ��" + (g.getMoney() - money) + "Ԫ"));
			u.setLevel(ls[ls.length - 1].getLevelNum());
		} else {
			for (int i = index + 1; i < ls.length; i++) {
				if ((u.getAllMoney()) < ls[i].getMoney()) {
					if (index == i - 1) {
						System.out.print("��������δ������");
					} else {
						System.out.print(
								"��ϲ����" + ls[i - 1].getLevelNum() + "����Ա���´ι��������" + ls[i - 1].getDiscount() * 10 + "��,");
					}
					System.out.println((index == 0 ? "���ι��ﲻ�����ۿۣ�����"+money+"Ԫ��"
							: "��������" + ls[index].getDiscount() * 10 + "�ۣ�����"+money+"Ԫ���Żݽ��" + (g.getMoney() - money) + "Ԫ") + ",��������"
							+ (ls[i].getMoney() - u.getAllMoney()) + "Ԫ������һ��");
					u.setLevel(ls[i - 1].getLevelNum());
					break;
				}
			}
		}
		// ��Ʒ
		// �������
		g.setBuyCard(u.getCardId());
		// ��������״̬
		g.setState("����");
		
		// ���ݿⷽ��
		if (f instanceof ModelImpl1) {
			// ��ȡ����
			Connection conn = JDBCHelper.getConn();
			try {
				conn.setAutoCommit(false);
				// ��ҿ۷�
				// ������Ǯ
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
			//��������
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
