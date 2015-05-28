package com.apetkova.tm.xml;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class TestXML {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try {
		    File file = new File("/home/pavel/workspace_razhobit/testmanager/TestManagerAPI/src/test/resources/result.xml");
		    JAXBContext jaxbContext = JAXBContext.newInstance(TestNGResult.class);
		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    TestNGResult result = (TestNGResult) jaxbUnmarshaller.unmarshal(file);
		    
		    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		    StringWriter writer = new StringWriter();
		    jaxbMarshaller.marshal(result, writer);
		    System.out.print(writer.toString());
		 } catch (JAXBException e) {
		    e.printStackTrace();
		 }

	}

}
