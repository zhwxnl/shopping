package com.woniuxy.modelDao;

import java.util.ArrayList;

import com.woniuxy.model.User;

public interface UserDao {
	//ע��
	public User addUser();
	//��ѯȫ���û�	ʹ��ע�ⷴ�������ѯ
	public ArrayList<User> getUsers();
	//���Ų����û�
	public User getByName(String cardId);
	//��¼ ����+����
	public User login(String cardId,String pwd);
	//�ϴ���Ʒ ����Ϊ���ҿ���
	public boolean upGoods(String cardId);
	//��ֵ (�����ڸ��Լ���ֵ �����ڹ����������ҳ�ֵ)	����Ϊ��ǰ��¼�� ��ֵ���� ��ֵ���
	public boolean addMoney(User u,String cardId,double money);
	//�ۿ�(����Ʒʱʹ��) ����λ��ǰ��¼�� �ۿ���
	public boolean moveMoney(User u,double money);
}
