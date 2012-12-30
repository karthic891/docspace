/**
 * 
 */
package com.docspace.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

/**
 * @author karthic
 *
 */
public class DBConnector {
	
	Logger logger = Logger.getLogger(DBConnector.class.getName());
	Connection connection;
	
	public DBConnector() {
		logger.info("Establishing DB Connection.");
		String connectionURL = "jdbc:mysql://localhost:3306/";
		String driver = "com.mysql.jdbc.Driver";
		String dbName = "docspace";
		String userName = "root";
		String password = "root";
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(connectionURL + dbName, userName, password);
			System.out.println("connected - only once!!");
			logger.info("Connection to DB established");
		} catch (Exception e) {
			logger.severe("Error establishing connection with the DB.");
			e.printStackTrace();
		}
		
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
}
