package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunner {

	public static void main(String[] args) throws SQLException {
		
	Connection connection =	DatabaseManager.getConnection();
	
	System.out.println(connection);
		
		

	}

}
