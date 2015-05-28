package com.apetkova.tm.details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ResultDetails extends EntityDetails {
	public int runId;
	public String testName;
	public String timestamp;
	public String result;
	public String duration;
	public String exceptionName;
	public String stackTrace;

	public static Map<Integer, List<ResultDetails>> getTestRuns(
			List<ResultDetails> results) {
		Map<Integer, List<ResultDetails>> runs = new HashMap<Integer, List<ResultDetails>>();
		for (ResultDetails result : results) {
			if (runs.keySet().contains(result.runId)) {
				runs.get(result.runId).add(result);
			} else {
				List<ResultDetails> list = new ArrayList<ResultDetails>();
				list.add(result);
				runs.put(result.runId, list);
			}
		}
		return runs;
	}

	public static String arrayToJsonString(
			Map<Integer, List<ResultDetails>> runs)
			throws JsonProcessingException {
		StringBuilder result = new StringBuilder();
		result.append("[{");
		boolean first = true;
		for (Integer runId : runs.keySet()) {
			if (!first) {
				result.append(",");
			}
			result.append("\"runId\":" + runId);
			result.append(", \"tests\":[");
			boolean firstDetail = true;
			for (ResultDetails details : runs.get(runId)) {
				if (!firstDetail) {
					result.append(",");
				}
				result.append(details.toJsonString());
				firstDetail = false;
			}
			result.append("]");
			first = false;
		}
		result.append("}]");
		return result.toString();
	}

}
