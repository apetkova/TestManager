package com.apetkova.tm.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import com.apetkova.tm.base.User;
import com.apetkova.tm.dao.UserDao;
import com.apetkova.tm.jason.UserJason;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Path("/user")
public class UserServices {

	private UserDao userDao;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(User user) throws JsonParseException, JsonMappingException, IOException{
		userDao = new UserDao();
		if (userDao.usernameExists(user.getUsername())) {
			if (userDao.passwordMatch(user.getUsername(), user.getPassword())){
				return "true";
			}
		}
		return "false";
	}
	
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean signup(String jason) throws JsonParseException, JsonMappingException, IOException{
		User user = UserJason.signupJasonToUser(jason);
		userDao = new UserDao();
		return userDao.insertUser(user);
	}
	
	
}
