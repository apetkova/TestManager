package com.apetkova.tm.dao;

import static com.apetkova.tm.database.SqlPreparedQuery.SQL_CHECK_EXISTING_EMAIL;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_CHECK_EXISTING_USERNAME;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_PASS_BY_USRNAME;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_INSERT_NEW_USER;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_SELECT_USER;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.apetkova.tm.base.User;
import com.apetkova.tm.database.Database;
import com.apetkova.tm.details.UserDetails;
import com.apetkova.tm.utils.PasswordEncryptor;

public class UserDao {

	Database db;

	public UserDao() {
		db = new Database();

	}

	public boolean insertUser(User user) {
		db.connect();
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(
					SQL_INSERT_NEW_USER);
			ps.setString(1, user.getUsername());
			ps.setString(2,
					PasswordEncryptor.encryptPasswordOnReg(user.getPassword()));
			ps.setString(3, user.getEmail());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
		return true;
	}

	public boolean usernameExists(String username) {

		boolean result = false;
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(
					SQL_CHECK_EXISTING_USERNAME);
			ps.setString(1, username);
			ResultSet rset = ps.executeQuery();

			while (rset.next()) {
				if (rset.getString(1).equals("0"))
					result = false;
				else
					result = true;
			}

			ps.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean emailExists(String email) {

		boolean result = false;
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(
					SQL_CHECK_EXISTING_EMAIL);
			ps.setString(1, email);
			ResultSet rset = ps.executeQuery();

			while (rset.next()) {
				if (rset.getString(1).equals("0"))
					result = false;
				else
					result = true;
			}

			ps.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public UserDetails loadUser(String username) {
		UserDetails user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_SELECT_USER);
			ps.setString(1, username);
			rs = ps.executeQuery();

			while (rs.next()) {
				user = new UserDetails();
				user.id = rs.getInt("id");
				user.username = rs.getString("username");
				user.password = rs.getString("password");
				user.email = rs.getString("email");
			}

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	// public User loadUser(String username){
	//
	// User user = new User();
	// user.setUsername(username);
	// try {
	// PreparedStatement ps =
	// db.getConnection().prepareStatement(SQL_SELECT_USER);
	// ps.setString(1, username);
	// ResultSet rset = ps.executeQuery();
	//
	// while(rset.next()){
	// user.setPassword(rset.getString("password"));
	// user.setEmail(rset.getString("email"));
	// }
	//
	// ps.close();
	// }catch (SQLException e) {
	// e.printStackTrace();
	// return user;
	// }
	// return user;
	// }

	public boolean passwordMatch(String username, String enteredPass) {

		String password = null;
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(
					SQL_GET_PASS_BY_USRNAME);
			ps.setString(1, username);
			ResultSet rset = ps.executeQuery();
			while (rset.next()) {
				password = rset.getString(1);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return PasswordEncryptor.isPasswordCorrect(enteredPass, password);
	}
}
