package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;



public class Database implements IPreparedStatementsSQL {

	private static final String DRIVER_NAME = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String DB_USERNAME = "postgres";
	private static final String DB_PASSWORD = "apetkova";


	Connection connection = null;

	public Connection getConnection() {
		return connection;
	}

	static Logger log = Logger.getLogger("tm.database");

	public Database(){
	}

	public void connect(){
		try {
			// Load the JDBC driver
			Class.forName(DRIVER_NAME);
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			connection.setAutoCommit(true);
		} catch (ClassNotFoundException e) {
			// Could not find the database driver
			//			log.log(null, "error", e);
		} catch (SQLException e) {
			// Could not connect to the database
		}
	}

	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		Database db = new Database();
//		db.connect();
//		User user = db.loadUser("admin");
//		System.out.println(user.getUsername());
//		db.closeConnection();
	}
}
