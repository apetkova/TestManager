package com.apetkova.tm.service;

import java.io.IOException;
import java.io.InputStream;
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

import com.apetkova.tm.utils.FileUtils;
import com.apetkova.tm.xml.TestSuite;
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

		try {
			return Response.status(200).entity(suite.toJsonString()).build();
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			return Response.noContent().build();
		}

	}

}
