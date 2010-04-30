package cn.edu.buaa.leochrist.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Storeman extends Person implements Serializable {

	private List<Lab> labs;

	public List<Lab> getLabs() {
		return labs;
	}

	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}

}
