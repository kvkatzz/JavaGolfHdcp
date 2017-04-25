package com.kfelix.golfhdcpapp.test;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyTestRunner {
  public static void main(String[] args) {
	  // Run the following test suites :
	  Result result = JUnitCore.runClasses(GhaAllTests.class);
//    Result result = JUnitCore.runClasses(GolfHdcpConfigTest.class,GolfHdcpCliTest.class);
    
	  for (Failure failure : result.getFailures()) {
		  System.out.println(failure.toString());
	  }
  }
}


