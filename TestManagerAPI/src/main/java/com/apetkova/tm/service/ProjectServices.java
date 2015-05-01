package com.apetkova.tm.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.apetkova.tm.dao.ProjectDao;
import com.apetkova.tm.details.ProjectDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/project")
public class ProjectServices {

	private ProjectDao projectDao;

	@GET
	@Path("/getprojects/{username}")
	public Response getProjects(@PathParam("username") String username)
			throws JsonParseException, JsonMappingException, IOException {
		projectDao = new ProjectDao();
		
		return Response.ok(projectDao.getUserProjects(username).toString()).build();
	}

	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getProject(@PathParam("id") Integer id) {
		projectDao = new ProjectDao();
		ProjectDetails result = projectDao.getProjectById(id);

		try {
			return Response.ok(result.toJson(), MediaType.APPLICATION_JSON)
					.build();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/getnewprojects/{username}")
	public Response getNewProjects(@PathParam("username") String username)
			throws JsonParseException, JsonMappingException, IOException {
		projectDao = new ProjectDao();
		List<String[]> projects = projectDao.getNonUserProjects(username);
		JSONArray array = new JSONArray();
		for (String[] project : projects) {
			JSONObject json = new JSONObject();
			json.put("project", project[0]);
			json.put("role", project[1]);
			json.put("timestamp", project[2]);
			array.add(json);
		}
		return Response.ok(array.toJSONString()).build();
	}

}