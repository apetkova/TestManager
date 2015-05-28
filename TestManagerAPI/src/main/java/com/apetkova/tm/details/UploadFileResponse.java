package com.apetkova.tm.details;

import java.util.List;

public class UploadFileResponse extends EntityDetails {
	public String suiteName;
	public List<TestDetails> tests;

	// testname => type; includename => testname; description = "TestNG";
	// automated = true

}