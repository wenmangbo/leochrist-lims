package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.ResultDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Result;

public class ResultDaoHibernate extends
		GenericDaoHibernate<Result, Integer> implements ResultDao {

	public ResultDaoHibernate() {
		super(Result.class);
	}

}