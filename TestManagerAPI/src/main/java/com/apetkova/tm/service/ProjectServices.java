package com.apetkova.tm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.apetkova.tm.dao.ProjectDao;
import com.apetkova.tm.details.ProjectDetails;
import com.apetkova.tm.details.UserAccessDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/project")
public class ProjectServices {

	private ProjectDao projectDao;

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getprojects/{username}/{role}")
	public Response getProjects(@PathParam("username") String username,
			@PathParam("role") String role) throws JsonParseException,
			JsonMappingException, IOException {
		projectDao = new ProjectDao();
		JSONArray jsonArr = new JSONArray();
		ArrayList<ProjectDetails> projects = projectDao.getUserProjects(
				username, role);
		for (ProjectDetails pd : projects) {
			JSONObject json = pd.toJson();
			jsonArr.add(json);
		}
		return Response.ok(jsonArr.toJSONString()).build();
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

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getnewprojects/{username}")
	public Response getNewProjects(@PathParam("username") String username)
			throws JsonParseException, JsonMappingException, IOException {
		projectDao = new ProjectDao();
		List<String[]> projects = projectDao.getNonUserProjects(username);
		JSONArray array = new JSONArray();
		for (String[] project : projects) {
			JSONObject json = new JSONObject();
			json.put("id", project[0]);
			json.put("project", project[1]);
			json.put("role", project[2]);
			json.put("timestamp", project[3]);
			array.add(json);
		}

		return Response.ok(array.toJSONString(), MediaType.TEXT_PLAIN).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/requestaccess")
	public Response requestAccess(UserAccessDetails userAccess) {
		projectDao = new ProjectDao();

		return Response.ok(
				Boolean.toString(projectDao.requestAccess(userAccess.username,
						userAccess.id)), MediaType.TEXT_PLAIN).build();
	}

}