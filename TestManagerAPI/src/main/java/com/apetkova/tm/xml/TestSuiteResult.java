package com.apetkova.tm.xml;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.apetkova.tm.details.EntityDetails;

@XmlRootElement(name = "suite")
public class TestSuiteResult extends EntityDetails {

	List<TestResult> tests;
	String name;

	public List<TestResult> getTests() {
		return tests;
	}

	@XmlElement(name = "test")
	public void setTests(List<TestResult> tests) {
		this.tests = tests;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TestSuiteResult.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(this, sw);

			return sw.toString();
		} catch (JAXBException jbe) {
			jbe.printStackTrace();
		}

		return null;
	}

}
