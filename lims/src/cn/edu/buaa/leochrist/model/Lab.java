package cn.edu.buaa.leochrist.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "lab")
public class Lab implements Serializable {

	private Integer id;

	private String Name;

	private Team team;

	private Person person;

	private Integer deviceNumber;

	private String address;

	private Boolean isEngage;

	private List<Device> devices;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name = "device_number")
	public Integer getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(Integer deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "is_engage")
	public Boolean getIsEngage() {
		return isEngage;
	}

	public void setIsEngage(Boolean isEngage) {
		this.isEngage = isEngage;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lab")
	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

}
