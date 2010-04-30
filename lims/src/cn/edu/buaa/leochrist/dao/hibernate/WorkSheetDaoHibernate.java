package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.WorkSheetDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.WorkSheet;

public class WorkSheetDaoHibernate extends
		GenericDaoHibernate<WorkSheet, Integer> implements WorkSheetDao {

	public WorkSheetDaoHibernate() {
		super(WorkSheet.class);
	}

}