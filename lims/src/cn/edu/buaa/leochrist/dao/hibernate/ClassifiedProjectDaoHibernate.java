package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.ClassifiedProjectDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.ClassifiedProject;

public class ClassifiedProjectDaoHibernate extends
		GenericDaoHibernate<ClassifiedProject, Integer> implements
		ClassifiedProjectDao {

	public ClassifiedProjectDaoHibernate() {
		super(ClassifiedProject.class);
	}

}
