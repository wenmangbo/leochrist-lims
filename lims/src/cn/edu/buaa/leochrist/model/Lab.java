package cn.edu.buaa.leochrist.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Lab implements Serializable {

	private Integer id;

	private Team team;

	private Person storeman;

	private Integer deviceNumber;

	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Person getStoreman() {
		return storeman;
	}

	public void setStoreman(Person storeman) {
		this.storeman = storeman;
	}

	public Integer getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(Integer deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
