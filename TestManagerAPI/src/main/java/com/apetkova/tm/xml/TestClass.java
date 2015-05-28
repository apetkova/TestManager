package com.apetkova.tm.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class TestClass {

	String name;
	List<TestMethod> methods;

	public List<TestMethod> getMethods() {
		return methods;
	}

	@XmlElement(name = "include")
	@XmlElementWrapper(name = "methods")
	public void setMethods(List<TestMethod> methods) {
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
