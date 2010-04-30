package cn.edu.buaa.leochrist.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "member")
@PrimaryKeyJoinColumn(name = "id")
public class Member extends Person implements Serializable {

	private Team team;

	private Person teamLeader;

	private Project project;

	private WorkSheet workSheet;

	private Boolean isTeamLeader;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Person getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(Person teamLeader) {
		this.teamLeader = teamLeader;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public WorkSheet getWorkSheet() {
		return workSheet;
	}

	public void setWorkSheet(WorkSheet workSheet) {
		this.workSheet = workSheet;
	}

	public Boolean getIsTeamLeader() {
		return isTeamLeader;
	}

	public void setIsTeamLeader(Boolean isTeamLeader) {
		this.isTeamLeader = isTeamLeader;
	}

}
