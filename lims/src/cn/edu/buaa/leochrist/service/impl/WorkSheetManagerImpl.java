package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.WorkSheetDao;
import cn.edu.buaa.leochrist.model.WorkSheet;
import cn.edu.buaa.leochrist.service.WorkSheetManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class WorkSheetManagerImpl extends
		GenericManagerImpl<WorkSheet, Integer> implements WorkSheetManager {

	WorkSheetDao workSheetDao;

	public WorkSheetManagerImpl(WorkSheetDao workSheetDao) {
		super(workSheetDao);
		this.workSheetDao = workSheetDao;
	}

}