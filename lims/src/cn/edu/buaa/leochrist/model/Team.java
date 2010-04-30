package cn.edu.buaa.leochrist.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Team implements Serializable {

	private Integer id;

	private Member teamLeader;

	private Project project;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Member getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(Member teamLeader) {
		this.teamLeader = teamLeader;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
