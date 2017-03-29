package net.internetworkconsulting.accounting.entities;

import java.io.Serializable;
import java.sql.Date;

public class ImportSage50 implements Serializable{
	public static String DB_SERVER = "DB Server";
	private String sDbServer;
	public void setDbServer(String value) { sDbServer = value; } 
	public String getDbServer() { return sDbServer; }

	public static String DB_DATABASE = "DB Database";
	private String sDbDatabase;
	public void setDbDatabase(String value) { sDbDatabase = value; } 
	public String getDbDatabase() { return sDbDatabase; }

	public static String DB_USER = "DB User";
	private String sDbUser;
	public void setDbUser(String value) { sDbUser = value; } 
	public String getDbUser() { return sDbUser; }

	public static String DB_PASSWORD = "DB Password";
	private String sDbPassword;
	public void setDbPassword(String value) { sDbPassword = value; } 
	public String getDbPassword() { return sDbPassword; }

	public static String START_DATE = "Start Date";
	private Date dtStartDate;
	public void setStartDate(Date value) { dtStartDate = value; } 
	public Date getStartDate() { return dtStartDate; }

	public static String END_DATE = "End Date";
	private Date dtEndDate;
	public void setEndDate(Date value) { dtEndDate = value; } 
	public Date getEndDate() { return dtEndDate; }

	public void testDatabase() {  }
}
