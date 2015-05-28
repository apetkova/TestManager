package com.apetkova.tm.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test-method")
public class TestMethodResult {

	String name;
	String status;
	String durationMs;
	String startedAt;
	TestException exception;

	public TestException getException() {
		return exception;
	}

	@XmlElement(name = "exception")
	public void setException(TestException exception) {
		this.exception = exception;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	@XmlAttribute(name = "status")
	public void setStatus(String status) {
		this.status = status;
	}

	public String getDurationMs() {
		return durationMs;
	}

	@XmlAttribute(name = "duration-ms")
	public void setDurationMs(String durationMs) {
		this.durationMs = durationMs;
	}

	public String getStartedAt() {
		return startedAt;
	}

	@XmlAttribute(name = "started-at")
	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}
}
