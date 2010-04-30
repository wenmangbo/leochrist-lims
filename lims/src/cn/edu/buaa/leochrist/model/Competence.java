package cn.edu.buaa.leochrist.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Competence implements Serializable {

	private Integer id;

	private String competenceName;

	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompetenceName() {
		return competenceName;
	}

	public void setCompetenceName(String competenceName) {
		this.competenceName = competenceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
