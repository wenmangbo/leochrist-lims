package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.ProductionDao;
import cn.edu.buaa.leochrist.model.Production;
import cn.edu.buaa.leochrist.service.ProductionManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class ProductionManagerImpl extends
		GenericManagerImpl<Production, Integer> implements ProductionManager {

	ProductionDao productionDao;

	public ProductionManagerImpl(ProductionDao productionDao) {
		super(productionDao);
		this.productionDao = productionDao;
	}

}