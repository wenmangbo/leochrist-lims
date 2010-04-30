package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.ProjectDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Project;

public class ProjectDaoHibernate extends GenericDaoHibernate<Project, Integer>
		implements ProjectDao {

	public ProjectDaoHibernate() {
		super(Project.class);
	}

}