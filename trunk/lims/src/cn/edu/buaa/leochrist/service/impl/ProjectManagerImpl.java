package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.ProjectDao;
import cn.edu.buaa.leochrist.model.Project;
import cn.edu.buaa.leochrist.service.ProjectManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class ProjectManagerImpl extends GenericManagerImpl<Project, Integer>
		implements ProjectManager {

	ProjectDao projectDao;

	public ProjectManagerImpl(ProjectDao projectDao) {
		super(projectDao);
		this.projectDao = projectDao;
	}

}