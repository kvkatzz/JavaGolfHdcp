package com.kfelix.golfhdcpapp.impl;

public class Golfer {

	private String name;
	
	public Golfer() {
		// TODO Auto-generated constructor stub
	}
	
	public void setName(String n) {
		name = n;
		System.out.println("Golfer : " + name);
	}
	
	public String getName() {
		return name;
	}

}
