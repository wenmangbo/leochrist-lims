package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.NormalProjectDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.NormalProject;

public class NormalProjectDaoHibernate extends
		GenericDaoHibernate<NormalProject, Integer> implements NormalProjectDao {

	public NormalProjectDaoHibernate() {
		super(NormalProject.class);
	}

}