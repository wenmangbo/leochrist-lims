package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.DissertationDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Dissertation;

public class DissertationDaoHibernate extends
		GenericDaoHibernate<Dissertation, Integer> implements DissertationDao {

	public DissertationDaoHibernate() {
		super(Dissertation.class);
	}

}