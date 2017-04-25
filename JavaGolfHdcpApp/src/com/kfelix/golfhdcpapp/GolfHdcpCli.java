package com.kfelix.golfhdcpapp;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import com.kfelix.golfhdcpapp.impl.*;
import com.kfelix.golfhdcpapp.exceptions.InvalidUserException;

public class GolfHdcpCli {

	private GolfHcpInterface ghi;
	private GolfHdcpConfig gc;
	private Scanner in;
	private int ii;
	public GolfHdcpCli(GolfHdcpConfig gconfig) {
		
		gc = gconfig;
		ghi = new FilesystemIntfc(gc);
		in = new Scanner(System.in);
		ii = 0;
	}
	
	public void run() {
		
		boolean done = false;
		
		/* Display the menu and execute the requests */
	    while(!done) {
	    	print("\nWhat would you like to do?");
	    	print("\n\t1)Add a new golfer.");
	    	print("\n\t2)Add a new course.");
	    	print("\n\t3)Add a new round.");
	    	print("\n\t4)Add a new user.");
	    	print("\n\t5)Remove a user.");
	    	print("\n\t6)List users.");
	    	print("\n\t7)Exit.");
	    	ii= in.nextInt();
	    	
	    	/* To consume \n that nextInt() ignored. */
	    	in.nextLine();
	    	
	    	switch(ii) {
	    		case 1:
	    			addGolfer();
	    			break;
	    		
	    		case 2:
	    			addCourse();
	    			break;
	    		
	    		case 3:
	    			addRound();
	    			break;
	    		case 4:
	    			addUser();
	    			break;
	    			
	    		case 5:
	    			removeUser();
	    			break;
	    			
	    		case 6:
	    			listUsers();
	    			break;

	    		case 7:
	    			done = true;
	    			break;
	    			
	    		default:
	    			print("\nIncorrect response!!");
	    			break;
	    	}
	    }
		
	}
	
	public void addGolfer() {
		Golfer g = new Golfer();
		print("Please input the golfer's name :");
		g.setName(in.nextLine());
	}
	
	public void addCourse() {
		GolfCourse c = new GolfCourse();
		print("Please input the name of the course : ");
		c.setName(in.nextLine());
	}
	
	public void addRound() {
		Golfer g = new Golfer();
		GolfCourse c = new GolfCourse();
		
		System.out.println("Who played this round : ");
		g.setName(in.nextLine());
		
		System.out.println("What course was this round played on : ");
		c.setName(in.nextLine());
		
		String date = "";

//	    for(int i=0; i<3; i++) {
//	        System.out.println("Enter " + str[i] + ": ");
//	        date = date + in.next() + "-";
//	    }
//	    date = date.substring(0, date.length()-1);
	    
	    System.out.println("Enter the date of the round (mm-dd-yyyy) ");
	    date = in.nextLine();
	    System.out.println("date: "+ date); 

	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	    Date parsedDate = null;

	    try {
	        parsedDate = dateFormat.parse(date);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
		
	    GolfRound r = new GolfRound(g, c);
		r.setDate(parsedDate);
		
		System.out.println("What was your score : ");
		r.setScore(in.nextInt());
		
		r.printRound();
		ghi.addRound(r);
		
	}
	
	private void addUser() {
		print("Please input the user's name :");
		String user = in.nextLine();
		print("Please input the user's password :");
		String pwrd = in.nextLine();
		
		try {
			gc.addUser(user, pwrd);
		} catch (InvalidUserException e)  {
			print("CLI addUser caught an exception");
			System.err.println(e.getMessage());
		}
	}
	
	private void removeUser() {
		print("Please input the user's name :");
		String user = in.nextLine();
		
		try {
			gc.removeUser(user);
		} catch (InvalidUserException e)  {
			System.err.println(e.getMessage());
		}
	}
	
	private void listUsers() {
		gc.listUsers();
	}
	private void print(String s) {
		System.out.println(s);
	}

}
