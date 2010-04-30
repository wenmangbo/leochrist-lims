package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.TeamDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Team;

public class TeamDaoHibernate extends GenericDaoHibernate<Team, Integer>
		implements TeamDao {

	public TeamDaoHibernate() {
		super(Team.class);
	}

}