package com.kfelix.golfhdcpapp.impl;

public class GolfCourse {

	private String name;
	private float rating;
	private int slope;
	
	
	public GolfCourse() {
		name = null;
		rating = 0;
		slope = 0;
	}

	public void setName(String n) {
		name = n;
		System.out.println("Golf course : " + name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setRating(float r) {
		rating = r;
		System.out.println("Course rating : " + rating);
	}

	public void setSlope(int s) {
		slope = s;
		System.out.println("Course slope : " + slope);
	}
}
