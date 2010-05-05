package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.RegisterDao;
import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.service.RegisterManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class RegisterManagerImpl extends GenericManagerImpl<Register, Integer>
		implements RegisterManager {

	RegisterDao registerDao;

	public RegisterManagerImpl(RegisterDao registerDao) {
		super(registerDao);
		this.registerDao = registerDao;
	}

	public boolean isExist(String username) {

		return registerDao.isExist(username);
	}

	public Register findByUsername(String username) {
		return registerDao.findByUsername(username);
	}
}