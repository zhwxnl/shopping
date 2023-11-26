package com.woniuxy.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

import com.woniuxy.BaseDao.BaseImpl.InputNumber;
import com.woniuxy.model.Address;
import com.woniuxy.model.Goods;
import com.woniuxy.model.User;
import com.woniuxy.modelDao.modelImpl.ModelImpl1;
import com.woniuxy.modelDao.modelImpl.ModelImpl2;
import com.woniuxy.serviceDao.serviceImpl.ServiceImpl;

public class Util {

	private ServiceImpl si;
	Scanner sc = new Scanner(System.in);

	public void menu() {
		System.out.println("��ѡ��1�����ݿ� 2���ļ�");
		si = InputNumber.getNum(1, 2) == 1 ? new ServiceImpl(new ModelImpl1()) : new ServiceImpl(new ModelImpl2());
		a: while (true) {
			System.out.println("��ѡ��1��ע�� 2����¼ 3����ѯ 4������ 5���˳�");
			int choose = InputNumber.getNum(1, 5);
			switch (choose) {
			case 1: {
				User user = si.addUser();
				System.out.println( user!= null ? "ע��ɹ�,��Ŀ���Ϊ��"+user.getCardId() : "ע��ʧ��");
				break;
			}
			case 2: {
				System.out.println("�����뿨��");
				String cardId = sc.next();
				System.out.println("����������");
				String pwd = sc.next();
				User user = si.login(cardId, pwd);
				if (user == null) {
					System.out.println("�˺Ż�������������");
					break;
				}
				System.out.println("��ӭ��½:" + user.getName());
				while (true) {
					System.out.println("��ѡ��1��չʾ������Ϣ 2���ϴ���Ʒ 3��չʾ��Ʒ��Ϣ 4��������Ʒ 5����ֵ 6���˳���¼");
					int c = InputNumber.getNum(1, 6);
					switch (c) {
					case 1: {
						System.out.println("�û�id\t���ѵȼ�\t����\t\t\t\t\t����\t����\t���\t���ѽ��");
						System.out.println(user);
						break;
					}
					case 2: {
						System.out.println(si.upGoods(user.getCardId()) ? "�ϴ��ɹ�" : "�ϴ�ʧ��");
						break;
					}
					case 3: {
						while (true) {
							System.out.println("��ѡ��1��ȫ�����ź��ۼ����ѽ�� 2�����ز�ѯ 3����ҳ��ѯ 4��״̬��ѯ 5�����Ͳ�ѯ 6�����ò�Ʒ 7���ҵ���Ʒ");
							int n = InputNumber.getNum(1, 7);
							if (n == 7) {
								si.showMy(user);
							} else {
								show(n);
							}
							System.out.println("�Ƿ������ѯy/n");
							if (!sc.next().equals("y")) {
								break;
							}
						}
						break;
					}
					case 4: {
						int type=1;
						try {
							Field f = si.getClass().getDeclaredField("f");
							f.setAccessible(true);
							if(f.get(si).getClass()==ModelImpl1.class) {
								System.out.println("��ѡ��������1.�˶���2.ʳƷ��3.�ľ���");
								type= InputNumber.getNum(1, 3);
								String typestr=type==1?"�˶���":type==2?"ʳƷ��":"�ľ���";
								ArrayList<Goods> all = si.getAll(type);
								if(all==null||all.isEmpty()) {
									System.out.println("û��"+typestr+"����Ʒ��");
									break;
								}
								if(all.stream().filter(o->o.getState().equals("����")&&!o.getSellCard().equals(user.getCardId())).count()==0) {
									System.out.println("û�п��Թ������Ʒ!");
									break;
								}
								System.out.println("��Ʒid\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
								all.stream().filter(o->o.getState().equals("����")&&!o.getSellCard().equals(user.getCardId())).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
							}else {
								ArrayList<Goods> all = si.getAll();
								if(all==null||all.isEmpty()) {
									System.out.println("û�в�ѯ����Ʒ");
									break;
								}
								if(all.stream().filter(o->o.getState().equals("����")&&!o.getSellCard().equals(user.getCardId())).count()==0) {
									System.out.println("û�п��Թ������Ʒ!");
									break;
								}
								System.out.println("��Ʒid\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
								all.stream().filter(o->o.getState().equals("����")&&!o.getSellCard().equals(user.getCardId())).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
							}
						} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("�����빺����Ʒid");
						int gid = InputNumber.getIntNum();
						System.out.println(si.buyGoods(user, type, gid) ? "����ɹ�" : "����ʧ��");
						break;
					}
					case 5: {
						System.out.println("�������ֵ���");
						double money = InputNumber.getDoubleNum();
						System.out.println(si.addMoney(user, cardId, money) ? "��ֵ�ɹ�" : "��ֵʧ��");
						break;
					}
					case 6: {
						System.out.println("��ӭ�´ε�¼");
						continue a;
					}
					default: {
						System.out.println("ѡ������");
					}

					}
				}
			}
			case 3: {
				while (true) {
					System.out.println("��ѡ��1��ȫ�����ź��ۼ����ѽ�� 2�����ز�ѯ 3����ҳ��ѯ 4��״̬��ѯ 5�����Ͳ�ѯ 6�����ò�Ʒ ");
					int n = InputNumber.getNum(1, 6);
					show(n);
					System.out.println("�Ƿ������ѯy/n");
					if (!sc.next().equals("y")) {
						break;
					}
				}
				break;
			}
			case 4: {
				while (true) {
					System.out.println("��ѡ��1��չʾ���в��� 2���������� ");
					int n = InputNumber.getNum(1, 2);
					if (n == 1) {
						ArrayList<Address> address = si.getAddress();
						if (address == null || address.isEmpty()) {
							System.out.println("δ��ѯ�����ݣ�");
						} else {
							System.out.println("����id\t����");
							address.stream().sorted((o1, o2) -> o1.getTid() - o2.getTid()).forEach(System.out::println);
						}
					} else if (n == 2) {
						System.out.println(si.addAddress() ? "�����ɹ�" : "����ʧ��");
					}
					System.out.println("�Ƿ��������y/n");
					if (!sc.next().equals("y")) {
						break;
					}
				}
				break;
			}
			case 5: {
				System.out.println("��ӭ�´�ʹ��");
				System.exit(0);
			}
			default: {
				System.out.println("��������");
			}
			}
		}
	}

	public void show(int n) {
		if (n == 1) {
			ArrayList<User> users = si.getUsers();
			if (users == null || users.isEmpty()) {
				System.out.println("δ��ѯ�����ݣ�");
			} else {
				System.out.println("����\t\t\t\t\t�ۼ����ѽ��");
				users.stream().sorted((o1, o2) -> o1.getAllMoney() > o2.getAllMoney() ? -1 : 1)
						.forEach(o -> System.out.println(o.getCardId() + "\t" + o.getAllMoney()));
			}
		} else if (n == 2) {
			si.show3();
		} else if (n == 3) {
			si.show1();
		} else if (n == 4) {
			si.show2();
		} else if (n == 5) {
			System.out.println("��ѡ���ѯ����1.�˶���2.ʳƷ��3.�ľ���");
			int type = InputNumber.getNum(1, 3);
			ArrayList<Goods> all = si.getAll(type);
			String typestr=type == 1 ? "�˶���" : type == 2 ? "ʳƷ��" : "�ľ���";
			if (all == null || all.isEmpty()) {
				System.out.println("û��" + typestr + "����Ʒ");
			} else {
				if(all.stream().filter(o->o.getState().equals("����")).count()>0) {
					System.out.println("��Ʒid\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t\t\t\t\t��������");
					all.stream().forEach(System.out::println);
				}else {
					System.out.println("��Ʒid\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
					all.stream().map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
				}
			}
		} else if (n == 6) {
			int type=1;
			try {
				Field f = si.getClass().getDeclaredField("f");
				f.setAccessible(true);
				if(f.get(si).getClass()==ModelImpl1.class) {
					System.out.println("��ѡ����������1.�˶���2.ʳƷ��3.�ľ���");
					type= InputNumber.getNum(1, 3);
					ArrayList<Goods> all = si.getAll(type);
					if(all==null||all.isEmpty()) {
						System.out.println("û��"+(type==1?"�˶���":type==2?"ʳƷ��":"�ľ���")+"����Ʒ��");
						return;
					}
					System.out.println("��Ʒid\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
					all.stream().filter(o->o.getState().equals("����")).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
				}else {
					ArrayList<Goods> all = si.getAll();
					if(all==null||all.isEmpty()) {
						System.out.println("û�в�ѯ����Ʒ");
						return;
					}
					System.out.println("��Ʒid\t����\t״̬\t����\t�ۼ�\t���ҿ���\t\t\t\t\t��ҿ���\t��������");
					all.stream().filter(o->o.getState().equals("����")).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("������������Ʒid");
			int gid = InputNumber.getIntNum();
			si.doGoods(type, gid);
		}
	}

}
