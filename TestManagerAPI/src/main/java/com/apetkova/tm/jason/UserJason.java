package com.apetkova.tm.jason;

import java.io.IOException;

import com.apetkova.tm.base.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserJason {

	private static ObjectMapper mapper = new ObjectMapper();

	public static User loginJasonToUser(String jason) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(jason, User.class);
	}
	
	public static User signupJasonToUser(String jason) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(jason, User.class);
	}
	
}
