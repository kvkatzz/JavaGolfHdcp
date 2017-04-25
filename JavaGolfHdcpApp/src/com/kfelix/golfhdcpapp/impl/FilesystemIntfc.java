package com.kfelix.golfhdcpapp.impl;

import java.util.Date;
import java.io.*;
import java.nio.file.Files;

import com.kfelix.golfhdcpapp.*;
import java.nio.file.StandardOpenOption;
import java.nio.charset.Charset;




public class FilesystemIntfc implements GolfHcpInterface {
	
	/* Implement the GolfHcpInterface methods using a file as a the datastore. */
	private String date;
	private String golfer;
	private String course;
	private int score;
	private GolfHdcpConfig gc;
	
	public FilesystemIntfc(GolfHdcpConfig g) {
		System.out.println("Entering GolfHdcpCli cstr.");
		gc = g;
	}
	
	// Validate that the expected files are there, create them
	// if not.
	private void validateFileStore() {
		
	}
	
	// Open the specified file for either reading or writing.
	private void openFile() {
		
	}
	
	// Sync (??) and close the specified file.
	private void closeFile() {
		
	}
	
	@Override
	public void addRound(GolfRound round) {
		date = round.getDate();
		golfer = round.getGolfer();
		course = round.getCourse();
		score = round.getScore();
		
		// DateFormat df = DateFormat.getDateInstance();
		
		// Use BufferedWriter for efficiency.
		BufferedWriter bw = null;
		Charset charset = Charset.forName("US-ASCII");

		// Create a '|' delimited string and save to file
//		String s = "|March 21, 2017|Kevin|BLissful Meadows|62|";
		String s = "|" + date + "|" + golfer + "|" + course + "|" + score + "|\n";
		
		System.out.println("Rounds data file : " + gc.getRoundsFilePath());
		
		try {
			bw = Files.newBufferedWriter(gc.getRoundsFilePath(), charset, StandardOpenOption.APPEND);
		    bw.write(s, 0, s.length());
		    bw.close();
		} catch (IOException x) {
		    System.err.format("IOException adding a round : %s%n", x);
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

	@Override
	public Golfer findGolfer(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addGolfer(Golfer golfer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCourse(GolfCourse course) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GolfCourse getCourse(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GolfRound getRound(Date date, GolfCourse course) {
		// TODO Auto-generated method stub
		return null;
	}

}
