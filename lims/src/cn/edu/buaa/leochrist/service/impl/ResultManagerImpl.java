package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.ResultDao;
import cn.edu.buaa.leochrist.model.Result;
import cn.edu.buaa.leochrist.service.ResultManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class ResultManagerImpl extends
		GenericManagerImpl<Result, Integer> implements ResultManager {

	ResultDao resultDao;

	public ResultManagerImpl(ResultDao resultDao) {
		super(resultDao);
		this.resultDao = resultDao;
	}

}