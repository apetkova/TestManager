package com.apetkova.tm.base;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the testcase database table.
 * 
 */
@Entity
@Table(name="testcase")
@NamedQuery(name="TestCase.findAll", query="SELECT t FROM TestCase t")
public class TestCase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Boolean automated;

	@Column(length=200)
	private String descr;

	@Column(nullable=false, length=60)
	private String name;

	@Column(name="suite_id", nullable=false)
	private Integer suiteId;

	@Column(nullable=false, length=8)
	private String type;

	public TestCase() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAutomated() {
		return this.automated;
	}

	public void setAutomated(Boolean automated) {
		this.automated = automated;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSuiteId() {
		return this.suiteId;
	}

	public void setSuiteId(Integer suiteId) {
		this.suiteId = suiteId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}