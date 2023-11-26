package com.woniuxy.modelDao.modelImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.woniuxy.BaseDao.BaseImpl.BaseIOImpl;
import com.woniuxy.BaseDao.BaseImpl.InputNumber;
import com.woniuxy.model.Address;
import com.woniuxy.model.Food;
import com.woniuxy.model.Goods;
import com.woniuxy.model.Sport;
import com.woniuxy.model.Study;
import com.woniuxy.model.User;
import com.woniuxy.modelDao.FatherDao;

//�ļ�����
public class ModelImpl2 implements FatherDao {

	private BaseIOImpl bii = new BaseIOImpl();
	Scanner sc = new Scanner(System.in);
	// ���ļ�
	private File parent = new File("E:\\EclipseWorkSpace\\class107_stu_28\\src");
	// ���ļ�
	private File address = new File(parent, "address.txt");
	private File user = new File(parent, "user.txt");
	private File goods = new File(parent, "Goods.txt");

	// �û�����
	ArrayList<User> us = null;

	@Override
	public User addUser() {
		// TODO Auto-generated method stub
		ArrayList<User> ulist = bii.readAll(user);
		if (ulist == null) {
			ulist = new ArrayList<User>();
		}
		System.out.println("����������");
		String name = sc.next();
		System.out.println("����������");
		String pwd = sc.next();
		User u = new User(ulist.size() + 1, name, pwd);
		ulist.add(u);
		return bii.writeAll(ulist, user) ? u : null;
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		if (us != null) {
			return us;
		}
		return bii.readAll(user);
	}

	@Override
	public User getByName(String cardId) {
		// TODO Auto-generated method stub
		if (us == null) {
			us = bii.readAll(user);
		}
		if (us == null) {
			return null;
		}
		List<User> ul = us.stream().filter(o -> o.getCardId().equals(cardId)).collect(Collectors.toList());
		if (ul.isEmpty()) {
			return null;
		}
		return ul.get(0);
	}

	@Override
	public User login(String cardId, String pwd) {
		// TODO Auto-generated method stub
		User u = getByName(cardId);
		if (u == null || !u.checkPwd(pwd)) {
			return null;
		}
		return u;
	}

	@Override
	public boolean upGoods(String cardId) {
		// TODO Auto-generated method stub
		ArrayList<Goods> gs = bii.readAll(goods);
		if (gs == null) {
			gs = new ArrayList<Goods>();
		}
		System.out.println("��ѡ���ϴ�����1.�˶���2.ʳƷ��3.�ľ���");
		int type = InputNumber.getNum(1, 3);
		System.out.println("��������Ʒ����");
		String name = sc.next();
		if (gs.stream().anyMatch(o -> o.getName().equals(name))) {
			System.out.print(name + "�Ѵ��ڣ�");
			return false;
		}
		System.out.println("��ѡ����أ�\n���ر��\t����");
		ArrayList<Address> as = getAddress();
		Map<Integer, Address> ms = as.stream().sorted((o1, o2) -> o1.getTid() - o2.getTid())
				.collect(Collectors.toMap(o -> o.getTid(), Function.identity()));
		ms.values().forEach(System.out::println);
		String addr = null;
		while (true) {
			int num = InputNumber.getIntNum();
			try {
				addr = ms.get(num).getAddress();
				break;
			} catch (Exception e) {
				System.out.println("ѡ�������밴���ر�ѡ��");
			}
		}

		System.out.println("������" + (type == 1 ? "����" : type == 2 ? "����" : "Ʒ��"));
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

		gs.add(type == 1 ? new Sport(gs.size() + 1, name, addr, money, cardId, s)
				: type == 2 ? new Food(gs.size() + 1, name, addr, money, cardId, s)
						: new Study(gs.size() + 1, name, addr, money, cardId, s));
		return bii.writeAll(gs, goods) ? true : false;
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
		u.setMoney(u.getMoney() + money);
		return bii.writeAll(us, user) ? true : false;
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
		u.setMoney(u.getMoney() - money);
		return bii.writeAll(us, user) ? true : false;
	}

	@Override
	public Goods getById(int type, int gid) {
		// TODO Auto-generated method stub
		ArrayList<Goods> gs = bii.readAll(goods);
		if (gs == null) {
			return null;
		}
		List<Goods> gl = gs.stream().filter(o -> o.getGid() == gid).collect(Collectors.toList());
		if (gl.isEmpty()) {
			return null;
		}
		return gl.get(0);
	}

	@Override
	public ArrayList<Goods> getAll(int type) {
		// TODO Auto-generated method stub
		ArrayList<Goods> gs = bii.readAll(goods);
		if (gs == null) {
			return null;
		}
		Class<?> c = type == 1 ? Sport.class : type == 2 ? Food.class : Study.class;
		ArrayList<Goods> gl = (ArrayList<Goods>) gs.stream().filter(o -> o.getClass() == c)
				.collect(Collectors.toList());
		return gl;
	}

	@Override
	public ArrayList<Goods> getAll() {
		// TODO Auto-generated method stub
		return bii.readAll(goods);
	}

	@Override
	public void show1() {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all == null) {
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
		if (all == null) {
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
		int c2 = sc.nextInt();
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
		if (all == null) {
			System.out.println("δ��ѯ�����ݣ�");
			return;
		}
		System.out.println("��ѡ���ѯ���أ�\n���ر��\t����");
		getAddress().stream().sorted((o1, o2) -> o1.getTid() - o2.getTid()).forEach(System.out::println);
		String[] address = new String[1];
		while (true) {
			int num=InputNumber.getIntNum();
			try {
				address[0] = getAddress().stream().filter(o -> o.getTid() == num).map(o -> o.getAddress())
						.collect(Collectors.toList()).get(0);
				break;
			} catch (Exception e) {
				System.out.println("ѡ�������밴���ر�ѡ��");
			}
		}
		if (all.stream().filter(o -> o.getAddress().equals(address[0])).count() == 0) {
			System.out.println("û��" + address[0] + "����Ʒ");
			return;
		}
		System.out.println("��ѡ������ʽ1.�۸�����2.�۸���");
		int c2 = InputNumber.getNum(1, 2);

		if(all.stream().filter(o->o.getAddress().equals(address[0])&&o.getState().equals("����")).count()>0) {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t\t\t\t\t��������");
			all.stream().filter(o -> o.getAddress().equals(address[0]))
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.forEach(o->System.out.println(o.toString().replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��")));
		}else {
			System.out.println("��Ʒ����\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
			all.stream().filter(o -> o.getAddress().equals(address[0]))
			.sorted((o1, o2) -> o1.getMoney() > o2.getMoney() ? c2 == 1 ? 1 : -1 : c2 == 1 ? -1 : 1)
				.map(o->o.toString().replaceAll("\\s+", "\t").replaceFirst(String.valueOf(o.getGid()), o.getClass().getSimpleName().equals("Sport")?"�˶���":o.getClass().getSimpleName().equals("Study")?"ѧϰ��":"ʳƷ��"))
					.forEach(System.out::println);
		}

	}

	@Override
	public void showMy(User u) {
		// TODO Auto-generated method stub
		ArrayList<Goods> all = getAll();
		if (all == null) {
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
		return bii.readAll(address);
	}

	@Override
	public boolean addAddress() {
		// TODO Auto-generated method stub
		System.out.println("��������ӵ�ַ");
		String addr = sc.next();
		ArrayList<Address> as = getAddress();
		if (as == null) {
			as = new ArrayList<Address>();
		}
		if (as.stream().anyMatch(o -> o.getAddress().equals(addr))) {
			System.out.print(addr + "�Ѵ��ڣ�");
			return false;
		}
		as.add(new Address(as.size() + 1, addr));
		return bii.writeAll(as, address);
	}

	@Override
	public <T> boolean updateObject(T t) {
		// TODO Auto-generated method stub
		File file = null;
		if (t instanceof User) {
			file = user;
		} else if (t instanceof Goods) {
			file = goods;
		} else {
			file = address;
		}
		boolean bool = false;
		if (t instanceof Goods) {
			Goods g = (Goods) t;
			ArrayList<Goods> list = bii.readAll(file);
			list.replaceAll((o) -> {
				if (o.getGid() == g.getGid()) {
					return g;
				}
				return o;
			});
			bool = bii.writeAll(list, file);
		} else {
			ArrayList<T> list = new ArrayList<T>();
			list = bii.readAll(file);
			list.replaceAll((o) -> {
				if (o == t) {
					return t;
				}
				return o;
			});
			bool = bii.writeAll(list, file);
		}
		return bool;
	}
}
