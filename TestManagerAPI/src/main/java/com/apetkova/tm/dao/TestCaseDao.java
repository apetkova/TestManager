package com.apetkova.tm.dao;

import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_LAST_SUITE_RUN;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_SUITE_TEST_COUNT;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_GET_TEST_CASES;
import static com.apetkova.tm.database.SqlPreparedQuery.SQL_INSERT_TEST;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.apetkova.tm.base.TestCase;
import com.apetkova.tm.database.Database;
import com.apetkova.tm.details.TestDetails;

public class TestCaseDao {

	Database db;
	@PersistenceUnit(unitName = "TestManagerAPI")
	private EntityManager em;

	public TestCaseDao() {
		db = new Database();

	}

	public List<TestDetails> getSuiteTestCases(int suiteId) {
		db.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_GET_TEST_CASES);
			ps.setInt(1, suiteId);
			rs = ps.executeQuery();
			List<TestDetails> result = new ArrayList<TestDetails>();
			while (rs.next()) {
				TestDetails details = new TestDetails();
				details.id = rs.getInt("id");
				details.name = rs.getString("name");
				details.descr = rs.getString("descr");
				details.type = rs.getString("type");
				details.automated = rs.getString("automated");
				details.suiteId = suiteId;
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

	public List<TestDetails> getLastTestRun(List<TestDetails> details) {
		db.connect();
		Statement s = null;
		ResultSet rs = null;
		List<TestDetails> result = new ArrayList<TestDetails>();
		String query = String.format(SQL_GET_LAST_SUITE_RUN,
				TestDetails.idsToArray(details));
		try {
			s = db.getConnection().createStatement();
			rs = s.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("test_id");
				TestDetails detail = TestDetails.getObjectByTestId(id, details);
				detail.lastRun = rs.getTimestamp("timestamp");
				detail.lastResult = rs.getString("result");
				result.add(detail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				rs.close();
				s.close();
				for (TestDetails detail : details) {
					if (TestDetails.getObjectByTestId(detail.id, result) == null) {
						result.add(detail);
					}
				}
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e);
			}
		}
		return null;
	}

	public int getSuiteTestCount(int suite_id) {
		db.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_GET_SUITE_TEST_COUNT);
			ps.setInt(1, suite_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
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
		return 0;
	}

	public boolean addNewTest(TestCase testCase) {
		db.connect();
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement(SQL_INSERT_TEST);
			ps.setString(1, testCase.getName());
			ps.setInt(2, testCase.getSuiteId());
			ps.setString(3, testCase.getDescr());
			ps.setBoolean(4, testCase.getAutomated());
			ps.setString(5, testCase.getType());
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
}
