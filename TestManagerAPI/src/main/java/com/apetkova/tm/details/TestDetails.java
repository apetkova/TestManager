package com.apetkova.tm.details;

import java.util.List;

public class TestDetails extends EntityDetails {
	public int id;
	public String name;
	public int suiteId;
	public String descr;
	public String type;
	public String automated;
	public String lastRun;
	public String lastResult;

	public static String idsToArray(List<TestDetails> details) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (TestDetails detail : details) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(detail.id);
			first = false;
		}
		return sb.toString();
	}

	public static TestDetails getObjectByTestId(int testId,
			List<TestDetails> details) {
		for (TestDetails detail : details) {
			if (detail.id == testId) {
				return detail;
			}
		}
		return null;
	}
}
