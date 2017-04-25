package com.kfelix.golfhdcpapp;

import java.nio.file.Files;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.Scanner;
import java.util.prefs.Preferences;
import javax.security.auth.login.*;


import com.kfelix.golfhdcpapp.exceptions.*;


public class GolfHdcpConfig {

	public static enum dbType {
		NONE, FILE, MYSQL
	}
	
	private final String defaultDataFilesPath = "/home/kevin/gha-app/datafiles";
	private final String defaultStoreType = "FILE";
	
	
	private int userCount;
	private String currentUser;
	private final String adminUser = "admin";
	private String dataFilesPath;
	private Preferences syspref;
	private Preferences userpref;
	private Scanner in;
	
	private final String golfersDataFile = "/Golfers";
	private final String coursesDataFile = "/Courses";
	private final String roundsDataFile = "/Rounds";

	dbType dbt;
	
	@interface ClassPreamble {
		   String author();
		   String date();
		   int currentRevision() default 1;
		   String lastModified() default "N/A";
		   String lastModifiedBy() default "N/A";
		   // Note use of array
		   String[] reviewers();
		}
	
	@ClassPreamble (
			   author = "Kevin Felix",
			   date = "3/17/2002",
			   currentRevision = 6,
			   lastModified = "4/12/2004",
			   lastModifiedBy = "Jane Doe",
			   // Note array notation
			   reviewers = {"Alice", "Bob", "Cindy"}
			)
	public GolfHdcpConfig() {
		dbt = dbType.NONE;

		// Returns the preference node from the system preference tree that is 
		// associated (by convention) with the specified class's (this class) package.
		syspref = Preferences.systemNodeForPackage(this.getClass());
		userpref = Preferences.userNodeForPackage(this.getClass());
		
		userCount = syspref.getInt("GHA_USER_COUNT", 0);
		
		in = new Scanner(System.in) ;
		System.out.println("GolfHdcpConfig cstr entered, currentUser : " + currentUser);
	}

	public void configure() {
		
		String datastoreType = syspref.get("GHA_STORE_TYPE", "NONE");
		
		System.out.println("GHA datastore type is : " + datastoreType);

		/* See if this is the first time configuring the system */
		if(datastoreType == "NONE") {
			/* This means the datastore is not set up yet. */
			
			/* !!!!! TODO ... Querie the installer on what to use, file or mysql */
		
			System.out.println("Creating the data store, type " + defaultStoreType);
			
			/* For now just use file .... */
			syspref.put("GHA_STORE_TYPE", defaultStoreType);
			syspref.put("GHA_STORE_LOCATION", defaultDataFilesPath);
			dataFilesPath = defaultDataFilesPath;
			
			/* Add the default 'admin' user */
			syspref.put("User_" + String.valueOf(000 + (userCount)), "admin");
			syspref.put("User_" + String.valueOf(000 + (userCount)) + "_pwrd", "admin");
			syspref.putInt("GHA_USER_COUNT", ++userCount);
			
			
			try {
				syspref.flush();
			} catch(Exception e) {
				System.err.println("Preferences flush() exception : " + e.getMessage());
				System.err.println("Make sure you run this app as root the first time");
				return;
			}
			
			/* Sanity check, the path should not exist */
			Path fp = Paths.get(dataFilesPath);
			BufferedWriter bw = null;
			
			if(!Files.exists(fp)) {
				try {
					/* Does not exist, create it */
					System.out.println("Creating filepath : " + fp.toString());
					Files.createDirectories(fp);
					
					/* Now create each of the separate data stores */
					// Create the 'Rounds' data store
					Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-rw-");
					FileAttribute<Set<PosixFilePermission>> fileAttrs = PosixFilePermissions.asFileAttribute(perms);
					
					Path rd = Paths.get(dataFilesPath + roundsDataFile);
					Files.createFile(rd, fileAttrs);
					bw = new BufferedWriter(new FileWriter(dataFilesPath + roundsDataFile));
					
					/* This is basically a header for the file, only for external viewing */
					bw.write("|Date|Golfer|Course|Score|\n");
					
				} catch(Exception e){
					System.err.println("Error creating filestore files : " + e.getMessage());
				} finally {
					if(bw != null){
						try {
							bw.close();
						} catch(IOException e) {
							System.err.println("Error closing the data file : " + e.getMessage());
						}
					}
				}
			}
		}

		/* Initialize this class with the current config info */
		dataFilesPath = defaultDataFilesPath;
		userCount = syspref.getInt("GHA_USER_COUNT", 0);
		System.out.println("GHA current user count : " + userCount);
	}
		
	public String getDataFilesPath() {
		return dataFilesPath;
	}

	public Path getRoundsFilePath() {
		System.out.println("getRoundsFilePath : " + dataFilesPath + ", " + roundsDataFile);
		return Paths.get(dataFilesPath + roundsDataFile);
	}
	
	public Path getCoursesFilePath() {
		return Paths.get(dataFilesPath + coursesDataFile);
	}
	
	public Path getGolfersFilePath() {
		return Paths.get(dataFilesPath + golfersDataFile);
	}
	
	
	
	public GolfHdcpUser getUser(String name) 
			throws InterruptedException, UserNotFoundException {	
		
		print("getUser looking for " + name);
		
		/* Get the user count */		
		userCount = syspref.getInt("GHA_USER_COUNT", 0);
		if(userCount <= 0) {
			System.out.println("No users currently.");
			throw new UserNotFoundException("No users currently in system.");
		}
		
		for(int i=0; i < userCount; i++) {
			String user = syspref.get("User_"  + String.valueOf(i), "NONE");
			print("User_" + String.valueOf(i) + " is " + user);
			
			if(user.equals(name)) {
				System.out.println("Found user " + name);
				GolfHdcpUser ghu = new GolfHdcpUser();
				
				try {
					ghu.setUser(name);
					ghu.setId(i);
				
					String pwrd = syspref.get("User_" + String.valueOf(000 + i) + "_pwrd", "admin");
					print("User_" + String.valueOf(i) + "_pwrd is " + pwrd);
					ghu.setPwrd(pwrd);
				} catch (DataFormatException e) {
					System.err.println(e.getMessage());
				}

			
				return ghu;
			}
		}
//		return null;
		throw new UserNotFoundException("\nUser " + name + " not found in system.");
	}

	public void addUser(String newUser, String pwrd) throws InvalidUserException {	
		
		System.out.println("addUser entered, currentUser : " + currentUser);
		
		/* Only the 'admin' user can make this call */
		if(currentUser == null) {
			print("CurrentUser is null");
			throw new InvalidUserException("No current user set, try logging in first.");
		}
		
		if(!currentUser.equals(adminUser)) {
			print("Invalid user for this op");
			throw new InvalidUserException("Only the user " + adminUser + " can call 'addUser()'" ); 
		}
		
			/* Make sure a user by this name doesn't already exist */
			/* If so provide the details, else add them */
		
		/* Check each user to see if this one already exists */
		for(int i=1; i <= userCount; i++) {
			String user = syspref.get("User_" + String.valueOf(i), "NONE");
			System.out.println("User #" + String.valueOf(i) + " is " + user);
			
			if(user == newUser) {
				System.out.println("User " + newUser + " already exists!!");
				return;
			}
		}

		/* Add the new user */
		syspref.put("User_" + String.valueOf(000 + (userCount)), newUser);
		syspref.put("User_" + String.valueOf(000 + (userCount)) + "_pwrd", pwrd);
		
		/* Bump the user count and save it */
		syspref.putInt("GHA_USER_COUNT", ++userCount);

		System.out.println("Current list of users :");
		for(int i=0; i < userCount; i++) {
			String user = syspref.get("User_" + String.valueOf(i), "NONE");
			System.out.println("User_" + String.valueOf(i) + " is " + user);
		}

		try {
			syspref.flush();
		} catch(Exception e) {
			System.err.println("Preferences flush() exception : " + e.getMessage());
		}
	}
	
	public void removeUser(String newUser ) throws InvalidUserException {	
		/* Only the 'admin' user can make this call */
		if(currentUser == null)
			throw new InvalidUserException("No current user set, try logging in first.");
		
		if(!currentUser.equals(adminUser))
			throw new InvalidUserException("Only the user " + adminUser + " can call " + 
											this.getClass().getEnclosingMethod().getName());
	}
	
	public void listUsers() {	
		/* Make sure a user by this name doesn't already exist */
		/* If so provide the details, else add them */
	
		/* Check each user to see if this one already exists */
		for(int i=0; i < userCount; i++) {
			String user = syspref.get("User_" + String.valueOf(i), "NONE");
			String pwrd = syspref.get("User_" + String.valueOf(i) + "_pwrd", "NONE");
			System.out.println("User #" + String.valueOf(i) + " is " + user + ", pwrd :" + pwrd);
		}
	}
	
	/* This one is bascially  used for the junit testing */
	public void login(String user, String pwrd) 
			throws FailedLoginException, UserNotFoundException{
		
		print("Entering Golf Config login().");
		
			/* Validate the user */
			GolfHdcpUser ghu;
			try {
				ghu = getUser(user);
				ghu.printUserInfo();
			
				if(!ghu.getPwrd().equals(pwrd)) {
					print("GHU password did not match.");
					throw new FailedLoginException("Login for " + user + " failed due to incorrect password.");
				}
				
				currentUser = user;
				
				print("\n\nWelcome " + user + "!!");
				
				return;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				print("Hit user not found exception");
				/* Rethrow the user not found exception so callers are aware */
				throw new UserNotFoundException(e.getMessage());			
			}
			
	}

	/* This version will query the caller for the login credentials. */
	public void login() throws FailedLoginException, UserNotFoundException {
		
		/* Ask for the user and password, allow 3 tries for possible fat finger */
		for(int i=0; i<3; i++)  {
			print("Username : ");
			String user = in.nextLine();
			print("Password : ");
			String pwrd = in.nextLine();
			
			/* Validate the user */
			GolfHdcpUser ghu;
			try {
				ghu = getUser(user);
				ghu.printUserInfo();
			
				if(!ghu.getPwrd().equals(pwrd)) {
					print("GHU password did not match.");
					continue;
				}
				
				currentUser = user;
				
				print("\n\nWelcome " + user + "!!");
				
				return;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				/* Rethrow the user not found exception so callers are aware */
				throw new UserNotFoundException(e.getMessage());			
			}
			
		}
		
		throw new FailedLoginException("Login failed after 3 attempts.");
	}
	
	private void print(String s) {
		System.out.println(s);
	}

}

