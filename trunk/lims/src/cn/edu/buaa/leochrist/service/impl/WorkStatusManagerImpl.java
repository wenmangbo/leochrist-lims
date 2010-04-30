package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.WorkStatusDao;
import cn.edu.buaa.leochrist.model.WorkStatus;
import cn.edu.buaa.leochrist.service.WorkStatusManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class WorkStatusManagerImpl extends
		GenericManagerImpl<WorkStatus, Integer> implements WorkStatusManager {

	WorkStatusDao workStatusDao;

	public WorkStatusManagerImpl(WorkStatusDao workStatusDao) {
		super(workStatusDao);
		this.workStatusDao = workStatusDao;
	}

}