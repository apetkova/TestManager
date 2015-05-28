package com.apetkova.tm.details;

import java.util.List;

public class UploadResultFileResponse extends EntityDetails {
	public String suiteName;
	public List<ResultDetails> results;

	// testname => type; includename => testname; description = "TestNG";
	// automated = true

}