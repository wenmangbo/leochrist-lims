package cn.edu.buaa.leochrist.dao.hibernate;

import cn.edu.buaa.leochrist.dao.MemberDao;
import cn.edu.buaa.leochrist.dao.generic.GenericDaoHibernate;
import cn.edu.buaa.leochrist.model.Member;

public class MemberDaoHibernate extends GenericDaoHibernate<Member, Integer>
		implements MemberDao {

	public MemberDaoHibernate() {
		super(Member.class);
	}

}