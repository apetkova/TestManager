package com.apetkova.tm.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public String getProjects(@PathParam("username") String username)
			throws JsonParseException, JsonMappingException, IOException {
		projectDao = new ProjectDao();
		return projectDao.getUserProjects(username).toString();
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
}