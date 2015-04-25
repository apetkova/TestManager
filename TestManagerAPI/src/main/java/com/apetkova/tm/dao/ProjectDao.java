package com.apetkova.tm.dao;

import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_PROJECT_BY_ID;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_USER_PROJECTS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.apetkova.tm.database.Database;
import com.apetkova.tm.details.ProjectDetails;

public class ProjectDao {

	// public boolean insertProject(Project project){
	// db.connect();
	// PasswordEncryptor pe = new PasswordEncryptor();
	// try{
	// PreparedStatement ps =
	// db.getConnection().prepareStatement(SQL_INSERT_NEW_USER);
	// ps.setString(1, user.getUsername());
	// ps.setString(2, pe.encryptPasswordOnReg(user.getPassword()));
	// ps.setString(3, user.getEmail());
	// ps.executeUpdate();
	// ps.close();
	// }catch(Exception e){
	// e.printStackTrace();
	// System.out.println(e);
	// return false;
	// }
	// return true;
	// }

	Database db;

	public ProjectDao() {
		db = new Database();

	}

	public ArrayList<String> getUserProjects(String username) {
		db.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_GET_USER_PROJECTS);
			ps.setString(1, username);
			rs = ps.executeQuery();
			ArrayList<String> result = new ArrayList<String>();
			while (rs.next()) {
				result.add(rs.getString("name"));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		}
		return null;
	}

	public ProjectDetails getProjectById(Integer id) {
		ProjectDetails result = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		db.connect();
		try {
			ps = db.getConnection().prepareStatement(SQL_GET_PROJECT_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				result = new ProjectDetails();
				result.id = rs.getInt("id");
				result.name = rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
