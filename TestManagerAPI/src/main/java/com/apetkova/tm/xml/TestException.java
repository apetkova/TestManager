package com.apetkova.tm.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exception")
public class TestException {

	String className;
	String stackTrace;

	public String getStackTrace() {
		return stackTrace;
	}

	@XmlElement(name = "short-stacktrace")
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getClassName() {
		return className;
	}

	@XmlAttribute(name="class")
	public void setClassName(String className) {
		this.className = className;
	}
	
	
}
