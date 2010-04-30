package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.DegreeDao;
import cn.edu.buaa.leochrist.model.Degree;
import cn.edu.buaa.leochrist.service.DegreeManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class DegreeManagerImpl extends GenericManagerImpl<Degree, Integer>
		implements DegreeManager {

	DegreeDao degreeDao;

	public DegreeManagerImpl(DegreeDao degreeDao) {
		super(degreeDao);
		this.degreeDao = degreeDao;
	}

}