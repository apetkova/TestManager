package com.apetkova.tm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.apetkova.tm.dao.SuiteDao;
import com.apetkova.tm.dao.TestCaseDao;
import com.apetkova.tm.details.ResultDetails;
import com.apetkova.tm.details.SuiteDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/suite")
public class SuiteServices {

	private SuiteDao suiteDao;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getsuites/{project_id}")
	public Response getSuites(@PathParam("project_id") int projectId)
			throws JsonParseException, JsonMappingException, IOException {
		suiteDao = new SuiteDao();
		JSONArray jsonArr = new JSONArray();
		TestCaseDao tcDao = new TestCaseDao();
		List<SuiteDetails> suites = suiteDao.getProjectSuites(projectId);
		for (SuiteDetails suite : suites) {
			suite.testCount = tcDao.getSuiteTestCount(suite.id);
			JSONObject json = suite.toJson();
			jsonArr.add(json);
		}
		return Response.ok(jsonArr.toJSONString()).build();
	}

	@GET
	@Path("/getruns/{suite_id}")
	public Response getRuns(@PathParam("suite_id") int suiteId)
			throws JsonParseException, JsonMappingException, IOException {
		suiteDao = new SuiteDao();
		Map<Integer, List<ResultDetails>> runs = ResultDetails
				.getTestRuns(suiteDao.getSuitesResults(suiteId));
		String result = ResultDetails.arrayToJsonString(runs);

		return Response.ok(result).build();
	}

	@POST
	@Path("/addnew")
	public Response addSuite(SuiteDetails suite,
			@Context HttpServletRequest request) {
		suiteDao = new SuiteDao();
		Boolean result = suiteDao.addNewSuite(suite.name, suite.projectId);

		return Response.ok(result.toString()).build();
	}
}