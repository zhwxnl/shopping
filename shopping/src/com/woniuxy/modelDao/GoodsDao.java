package com.woniuxy.modelDao;

import java.util.ArrayList;

import com.woniuxy.model.Goods;
import com.woniuxy.model.User;

public interface GoodsDao {
	//������Ʒ���� ��Ʒid��ѯ��Ʒ
	public Goods getById(int type,int gid);
	//��ѯĳƷ��������Ʒ ����Ϊ 1.�˶���2ʳƷ��3.�ľ���  
	public ArrayList<Goods> getAll(int type);
	//���ݿⷽ���� ʹ������(��Ӧ��Ʒ��+���ر�)���ϲ�ѯ 
	//���±����� ֱ�Ӷ�Goods���±� ��ü��� ��ɸѡ
	//��ȡ������Ʒ���ɵĸ��༯��
	public ArrayList<Goods> getAll();
	//���ݿⷽ���� Ҫ��������Ʒ����Ʒȫ���ó����븸�༯��
	//��ҳ��ѯÿҳ������Ʒ��Ϣ
	public void show1();
	//״̬��ѯ 1.���� 2.���� ѡ��� ��ѯ��1.�۸�����2.�۸���
	public void show2();
	//���ز�ѯ 1.�۸�����2.�۸���
	public void show3();
	//�ҵ���Ʒ
	public void showMy(User u);
	////���ò�Ʒ
	public void doGoods(int type,int gid);
}
