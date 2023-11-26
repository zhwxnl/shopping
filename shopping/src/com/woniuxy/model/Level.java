package com.woniuxy.model;

public enum Level {
		Levle1("F",0,1),
		Levle2("E",500,0.95),
		Levle3("D",5000,0.8),
		Levle4("C",10000,0.75),
		Levle5("B",13000,0.7),	
		Levle6("A",25000,0.6);
	//等级			
	private String levelNum;//	(赋值为"A"、"B"..)	 	
	//达标金额	
	private double money;	 				

	//享受折扣	
	private double discount;

	private Level(String levelNum, double money, double discount) {
		this.levelNum = levelNum;
		this.money = money;
		this.discount = discount;
	}

	public String getLevelNum() {
		return levelNum;
	}

	public double getMoney() {
		return money;
	}

	public double getDiscount() {
		return discount;
	}
	
}
