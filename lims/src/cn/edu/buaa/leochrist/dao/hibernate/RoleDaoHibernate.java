package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.RoleDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Role;

public class RoleDaoHibernate extends GenericDaoHibernate<Role, Integer>
		implements RoleDao {

	public RoleDaoHibernate() {
		super(Role.class);
	}

}