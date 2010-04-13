package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.RegisterDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Register;

public class RegisterDaoHibernate extends
		GenericDaoHibernate<Register, Integer> implements RegisterDao {

	public RegisterDaoHibernate() {
		super(Register.class);
	}

}
