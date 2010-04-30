package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.PersonDao;
import cn.edu.buaa.leochrist.model.Person;
import cn.edu.buaa.leochrist.service.PersonManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class PersonManagerImpl extends GenericManagerImpl<Person, Integer>
		implements PersonManager {

	PersonDao personDao;

	public PersonManagerImpl(PersonDao personDao) {
		super(personDao);
		this.personDao = personDao;
	}

}
