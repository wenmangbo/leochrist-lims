package cn.edu.buaa.leochrist.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "project")
@Inheritance(strategy = InheritanceType.JOINED)
public class Project implements Serializable {

	private Integer id;
	
	private String name;

	private Boolean isClassified;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "is_classified", nullable = false)
	public Boolean getIsClassified() {
		return isClassified;
	}

	public void setIsClassified(Boolean isClassified) {
		this.isClassified = isClassified;
	}

}
