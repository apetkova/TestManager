package com.apetkova.tm.dao;

import java.sql.PreparedStatement;

import com.apetkova.tm.base.Project;
import com.apetkova.tm.base.User;
import com.apetkova.tm.utils.PasswordEncryptor;

public class ProjectDao {

//	public boolean insertProject(Project project){
//		db.connect();
//		PasswordEncryptor pe = new PasswordEncryptor();
//		try{
//			PreparedStatement ps = db.getConnection().prepareStatement(SQL_INSERT_NEW_USER);
//			ps.setString(1, user.getUsername());
//			ps.setString(2, pe.encryptPasswordOnReg(user.getPassword()));
//			ps.setString(3, user.getEmail());
//			ps.executeUpdate();	
//			ps.close();
//		}catch(Exception e){
//			e.printStackTrace();
//			System.out.println(e);
//			return false;
//		}
//		return true;
//	}
}
