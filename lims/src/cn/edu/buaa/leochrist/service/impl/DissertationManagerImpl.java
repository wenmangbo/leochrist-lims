package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.DissertationDao;
import cn.edu.buaa.leochrist.model.Dissertation;
import cn.edu.buaa.leochrist.service.DissertationManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class DissertationManagerImpl extends
		GenericManagerImpl<Dissertation, Integer> implements
		DissertationManager {

	DissertationDao dissertationDao;

	public DissertationManagerImpl(DissertationDao dissertationDao) {
		super(dissertationDao);
		this.dissertationDao = dissertationDao;
	}

}