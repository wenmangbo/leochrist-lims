package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.NormalProjectDao;
import cn.edu.buaa.leochrist.model.NormalProject;
import cn.edu.buaa.leochrist.service.NormalProjectManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class NormalProjectManagerImpl extends
		GenericManagerImpl<NormalProject, Integer> implements
		NormalProjectManager {

	NormalProjectDao normalProjectDao;

	public NormalProjectManagerImpl(NormalProjectDao normalProjectDao) {
		super(normalProjectDao);
		this.normalProjectDao = normalProjectDao;
	}

}