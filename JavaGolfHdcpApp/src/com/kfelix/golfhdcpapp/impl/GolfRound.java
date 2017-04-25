package com.kfelix.golfhdcpapp.impl;

import java.util.Date;

public class GolfRound {

	private Date roundDate;
	private int roundScore;
	private GolfCourse course;
	private Golfer golfer;
	
	public GolfRound(Golfer g, GolfCourse gc) {
		golfer = g;
		course = gc;
	}
	
	public String getGolfer() {
		return golfer.getName();
	}
	
	public String getCourse() {
		return course.getName();
	}
	
	public void setDate(Date d) {
		roundDate = d;
		System.out.println("Date of this round : " + roundDate);
	}
	
	public String getDate() {
		return roundDate.toString();
	}

	public void setScore(int s) {
		roundScore = s;
		System.out.println("Score of this round : " + roundScore);
	}
	
	public int getScore() {
		return roundScore;
	}

	public void printRound() {
		System.out.println("This round was played by " + golfer.getName() +
							" on " + roundDate.toString() +
							" at " + course.getName() + ", score was " +
							roundScore);
	}
}
