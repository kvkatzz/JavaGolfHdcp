package com.kfelix.golfhdcpapp.test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.FixMethodOrder;
import org.junit.Assume;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GolfHdcpCliTest {

	
	@Test
	public final void testAddGolfer() {
		System.out.println("Entering testAddGolfer");
		//fail("Not yet implemented");
		System.out.println("Exiting testAddGolfer");
	}

//	@Ignore("Not implemented") 
	@Test
	public final void testAddCourse() {
		// This will cause the test to be ignored, so 'fail' will not be executed
		Assume.assumeTrue(false);
		fail("Not yet implemented");
	}

	@Ignore("Not implemented") 
	@Test
	public final void testAddRound() {
		fail("Not yet implemented");
	}

}
