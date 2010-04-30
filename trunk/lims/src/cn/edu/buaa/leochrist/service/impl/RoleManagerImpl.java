package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.RoleDao;
import cn.edu.buaa.leochrist.model.Role;
import cn.edu.buaa.leochrist.service.RoleManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class RoleManagerImpl extends GenericManagerImpl<Role, Integer>
		implements RoleManager {

	RoleDao roleDao;

	public RoleManagerImpl(RoleDao roleDao) {
		super(roleDao);
		this.roleDao = roleDao;
	}

}