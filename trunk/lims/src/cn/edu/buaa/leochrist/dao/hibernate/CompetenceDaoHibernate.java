package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.CompetenceDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Competence;

public class CompetenceDaoHibernate extends
		GenericDaoHibernate<Competence, Integer> implements CompetenceDao {

	public CompetenceDaoHibernate() {
		super(Competence.class);
	}

}
