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

	// queries for app_schema.project table
	public static final String SQL_GET_USER_PROJECTS = "select id, name from app_schema.project where id in ("
			+ "select project_id from app_schema.user_project where user_id in ("
			+ "select id from app_schema.user where username = ?)"
			+ "and role in (?))";

	public static final String SQL_GET_NON_USER_PROJECTS = "select project.id, name, role, timestamp "
			+ "from app_schema.project left join app_schema.user_project on id = project_id "
			+ "left join app_schema.user u on user_id = u.id "
			+ "where "
			+ "(username = ? and role not in ('admin', 'regular')) or (username <> ? or username is null)";

	public static final String SQL_GET_PROJECT_BY_ID = "select id, name from app_schema.project where id = ?;";

	public static final String SQL_INSERT_INTO_USER_PROJECT = "INSERT INTO app_schema.user_project(user_id, project_id, role) "
			+ "VALUES (?, ?, ?)";

	// queries for app_schema.suite table
	public static final String SQL_GET_PROJECT_SUITES = "select id, name, created from app_schema.suite where project_id = ?";

	// queries for app_schema.suite table
	public static final String SQL_GET_SUITE_TEST_COUNT = "select count(1) from app_schema.testcase where suite_id = ?";

	public static final String SQL_GET_TEST_CASES = "select id, name, descr, type, automated from app_schema.testcase where suite_id = ?";

	public static final String SQL_GET_LAST_SUITE_RUN = "select test_id, timestamp, result from app_schema.test_run"
			+ " where id in (select id from app_schema.test_run where test_id in (%s) group by id order by timestamp desc limit 1)";

	public static final String SQL_INSERT_TEST = "INSERT INTO app_schema.testcase("
			+ "name, suite_id, descr, automated, type) "
			+ "VALUES (?, ?, ?, ?, ?)";

	private SqlPreparedQuery() {
	};
}
