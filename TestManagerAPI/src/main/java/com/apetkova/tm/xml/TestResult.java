package com.apetkova.tm.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test")
public class TestResult {
	
	String name;
	List<TestClassResult> classes;
	
	public String getName() {
		return name;
	}
	
	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}
	
	public List<TestClassResult> getClasses() {
		return classes;
	}
	
	@XmlElement(name = "class")
	public void setClasses(List<TestClassResult> classes) {
		this.classes = classes;
	}
	
	
}
