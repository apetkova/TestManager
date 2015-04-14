package com.apetkova.tm.database;

public interface IPreparedStatementsSQL {
	
	//queries for app_schema.user table
	static final String SQL_CHECK_EXISTING_USERNAME = "SELECT COUNT(*) FROM app_schema.user WHERE username = ?";
	
	static final String SQL_GET_PASS_BY_USRNAME = "SELECT password FROM app_schema.user WHERE username = ?";
	
	static final String SQL_SELECT_USER = "SELECT * FROM app_schema.user WHERE username = ?";
	
	static final String SQL_CHECK_EXISTING_EMAIL = "SELECT COUNT(*) FROM app_schema.user WHERE email = ?";
	
	static final String SQL_GET_USER_ID = "SELECT id FROM app_schema.user WHERE username = ?";
	
	static final String SQL_INSERT_NEW_USER = "INSERT INTO app_schema.user(username, password, email, weight, height, age,sex, lang)" +
			  "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
}	
	
	
