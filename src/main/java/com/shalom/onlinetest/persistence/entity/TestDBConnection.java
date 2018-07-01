package com.shalom.onlinetest.persistence.entity;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:mysql://localhost:3306/online_test?useSSL=false";
		String user = "springstudent";
		String pass = "mysqlP@ssw0rd";
		
		try {
			System.out.println("Connecting to database: " + jdbcUrl);
			Connection myConn = 
					DriverManager.getConnection(jdbcUrl,user,pass);
			System.out.println("Connection successful!!!");
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}