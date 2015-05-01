package com.apetkova.tm.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "suite")
public class TestSuite {

	List<Test> tests;
	String name;
	String verbose;

	public List<Test> getTests() {
		return tests;
	}

	@XmlElement(name = "test")
	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public String getVerbose() {
		return verbose;
	}

	@XmlAttribute(name = "verbose")
	public void setVerbose(String verbose) {
		this.verbose = verbose;
	}
	
	
	
}
