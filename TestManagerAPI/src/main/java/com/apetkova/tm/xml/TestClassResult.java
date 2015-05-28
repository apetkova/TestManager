package com.apetkova.tm.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class TestClassResult {

	String name;
	List<TestMethodResult> methods;

	public List<TestMethodResult> getMethods() {
		return methods;
	}

	@XmlElement(name = "test-method")
	public void setMethods(List<TestMethodResult> methods) {
		this.methods = methods;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

}
