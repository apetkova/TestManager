package com.apetkova.tm.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.apetkova.tm.base.User;
import com.apetkova.tm.dao.UserDao;
import com.apetkova.tm.details.UserDetails;
import com.apetkova.tm.details.UserProjectDetails;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/user")
public class UserServices {

	private UserDao userDao;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(User user, @Context HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		userDao = new UserDao();
		if (userDao.usernameExists(user.getUsername())) {
			if (userDao.passwordMatch(user.getUsername(), user.getPassword())) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user.getUsername());
				return Response.ok("true").build();
			}
		}
		return Response.ok("false").build();
	}

	@GET
	@Path("/me")
	public Response getMe(@Context HttpServletRequest request) {
		Response response = null;
		userDao = new UserDao();

		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			UserDetails user = userDao.loadUser((String) session
					.getAttribute("user"));
			try {
				response = Response.ok(user.toJson(),
						MediaType.APPLICATION_JSON).build();
			} catch (JsonProcessingException jpe) {
				response = Response.serverError().build();
			}
		} else {
			response = Response.status(Status.UNAUTHORIZED).build();
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/inproject/{projectId}/{role}")
	public Response getUsersWithAccessLevel(
			@PathParam("projectId") int projectId,
			@PathParam("role") String role) throws JsonProcessingException {

		userDao = new UserDao();

		List<UserProjectDetails> details = userDao.getAwaitingAccess(projectId,
				role);
		JSONArray jsonArr = new JSONArray();
		for (UserProjectDetails upd : details) {
			JSONObject json = upd.toJson();
			jsonArr.add(json);
		}

		return Response.ok(jsonArr.toJSONString(), MediaType.APPLICATION_JSON)
				.build();
	}

}
