package cn.edu.buaa.leochrist.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Project implements Serializable {

	private Integer id;

	private Boolean isClassified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsClassified() {
		return isClassified;
	}

	public void setIsClassified(Boolean isClassified) {
		this.isClassified = isClassified;
	}

}
