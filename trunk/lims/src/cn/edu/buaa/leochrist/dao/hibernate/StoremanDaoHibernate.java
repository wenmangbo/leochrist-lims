package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.StoremanDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Storeman;

public class StoremanDaoHibernate extends
		GenericDaoHibernate<Storeman, Integer> implements StoremanDao {

	public StoremanDaoHibernate() {
		super(Storeman.class);
	}

}