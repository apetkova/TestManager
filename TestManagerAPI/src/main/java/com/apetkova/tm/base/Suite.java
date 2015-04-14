package com.apetkova.tm.base;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the suite database table.
 * 
 */
@Entity
@Table(name="suite")
@NamedQuery(name="Suite.findAll", query="SELECT s FROM Suite s")
public class Suite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=30)
	private String name;

	@Column(name="project_id", nullable=false)
	private Integer projectId;

	public Suite() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

}