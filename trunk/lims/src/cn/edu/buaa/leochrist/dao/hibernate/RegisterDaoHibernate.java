package cn.edu.buaa.leochrist.dao.hibernate;

import java.util.List;

import cn.edu.buaa.leochrist.dao.RegisterDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Register;

public class RegisterDaoHibernate extends
		GenericDaoHibernate<Register, Integer> implements RegisterDao {

	public RegisterDaoHibernate() {
		super(Register.class);
	}

	@SuppressWarnings("unchecked")
	public boolean isExist(String username) {
		List<Register> registers = this.getHibernateTemplate().find(
				"from Register where username=?", username);
		if (registers != null && registers.size() != 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Register findByUsername(String username) {
		List<Register> registers = this.getHibernateTemplate().find(
				"from Register where username=?", username);
		if (registers == null || registers.size() == 0) {
			return null;
		}
		return registers.get(0);
	}
}
