package com.woniuxy.model;
@TypeAnno("sport")
public class Sport extends Goods {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 材质
	private String material;

	public Sport(int gid, String name, String state, String address, double money, String sellCard, String buyCard,
			String material) {
		super(gid, name, state, address, money, sellCard, buyCard);
		this.material = material;
	}
	public Sport(int gid, String name, String address,double money, String sellCard,String material) {
		super(gid,name,address,money,sellCard);
		this.material = material;
	}
	public void doSport() {
		System.out.println("运动使我容易饿");
	}

	@Override
	public void gotIt() {
		// TODO Auto-generated method stub
		doSport();
	}

	@Override
	public String toString() {
		return super.toString()+(getBuyCard()==null?"\t\t\t\t\t":"\t")+ material ;
	}

}
