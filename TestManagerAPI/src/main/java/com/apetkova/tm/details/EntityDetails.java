package com.apetkova.tm.details;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityDetails {

	public String toJsonString() throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(this);
	}

	public JSONObject toJson() throws JsonProcessingException {
		return (JSONObject) JSONValue.parse(toJsonString());
	}

}
