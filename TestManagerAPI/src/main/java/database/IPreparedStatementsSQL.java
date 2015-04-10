package database;

public interface IPreparedStatementsSQL {
	
	//queries for USERS table
	static final String SQL_CHECK_EXISTING_USERNAME = "SELECT COUNT(*) FROM users WHERE username = ?";
	
	static final String SQL_GET_PASS_BY_USRNAME = "SELECT password FROM users WHERE username = ?";
	
	static final String SQL_SELECT_USER = "SELECT * FROM app_schema.user WHERE username = ?";
	
	static final String SQL_CHECK_EXISTING_EMAIL = "SELECT COUNT(*) FROM users WHERE email = ?";
	
	static final String SQL_GET_USER_ID = "SELECT id FROM users WHERE username = ?";
	
	static final String SQL_GET_USER_LANG = "SELECT lang FROM users WHERE username = ?";
	
	static final String SQL_INSERT_NEW_USER = "INSERT INTO users(username, password, email, weight, height, age,sex, lang)" +
			  "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
}	
	
	
