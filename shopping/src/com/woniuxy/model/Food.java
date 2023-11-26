package com.woniuxy.model;
@TypeAnno("food")
public class Food extends Goods {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 热量
	private String caloric;

	public Food(int gid, String name, String state, String address, double money, String sellCard, String buyCard,
			String caloric) {
		super(gid, name, state, address, money, sellCard, buyCard);
		this.caloric = caloric;
	}

	
	public Food(int gid, String name, String address,double money, String sellCard,String caloric) {
		super(gid,name,address,money,sellCard);
		this.caloric = caloric;
	}


	public void eatFood() {
		System.out.println("美食让我堕落");
	}

	@Override
	public void gotIt() {
		// TODO Auto-generated method stub
		eatFood();
	}

	
	@Override
	public String toString() {
		return super.toString()+(getBuyCard()==null?"\t\t\t\t\t":"\t")+ caloric;
	}

}
