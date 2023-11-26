package com.woniuxy.model;

import java.io.Serializable;

public abstract class Goods implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键	
	private int gid;    

	//品名	
	private String name;//记事本 所有商品不可重复   数据库 本类商品不可重复

	//状态	
	private String state="待售";//	（待售/已售）默认"待售" 

	//产地	
	private String address;	

	//售价	
	private double money;

	//商品卖家	
	private String sellCard;

	//商品买家	
	private String buyCard=null;//	默认为null

	
	public Goods() {
		super();
	}


	public Goods(int gid, String name, String state, String address, double money, String sellCard, String buyCard) {
		super();
		this.gid = gid;
		this.name = name;
		this.state = state;
		this.address = address;
		this.money = money;
		this.sellCard = sellCard;
		this.buyCard = buyCard;
	}

	

	public Goods(int gid, String name, String address,double money, String sellCard) {
		super();
		this.gid = gid;
		this.name = name;
		this.address = address;
		this.money = money;
		this.sellCard = sellCard;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public double getMoney() {
		return money;
	}


	public void setMoney(double money) {
		this.money = money;
	}


	public String getBuyCard() {
		return buyCard;
	}


	public void setBuyCard(String buyCard) {
		this.buyCard = buyCard;
	}


	public int getGid() {
		return gid;
	}


	public String getName() {
		return name;
	}


	public String getSellCard() {
		return sellCard;
	}

	public abstract void gotIt();
	@Override
	public String toString() {
		return  gid + "\t" + name + "\t" + state + "\t" + address + "\t" + money
				+ "\t" + sellCard + "\t" + buyCard ;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gid;
		return result;
	}
	
}
