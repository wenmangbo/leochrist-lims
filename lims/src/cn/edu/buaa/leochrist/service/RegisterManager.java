package cn.edu.buaa.leochrist.service;

import cn.edu.buaa.leochrist.model.Register;
import cn.edu.buaa.leochrist.service.generic.GenericManager;

public interface RegisterManager extends GenericManager<Register, Integer> {

	public boolean isExist(String username);

	public Register findByUsername(String username);
}