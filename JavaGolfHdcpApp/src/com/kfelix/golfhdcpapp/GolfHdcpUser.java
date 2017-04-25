package com.kfelix.golfhdcpapp;


import com.kfelix.golfhdcpapp.exceptions.DataFormatException;
import java.io.PrintStream;

public class GolfHdcpUser
{
  private String name;
  private String pwrd;
  private int id;
  
  public GolfHdcpUser(String n)
  {
    this.name = n;
  }
  
  public GolfHdcpUser()
  {
    this.name = null;
  }
  
  public void setUser(String u)
    throws DataFormatException
  {
    if (!u.matches("[a-zA-Z0-9]*")) {
      throw new DataFormatException("User name can only contain characters or numbers");
    }
    this.name = u;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setPwrd(String p)
    throws DataFormatException
  {
    if (!p.matches("[a-zA-Z0-9]*")) {
      throw new DataFormatException("User name can only contain characters or numbers");
    }
    this.pwrd = p;
  }
  
  public String getPwrd()
  {
    return this.pwrd;
  }
  
  public void setId(int n)
  {
    this.id = n;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void printUserInfo()
  {
    System.out.println("User name : " + this.name);
    System.out.println("User ID : " + this.id);
    System.out.println("User password : " + this.pwrd);
  }
}
