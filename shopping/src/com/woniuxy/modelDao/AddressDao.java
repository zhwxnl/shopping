package com.woniuxy.modelDao;

import java.util.ArrayList;

import com.woniuxy.model.Address;

public interface AddressDao {
	//查询所有地址	使用注解反射整表查询
	public ArrayList<Address> getAddress();
	//添加地址  注意不能重复
	public boolean	addAddress();
}
