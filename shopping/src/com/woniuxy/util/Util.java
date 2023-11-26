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
		System.out.println("请选择1、数据库 2、文件");
		si = InputNumber.getNum(1, 2) == 1 ? new ServiceImpl(new ModelImpl1()) : new ServiceImpl(new ModelImpl2());
		a: while (true) {
			System.out.println("请选择1、注册 2、登录 3、查询 4、产地 5、退出");
			int choose = InputNumber.getNum(1, 5);
			switch (choose) {
			case 1: {
				User user = si.addUser();
				System.out.println( user!= null ? "注册成功,你的卡号为："+user.getCardId() : "注册失败");
				break;
			}
			case 2: {
				System.out.println("请输入卡号");
				String cardId = sc.next();
				System.out.println("请输入密码");
				String pwd = sc.next();
				User user = si.login(cardId, pwd);
				if (user == null) {
					System.out.println("账号或密码输入有误！");
					break;
				}
				System.out.println("欢迎登陆:" + user.getName());
				while (true) {
					System.out.println("请选择1、展示个人信息 2、上传商品 3、展示商品信息 4、购买商品 5、充值 6、退出登录");
					int c = InputNumber.getNum(1, 6);
					switch (c) {
					case 1: {
						System.out.println("用户id\t消费等级\t卡号\t\t\t\t\t姓名\t密码\t余额\t消费金额");
						System.out.println(user);
						break;
					}
					case 2: {
						System.out.println(si.upGoods(user.getCardId()) ? "上传成功" : "上传失败");
						break;
					}
					case 3: {
						while (true) {
							System.out.println("请选择1、全部卡号和累计消费金额 2、产地查询 3、分页查询 4、状态查询 5、类型查询 6、试用产品 7、我的商品");
							int n = InputNumber.getNum(1, 7);
							if (n == 7) {
								si.showMy(user);
							} else {
								show(n);
							}
							System.out.println("是否继续查询y/n");
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
								System.out.println("请选择购买类型1.运动类2.食品类3.文具类");
								type= InputNumber.getNum(1, 3);
								String typestr=type==1?"运动类":type==2?"食品类":"文具类";
								ArrayList<Goods> all = si.getAll(type);
								if(all==null||all.isEmpty()) {
									System.out.println("没有"+typestr+"的商品！");
									break;
								}
								if(all.stream().filter(o->o.getState().equals("待售")&&!o.getSellCard().equals(user.getCardId())).count()==0) {
									System.out.println("没有可以购买的商品!");
									break;
								}
								System.out.println("商品id\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
								all.stream().filter(o->o.getState().equals("待售")&&!o.getSellCard().equals(user.getCardId())).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
							}else {
								ArrayList<Goods> all = si.getAll();
								if(all==null||all.isEmpty()) {
									System.out.println("没有查询到商品");
									break;
								}
								if(all.stream().filter(o->o.getState().equals("待售")&&!o.getSellCard().equals(user.getCardId())).count()==0) {
									System.out.println("没有可以购买的商品!");
									break;
								}
								System.out.println("商品id\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
								all.stream().filter(o->o.getState().equals("待售")&&!o.getSellCard().equals(user.getCardId())).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
							}
						} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("请输入购买商品id");
						int gid = InputNumber.getIntNum();
						System.out.println(si.buyGoods(user, type, gid) ? "购买成功" : "购买失败");
						break;
					}
					case 5: {
						System.out.println("请输入充值金额");
						double money = InputNumber.getDoubleNum();
						System.out.println(si.addMoney(user, cardId, money) ? "充值成功" : "充值失败");
						break;
					}
					case 6: {
						System.out.println("欢迎下次登录");
						continue a;
					}
					default: {
						System.out.println("选择有误");
					}

					}
				}
			}
			case 3: {
				while (true) {
					System.out.println("请选择1、全部卡号和累计消费金额 2、产地查询 3、分页查询 4、状态查询 5、类型查询 6、试用产品 ");
					int n = InputNumber.getNum(1, 6);
					show(n);
					System.out.println("是否继续查询y/n");
					if (!sc.next().equals("y")) {
						break;
					}
				}
				break;
			}
			case 4: {
				while (true) {
					System.out.println("请选择1、展示所有产地 2、新增产地 ");
					int n = InputNumber.getNum(1, 2);
					if (n == 1) {
						ArrayList<Address> address = si.getAddress();
						if (address == null || address.isEmpty()) {
							System.out.println("未查询到数据！");
						} else {
							System.out.println("产地id\t产地");
							address.stream().sorted((o1, o2) -> o1.getTid() - o2.getTid()).forEach(System.out::println);
						}
					} else if (n == 2) {
						System.out.println(si.addAddress() ? "新增成功" : "新增失败");
					}
					System.out.println("是否继续操作y/n");
					if (!sc.next().equals("y")) {
						break;
					}
				}
				break;
			}
			case 5: {
				System.out.println("欢迎下次使用");
				System.exit(0);
			}
			default: {
				System.out.println("输入有误！");
			}
			}
		}
	}

	public void show(int n) {
		if (n == 1) {
			ArrayList<User> users = si.getUsers();
			if (users == null || users.isEmpty()) {
				System.out.println("未查询到数据！");
			} else {
				System.out.println("卡号\t\t\t\t\t累计消费金额");
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
			System.out.println("请选择查询类型1.运动类2.食品类3.文具类");
			int type = InputNumber.getNum(1, 3);
			ArrayList<Goods> all = si.getAll(type);
			String typestr=type == 1 ? "运动类" : type == 2 ? "食品类" : "文具类";
			if (all == null || all.isEmpty()) {
				System.out.println("没有" + typestr + "的商品");
			} else {
				if(all.stream().filter(o->o.getState().equals("已售")).count()>0) {
					System.out.println("商品id\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t\t\t\t\t特有属性");
					all.stream().forEach(System.out::println);
				}else {
					System.out.println("商品id\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
					all.stream().map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
				}
			}
		} else if (n == 6) {
			int type=1;
			try {
				Field f = si.getClass().getDeclaredField("f");
				f.setAccessible(true);
				if(f.get(si).getClass()==ModelImpl1.class) {
					System.out.println("请选择试用类型1.运动类2.食品类3.文具类");
					type= InputNumber.getNum(1, 3);
					ArrayList<Goods> all = si.getAll(type);
					if(all==null||all.isEmpty()) {
						System.out.println("没有"+(type==1?"运动类":type==2?"食品类":"文具类")+"的商品！");
						return;
					}
					System.out.println("商品id\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
					all.stream().filter(o->o.getState().equals("待售")).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
				}else {
					ArrayList<Goods> all = si.getAll();
					if(all==null||all.isEmpty()) {
						System.out.println("没有查询到商品");
						return;
					}
					System.out.println("商品id\t名称\t状态\t产地\t售价\t卖家卡号\t\t\t\t\t买家卡号\t特有属性");
					all.stream().filter(o->o.getState().equals("待售")).map(o->o.toString().replaceAll("\\s+", "\t")).forEach(System.out::println);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("请输入试用商品id");
			int gid = InputNumber.getIntNum();
			si.doGoods(type, gid);
		}
	}

}
