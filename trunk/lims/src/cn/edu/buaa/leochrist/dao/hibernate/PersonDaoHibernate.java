package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.PersonDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Person;

public class PersonDaoHibernate extends GenericDaoHibernate<Person, Integer>
		implements PersonDao {

	public PersonDaoHibernate() {
		super(Person.class);
	}

}