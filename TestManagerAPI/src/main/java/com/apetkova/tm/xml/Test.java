package com.apetkova.tm.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test")
public class Test {
	
	String name;
	List<TestClass> classes;
	
	public String getName() {
		return name;
	}
	
	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}
	
	public List<TestClass> getClasses() {
		return classes;
	}
	
	@XmlElement(name = "class")
	@XmlElementWrapper(name = "classes")
	public void setClasses(List<TestClass> classes) {
		this.classes = classes;
	}
	
	
}
