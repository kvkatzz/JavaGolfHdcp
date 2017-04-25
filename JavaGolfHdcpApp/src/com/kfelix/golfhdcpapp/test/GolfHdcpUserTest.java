package com.kfelix.golfhdcpapp.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.kfelix.golfhdcpapp.GolfHdcpUser;
import com.kfelix.golfhdcpapp.exceptions.DataFormatException;

/**
 * @author Kevin J. Felix
 *
 */
@RunWith(Parameterized.class)
public class GolfHdcpUserTest {

	private enum Type {GOOD_USER, BAD_USER};
	private static final Object [][] UserData = { {Type.GOOD_USER, "Kev" },
												  {Type.GOOD_USER, "Tom" },
												  {Type.BAD_USER, "Kev13+"},
												  {Type.BAD_USER, "Tom!"} };
	private GolfHdcpUser ghu;
	
//	@Parameter
	private Type type;
//	@Parameter
    private String user;
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    
	public GolfHdcpUserTest(Type t, String u) {
		this.type = t;
		this.user = u;
		this.ghu = new GolfHdcpUser();
	}


	@Parameters
    public static Collection<Object []> data() {
//        Object[][] data = new Object[][] { {Type.GOOD_USER, "Kev" },
//        									{Type.GOOD_USER, "Tom" },
//        									{Type.BAD_USER, "Kev13"} };
//	  	
        return Arrays.asList(UserData);
    }
    
    @Test
    public  void testSetUser() {
    	Assume.assumeTrue(type == Type.GOOD_USER);
    	System.out.println("Entering testSetUser with type/user : " + type + "/" + user);
    	try {
			ghu.setUser(user);
		} catch (DataFormatException e) {
			System.err.println(e.getMessage());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	assertEquals("User does not match!!", user, ghu.getName());
    }
    
    
    @Test
    public  void testSetUserExceptionRule() throws DataFormatException {
    	Assume.assumeTrue(type == Type.BAD_USER);
    	System.out.println("Entering testSetUserExceptionRule with type/user : " + type + "/" + user);
    	exception.expect(DataFormatException.class);
    	exception.expectMessage("User name can only contain characters or numbers");    		
    	ghu.setUser(user);
    }

    @Test
    public  void testSetUserException() {
    	Assume.assumeTrue(type == Type.BAD_USER);
    	System.out.println("Entering testSetUserException with type/user : " + type + "/" + user);
    	
    	try {
			ghu.setUser(user);
			fail("Exception did not occur!");
		} catch (DataFormatException e) {
			System.err.println(e.getMessage());			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test 
    public void testSetPwrd() {
//    	fail("Not implemeneted yet!");
    }
    
//    @Test
//    public void testAddId() {
//    	
//    }
 
}
