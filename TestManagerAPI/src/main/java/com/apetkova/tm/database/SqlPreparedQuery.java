package com.apetkova.tm.database;

public final class SqlPreparedQuery {

	// queries for app_schema.user table
	public static final String SQL_CHECK_EXISTING_USERNAME = "SELECT COUNT(*) FROM app_schema.user WHERE username = ?";

	public static final String SQL_GET_PASS_BY_USRNAME = "SELECT password FROM app_schema.user WHERE username = ?";

	public static final String SQL_SELECT_USER = "SELECT * FROM app_schema.user WHERE username = ?";

	public static final String SQL_CHECK_EXISTING_EMAIL = "SELECT COUNT(*) FROM app_schema.user WHERE email = ?";

	public static final String SQL_GET_USER_ID = "SELECT id FROM app_schema.user WHERE username = ?";

	public static final String SQL_INSERT_NEW_USER = "INSERT INTO app_schema.user(username, password, email, weight, height, age,sex, lang)"
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String SQL_GET_USER_PROJECTS = "select name from app_schema.project where id in ("
			+ "select project_id from app_schema.user_project where user_id in ("
			+ "select id from app_schema.user where username = ?)"
			+ "and role in ('admin', 'regular'))";

	public static final String SQL_GET_PROJECT_BY_ID = "select id, name from app_schema.project where id = ?;";

	private SqlPreparedQuery() {
	};
}
