package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.ClassifiedProjectDao;
import cn.edu.buaa.leochrist.model.ClassifiedProject;
import cn.edu.buaa.leochrist.service.ClassifiedProjectManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class ClassifiedProjectManagerImpl extends
		GenericManagerImpl<ClassifiedProject, Integer> implements
		ClassifiedProjectManager {

	ClassifiedProjectDao classifiedProjectDao;

	public ClassifiedProjectManagerImpl(
			ClassifiedProjectDao classifiedProjectDao) {
		super(classifiedProjectDao);
		this.classifiedProjectDao = classifiedProjectDao;
	}

}