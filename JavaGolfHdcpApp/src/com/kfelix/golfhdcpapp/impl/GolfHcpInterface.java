package com.kfelix.golfhdcpapp.impl;

import java.util.Date;

public interface GolfHcpInterface {
	/** 
	 * Adds a golfer profile to the system.
	 * 
	 * @param golfer
	 */
	void addGolfer(Golfer golfer);
	
	/**
	 * Returns the profile of a particular golfer.
	 * @param name
	 * @return
	 */
	Golfer findGolfer(String name);
	
	/**
	 * Adds a course profile to the system.
	 * 
	 * @param course
	 */
	void addCourse(GolfCourse course);
	
	/**
	 * Returns info on a particular course.
	 * @param name
	 * @return
	 */
	GolfCourse getCourse(String name);
	
	/**
	 * Inputs a specific round of golf.
	 * 
	 * @param course
	 */
	void addRound(GolfRound round);
	
	/**
	 * Returns a specific round of golf.
	 * @param date
	 * @param course
	 * @return
	 */
	GolfRound getRound(Date date, GolfCourse course);

}
