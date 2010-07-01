package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.ProductionDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Production;

public class ProductionDaoHibernate extends
		GenericDaoHibernate<Production, Integer> implements ProductionDao {

	public ProductionDaoHibernate() {
		super(Production.class);
	}

}