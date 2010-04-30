package cn.edu.buaa.leochrist.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Degree implements Serializable {

	private Integer id;

	private String degreeName;

	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
