package com.apetkova.tm.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.apetkova.tm.dao.TestCaseDao;
import com.apetkova.tm.details.TestDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/testcase")
public class TestCaseServices {

	private TestCaseDao testDao;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/gettests/{suite_id}")
	public Response getTests(@PathParam("suite_id") int suiteId)
			throws JsonParseException, JsonMappingException, IOException {
		testDao = new TestCaseDao();
		JSONArray jsonArr = new JSONArray();
		List<TestDetails> tests = testDao.getLastTestRun(testDao
				.getSuiteTestCases(suiteId));
		for (TestDetails test : tests) {
			JSONObject json = test.toJson();
			jsonArr.add(json);
		}
		return Response.ok(jsonArr.toJSONString()).build();
	}
}