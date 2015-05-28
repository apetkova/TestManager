package com.apetkova.tm.dao;

import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_ALL_SUITE_RESULTS;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_PROJECT_SUITES;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_INSERT_SUITE;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.apetkova.tm.database.Database;
import com.apetkova.tm.details.EntityDetails;
import com.apetkova.tm.details.ResultDetails;
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
				details.created = EntityDetails.toStringDate(rs
						.getTimestamp("created"));
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

	public boolean addNewSuite(String name, int projectId) {
		db.connect();
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_INSERT_SUITE);
			ps.setString(1, name);
			ps.setInt(2, projectId);
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		}
	}

	public List<ResultDetails> getSuitesResults(int suiteId) {
		db.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_GET_ALL_SUITE_RESULTS);
			ps.setInt(1, suiteId);
			rs = ps.executeQuery();
			List<ResultDetails> result = new ArrayList<ResultDetails>();
			while (rs.next()) {
				ResultDetails details = new ResultDetails();
				details.runId = rs.getInt("id");
				details.testName = rs.getString("name");
				details.timestamp = EntityDetails.toStringDate(rs
						.getTimestamp("timestamp"));
				details.result = rs.getString("result");
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
