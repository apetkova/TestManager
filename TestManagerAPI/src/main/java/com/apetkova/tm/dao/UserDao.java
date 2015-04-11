package com.apetkova.tm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.apetkova.tm.base.User;
import com.apetkova.tm.database.Database;
import com.apetkova.tm.database.IPreparedStatementsSQL;
import com.apetkova.tm.utils.PasswordEncryptor;

public class UserDao implements IPreparedStatementsSQL{

	Database db;
	public UserDao(){
		db = new Database();
		
	}
	
	public boolean insertUser(User user){
		db.connect();
		PasswordEncryptor pe = new PasswordEncryptor();
		try{
			PreparedStatement ps = db.getConnection().prepareStatement(SQL_INSERT_NEW_USER);
			ps.setString(1, user.getUsername());
			ps.setString(2, pe.encryptPasswordOnReg(user.getPassword()));
			ps.setString(3, user.getEmail());
			ps.executeUpdate();	
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public boolean usernameExists(String username){

		boolean result = false;
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(SQL_CHECK_EXISTING_USERNAME);
			ps.setString(1, username);
			ResultSet rset = ps.executeQuery();

			while (rset.next()){
				if (rset.getString(1).equals("0"))
					result = false;
				else result = true;
			}

			ps.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean emailExists(String email){

		boolean result = false;
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(SQL_CHECK_EXISTING_EMAIL);
			ps.setString(1, email);
			ResultSet rset = ps.executeQuery();

			while (rset.next()){
				if (rset.getString(1).equals("0"))
					result = false;
				else result = true;
			}

			ps.close();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public User loadUser(String username){

		User user = new User();
		user.setUsername(username);
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(SQL_SELECT_USER);
			ps.setString(1, username);
			ResultSet rset = ps.executeQuery();

			while(rset.next()){
				user.setPassword(rset.getString("password"));
				user.setEmail(rset.getString("email"));
			}

			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			return user;
		}
		return user;
	}
	
	public boolean passwordMatch(String username, String enteredPass){

		PasswordEncryptor encryptor = new PasswordEncryptor();
		String password = null;
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(SQL_GET_PASS_BY_USRNAME);
			ps.setString(1, username);
			ResultSet rset = ps.executeQuery();
			while (rset.next()){
				password = rset.getString(1);
			}
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return password.equals(encryptor.encryptPasswordOnLogin(enteredPass, password));
	}
}
