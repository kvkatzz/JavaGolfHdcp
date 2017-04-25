package com.kfelix.golfhdcpapp;



import java.util.Date;
import java.util.prefs.Preferences;

//import com.kfelix.util.KevUtility;

import java.net.*;

/**
 * Kicks off the Golf Handicap application.
 */

/**
 * @author Kevin J. Felix
 *
 */
public class GolfHcpLauncher {

	
	
	/**
	 * 
	 */
	public GolfHcpLauncher() {
		// TODO Auto-generated constructor stub
	}
	
	private static void print(String s) {
		System.out.println(s);
	}

	private void login() {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Best Golf Handicap app ever!!");
		System.out.println("Start time : " + (new Date()).toString() );
		print("\n--------------------------------------------------------");
		System.out.println(System.getProperty("os.name"));
		System.out.println(System.getProperty("os.version"));
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("java.class.path"));
		System.out.println(System.getProperty("java.version"));
		print("--------------------------------------------------------\n");
		
		
//		System.out.println(System.getProperty("java.class.path"));
//		
//		ClassLoader cl = ClassLoader.getSystemClassLoader();
//
//        URL[] urls = ((URLClassLoader)cl).getURLs();
//
//        for(URL url: urls){
//        	System.out.println(url.getFile());
//        }
        
		/* Initialize the system if this is the first time in 
		 * 
		 * - Initialize the data store (filesystem or mysql db)
		 * - Build the config file for future startups
		 * 
		 */
		GolfHdcpConfig gc = new GolfHdcpConfig();
		gc.configure();
		
		try {
		
			gc.login();
			
			GolfHdcpCli gcli = new GolfHdcpCli(gc);
			/* Start up the cli version for now */
			gcli.run();

		} catch(Exception e) {
			System.err.println("GHA threw an exception : " + e.getMessage());
		}
		
		
		print("Goodbye!!");
		
	}
	
	

}
