package com.woniuxy.model;
@TypeAnno("study")
public class Study extends Goods {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 品牌
	private String brand;

	public Study(int gid, String name, String state, String address, double money, String sellCard, String buyCard,
			String brand) {
		super(gid, name, state, address, money, sellCard, buyCard);
		this.brand = brand;
	}
	public Study(int gid, String name, String address, double money,String sellCard,String brand) {
		super(gid,name,address,money,sellCard);
		this.brand = brand;
	}
	public void goStudy() {
		System.out.println("学习给我整失眠了");
	}

	@Override
	public void gotIt() {
		// TODO Auto-generated method stub
		goStudy();
	}

	@Override
	public String toString() {
		return super.toString()+(getBuyCard()==null?"\t\t\t\t\t":"\t")+ brand ;
	}
	
}
