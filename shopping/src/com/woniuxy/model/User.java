package com.woniuxy.model;

import java.io.Serializable;
import java.util.UUID;

@TypeAnno("user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int uid;
	private	String level = "F";
	private String cardId=UUID.randomUUID().toString();
	private String name;
	private String pwd;
	private double money;
	private double allMoney;
	@ConsAnno(7)
	public User(int uid, String level, String cardId, String name, String pwd, double money, double allMoney) {
		super();
		this.uid = uid;
		this.level = level;
		this.cardId = cardId;
		this.name = name;
		this.pwd = pwd;
		this.money = money;
		this.allMoney = allMoney;
	}
	public User(int uid, String name, String pwd) {
		super();
		this.uid = uid;
		this.name = name;
		this.pwd = pwd;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(double allMoney) {
		this.allMoney = allMoney;
	}
	public int getUid() {
		return uid;
	}
	public String getCardId() {
		return cardId;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean checkPwd(String pwd){
		return this.pwd.equals(pwd);
	}
	@Override
	public String toString() {
		return uid + "\t" + level + "\t" + cardId + "\t" + name + "\t" + pwd
				+ "\t" + money + "\t" + allMoney;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uid;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (uid != other.uid)
			return false;
		return true;
	}
	
}
