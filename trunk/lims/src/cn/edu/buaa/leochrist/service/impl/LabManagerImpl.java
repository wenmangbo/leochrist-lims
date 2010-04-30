package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.LabDao;
import cn.edu.buaa.leochrist.model.Lab;
import cn.edu.buaa.leochrist.service.LabManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class LabManagerImpl extends GenericManagerImpl<Lab, Integer> implements
		LabManager {

	LabDao labDao;

	public LabManagerImpl(LabDao labDao) {
		super(labDao);
		this.labDao = labDao;
	}

}