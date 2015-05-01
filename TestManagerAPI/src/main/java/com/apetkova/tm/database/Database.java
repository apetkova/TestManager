package com.apetkova.tm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Database {

	private static final String DRIVER_NAME = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String DB_USERNAME = "postgres";
	private static final String DB_PASSWORD = "apetkova";

	Connection connection = null;

	public Connection getConnection() {
		if (connection == null) {
			connect();
		}
		return connection;
	}

	static Logger log = Logger.getLogger("tm.database");

	public Database() {
	}

	public void connect() {
		try {
			// Load the JDBC driver
			Class.forName(DRIVER_NAME);
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME,
					DB_PASSWORD);
			connection.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			// Could not find the database driver
			// log.log(null, "error", e);
		} catch (SQLException e) {
			// Could not connect to the database
		}
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// UserDao ud = new UserDao();
		// User user = ud.loadUser("admin");
		// ProjectDao pd = new ProjectDao();
		// System.out.println(pd.getNonUserProjects("admin"));
	}
}
