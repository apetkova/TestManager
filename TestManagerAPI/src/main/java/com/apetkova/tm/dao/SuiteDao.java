package com.apetkova.tm.dao;

import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_PROJECT_SUITES;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.apetkova.tm.database.Database;
import com.apetkova.tm.details.SuiteDetails;

public class SuiteDao {

	Database db;
	@PersistenceUnit(unitName = "TestManagerAPI")
	private EntityManager em;

	public SuiteDao() {
		db = new Database();

	}

	public List<SuiteDetails> getProjectSuites(int project_id) {
		db.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_GET_PROJECT_SUITES);
			ps.setInt(1, project_id);
			rs = ps.executeQuery();
			List<SuiteDetails> result = new ArrayList<SuiteDetails>();
			while (rs.next()) {
				SuiteDetails details = new SuiteDetails();
				details.id = rs.getInt("id");
				details.name = rs.getString("name");
				details.created = rs.getTimestamp("created");
				details.projectId = project_id;
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
}
