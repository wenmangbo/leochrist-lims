package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.MemberDao;
import cn.edu.buaa.leochrist.model.Member;
import cn.edu.buaa.leochrist.service.MemberManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class MemberManagerImpl extends GenericManagerImpl<Member, Integer>
		implements MemberManager {

	MemberDao memberDao;

	public MemberManagerImpl(MemberDao memberDao) {
		super(memberDao);
		this.memberDao = memberDao;
	}

}