package com.apetkova.tm.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "testng-results")
public class TestNGResult {
	List<TestSuiteResult> suits;

	public List<TestSuiteResult> getSuits() {
		return suits;
	}

	@XmlElement(name = "suite")
	public void setSuits(List<TestSuiteResult> suits) {
		this.suits = suits;
	}
	
	
}
