package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.StoremanDao;
import cn.edu.buaa.leochrist.model.Storeman;
import cn.edu.buaa.leochrist.service.StoremanManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class StoremanManagerImpl extends GenericManagerImpl<Storeman, Integer>
		implements StoremanManager {

	StoremanDao storemanDao;

	public StoremanManagerImpl(StoremanDao storemanDao) {
		super(storemanDao);
		this.storemanDao = storemanDao;
	}

}