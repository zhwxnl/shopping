package com.woniuxy.modelDao;

import java.util.ArrayList;

import com.woniuxy.model.Address;

public interface AddressDao {
	//��ѯ���е�ַ	ʹ��ע�ⷴ�������ѯ
	public ArrayList<Address> getAddress();
	//��ӵ�ַ  ע�ⲻ���ظ�
	public boolean	addAddress();
}
