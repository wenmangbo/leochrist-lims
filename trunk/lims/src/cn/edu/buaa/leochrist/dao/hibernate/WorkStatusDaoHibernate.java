package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.WorkStatusDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.WorkStatus;

public class WorkStatusDaoHibernate extends
		GenericDaoHibernate<WorkStatus, Integer> implements WorkStatusDao {

	public WorkStatusDaoHibernate() {
		super(WorkStatus.class);
	}

}