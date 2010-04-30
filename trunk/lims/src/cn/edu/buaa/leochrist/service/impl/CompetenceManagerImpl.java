package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.CompetenceDao;
import cn.edu.buaa.leochrist.model.Competence;
import cn.edu.buaa.leochrist.service.CompetenceManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class CompetenceManagerImpl extends
		GenericManagerImpl<Competence, Integer> implements CompetenceManager {

	CompetenceDao competenceDao;

	public CompetenceManagerImpl(CompetenceDao competenceDao) {
		super(competenceDao);
		this.competenceDao = competenceDao;
	}

}