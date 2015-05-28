package com.apetkova.tm.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.apetkova.tm.details.ResultDetails;
import com.apetkova.tm.details.TestDetails;
import com.apetkova.tm.details.UploadFileResponse;
import com.apetkova.tm.details.UploadResultFileResponse;
import com.apetkova.tm.utils.FileUtils;
import com.apetkova.tm.xml.Test;
import com.apetkova.tm.xml.TestClass;
import com.apetkova.tm.xml.TestClassResult;
import com.apetkova.tm.xml.TestMethod;
import com.apetkova.tm.xml.TestMethodResult;
import com.apetkova.tm.xml.TestNGResult;
import com.apetkova.tm.xml.TestResult;
import com.apetkova.tm.xml.TestSuite;
import com.apetkova.tm.xml.TestSuiteResult;
import com.fasterxml.jackson.core.JsonProcessingException;

@Path("/file")
public class FileServices {

	private static String UPLOADED_FILE_PATH = "C:\\TM\\apache-tomcat-8.0.21\\webapps\\TestManagerAPI\\";

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(MultipartFormDataInput input) {

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		TestSuite suite = null;
		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = FileUtils.getFileName(header);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class,
						null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				// constructs upload file path
				fileName = UPLOADED_FILE_PATH + fileName;

				FileUtils.writeFile(bytes, fileName);

				suite = FileUtils.getSuiteFromFile(fileName);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		UploadFileResponse response = new UploadFileResponse();
		response.suiteName = suite.getName();
		List<TestDetails> convertedTests = new ArrayList<TestDetails>();
		Iterator<Test> testIter = suite.getTests().iterator();
		while (testIter.hasNext()) {
			Test nextTest = testIter.next();

			if (nextTest != null && nextTest.getClasses() != null) {
				Iterator<TestClass> classIter = nextTest.getClasses()
						.iterator();
				while (classIter.hasNext()) {
					TestClass nextClass = classIter.next();

					if (nextClass != null && nextClass.getMethods() != null) {
						Iterator<TestMethod> methodIter = nextClass
								.getMethods().iterator();
						while (methodIter.hasNext()) {
							TestMethod nextMethod = methodIter.next();

							TestDetails nextConvertedTest = new TestDetails();
							nextConvertedTest.name = nextMethod.getName();
							nextConvertedTest.type = nextTest.getName();
							nextConvertedTest.descr = "TestNG";
							nextConvertedTest.automated = "true";

							convertedTests.add(nextConvertedTest);
						}
					}
				}
			}

			response.tests = convertedTests;
		}

		try {
			return Response.status(200).entity(response.toJsonString()).build();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return Response.noContent().build();
		}

	}

	@POST
	@Path("/upload-results")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadResultsFile(MultipartFormDataInput input) {

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		TestNGResult results = null;
		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = FileUtils.getFileName(header);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class,
						null);

				byte[] bytes = IOUtils.toByteArray(inputStream);

				// constructs upload file path
				fileName = UPLOADED_FILE_PATH + fileName;

				FileUtils.writeFile(bytes, fileName);

				results = FileUtils.getResultsFromFile(fileName);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		UploadResultFileResponse response = new UploadResultFileResponse();
		if (results != null && results.getSuits() != null && results.getSuits().size() > 0) {
			TestSuiteResult suiteResult = results.getSuits().get(0);
			response.suiteName = suiteResult.getName();
			
			List<ResultDetails> resultDetails = new ArrayList<ResultDetails>();
			if (suiteResult.getTests() != null && suiteResult.getTests().size() > 0) {
				Iterator<TestResult> resultIter = suiteResult.getTests().iterator();
				
				TestResult nextTestResult;
				while ( resultIter.hasNext() ) {
					nextTestResult = resultIter.next();
					
					if (nextTestResult.getClasses() != null && nextTestResult.getClasses().size() > 0) {
						Iterator<TestClassResult> classIter = nextTestResult.getClasses().iterator();
						
						TestClassResult nextClassResult;
						while ( classIter.hasNext() ) {
							nextClassResult = classIter.next();
							
							if (nextClassResult.getMethods() != null && nextClassResult.getMethods().size() > 0) {
								Iterator<TestMethodResult> methodsIter = nextClassResult.getMethods().iterator();
								
								TestMethodResult nextMethod;
								while ( methodsIter.hasNext() ) {
									nextMethod = methodsIter.next();
									
									ResultDetails nextResultDetails = new ResultDetails();
									nextResultDetails.testName = nextMethod.getName();
									nextResultDetails.duration = nextMethod.getDurationMs();
									nextResultDetails.result = nextMethod.getStatus();
									nextResultDetails.timestamp = nextMethod.getStartedAt();
									if (nextMethod.getException() != null) {
										nextResultDetails.exceptionName = nextMethod.getException().getClassName();
										nextResultDetails.stackTrace = nextMethod.getException().getStackTrace();
									}
						
									resultDetails.add(nextResultDetails);
								}
							}
						}
					}
				}
			}
			
			response.results = resultDetails;
		}
		
		try {
			return Response.status(200).entity(response.toJsonString()).build();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return Response.noContent().build();
		}

	}

}
