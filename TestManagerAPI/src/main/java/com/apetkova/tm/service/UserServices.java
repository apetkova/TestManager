package com.apetkova.tm.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.apetkova.tm.base.User;
import com.apetkova.tm.dao.UserDao;
import com.apetkova.tm.details.UserDetails;
import com.apetkova.tm.jason.UserJason;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/user")
public class UserServices {

	private UserDao userDao;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(User user, @Context HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {
		userDao = new UserDao();
		if (userDao.usernameExists(user.getUsername())) {
			if (userDao.passwordMatch(user.getUsername(), user.getPassword())) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user.getUsername());
				return "true";
			}
		}
		return "false";
	}

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean signup(String jason) throws JsonParseException,
			JsonMappingException, IOException {
		User user = UserJason.signupJasonToUser(jason);
		userDao = new UserDao();
		return userDao.insertUser(user);
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
			// session.setAttribute("user", "admin");
			// response = Response.ok().build();
			response = Response.status(Status.UNAUTHORIZED).build();
		}

		return response;
	}

}
