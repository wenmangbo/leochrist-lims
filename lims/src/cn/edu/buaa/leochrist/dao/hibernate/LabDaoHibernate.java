package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.LabDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Lab;

public class LabDaoHibernate extends GenericDaoHibernate<Lab, Integer>
		implements LabDao {

	public LabDaoHibernate() {
		super(Lab.class);
	}

}