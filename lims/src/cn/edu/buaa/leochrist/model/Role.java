package cn.edu.buaa.leochrist.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Role implements Serializable {

	private Integer id;

	private String roleName;

	private String descirption;

	private List<Competence> competences;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescirption() {
		return descirption;
	}

	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}

	public List<Competence> getCompetences() {
		return competences;
	}

	public void setCompetences(List<Competence> competences) {
		this.competences = competences;
	}

}
