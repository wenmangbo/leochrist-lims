package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.TeamDao;
import cn.edu.buaa.leochrist.model.Team;
import cn.edu.buaa.leochrist.service.TeamManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class TeamManagerImpl extends GenericManagerImpl<Team, Integer>
		implements TeamManager {

	TeamDao teamDao;

	public TeamManagerImpl(TeamDao teamDao) {
		super(teamDao);
		this.teamDao = teamDao;
	}

}