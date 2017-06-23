package com.tube.rest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.*;
import javax.sql.*;

public class SQLdbtube {

	private static Connection connection;

	public static Connection initDBConnection() throws Exception {

		if (connection != null)
			return connection;

		try {
			if (connection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/training?user=root&password=root");
			}

		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;

	}

	public static Connection getConnection() {
		return connection;
	}
}