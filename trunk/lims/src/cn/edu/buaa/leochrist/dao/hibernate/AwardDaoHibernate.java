package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.AwardDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Award;

public class AwardDaoHibernate extends
		GenericDaoHibernate<Award, Integer> implements AwardDao {

	public AwardDaoHibernate() {
		super(Award.class);
	}

}