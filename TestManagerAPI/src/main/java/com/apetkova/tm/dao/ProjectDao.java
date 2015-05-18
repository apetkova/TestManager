package com.apetkova.tm.dao;

import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_NON_USER_PROJECTS;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_PROJECT_BY_ID;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_USER_PROJECTS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import com.apetkova.tm.base.Project;
import com.apetkova.tm.database.Database;
import com.apetkova.tm.database.SqlPreparedQuery;
import com.apetkova.tm.details.ProjectDetails;
import com.apetkova.tm.utils.Role;

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
	@PersistenceUnit(unitName = "TestManagerAPI")
	private EntityManager em;

	public ProjectDao() {
		db = new Database();

	}

	public ArrayList<ProjectDetails> getUserProjects(String username,
			String role) {
		db.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = SQL_GET_USER_PROJECTS;
		if (role.equals("all")) {
			sql.replaceAll("?", "?, ?");
			sql.replaceFirst("?, ?", "?");
		}
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setString(1, username);
			if (role.equals("all")) {
				ps.setString(2, "admin");
				ps.setString(3, "regular");
			} else {
				ps.setString(2, role);
			}
			rs = ps.executeQuery();
			ArrayList<ProjectDetails> result = new ArrayList<ProjectDetails>();
			while (rs.next()) {
				ProjectDetails details = new ProjectDetails();
				details.id = rs.getInt("id");
				details.name = rs.getString("name");
				result.add(details);
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

	public ArrayList<String[]> getNonUserProjects(String username) {
		db.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_GET_NON_USER_PROJECTS);
			ps.setString(1, username);
			ps.setString(2, username);
			rs = ps.executeQuery();
			ArrayList<String[]> result = new ArrayList<String[]>();
			while (rs.next()) {
				String[] row = new String[4];
				row[0] = rs.getString("id");
				row[1] = rs.getString("name");
				row[2] = rs.getString("role");
				row[3] = rs.getString("timestamp");
				result.add(row);
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

	public List<Project> getAllProjects() {
		TypedQuery<Project> query = em.createNamedQuery("Project.findAll",
				Project.class);
		return query.getResultList();
	}

	public boolean requestAccess(String username, int id) {
		UserDao ud = new UserDao();
		int user_id = ud.loadUser(username).id;
		PreparedStatement ps = null;

		db.connect();
		try {
			ps = db.getConnection().prepareStatement(
					SqlPreparedQuery.SQL_INSERT_INTO_USER_PROJECT);
			ps.setInt(1, user_id);
			ps.setInt(2, id);
			ps.setString(3, Role.PENDING.name().toLowerCase());
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
