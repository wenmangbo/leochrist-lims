package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.DegreeDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Degree;

public class DegreeDaoHibernate extends GenericDaoHibernate<Degree, Integer>
		implements DegreeDao {

	public DegreeDaoHibernate() {
		super(Degree.class);
	}

}