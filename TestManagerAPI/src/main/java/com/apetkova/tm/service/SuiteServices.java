package com.apetkova.tm.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.apetkova.tm.dao.SuiteDao;
import com.apetkova.tm.dao.TestCaseDao;
import com.apetkova.tm.details.SuiteDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/suite")
public class SuiteServices {

	private SuiteDao suiteDao;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getsuites/{project_id}")
	public Response getProjects(@PathParam("project_id") int projectId)
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
}