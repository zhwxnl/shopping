package com.woniuxy.model;

import java.io.Serializable;

@TypeAnno("address")
public class Address implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//主键		
	private int tid;
	//产地（不可重复）
	private String address;
	@ConsAnno(2)
	public Address(int tid, String address) {
		super();
		this.tid = tid;
		this.address = address;
	}
	public int getTid() {
		return tid;
	}
	public String getAddress() {
		return address;
	}
	@Override
	public String toString() {
		return  tid + "\t" + address;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tid;
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
		Address other = (Address) obj;
		if (tid != other.tid)
			return false;
		return true;
	}
	
}
