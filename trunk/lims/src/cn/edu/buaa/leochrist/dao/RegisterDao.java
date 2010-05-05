package cn.edu.buaa.leochrist.dao;

import cn.edu.buaa.leochrist.dao.generic.GenericDao;
import cn.edu.buaa.leochrist.model.Register;

public interface RegisterDao extends GenericDao<Register, Integer> {
	
	public boolean isExist(String username);
	public Register findByUsername(String username);

}
