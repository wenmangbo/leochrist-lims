package cn.edu.buaa.leochrist.service.impl;

import cn.edu.buaa.leochrist.dao.AwardDao;
import cn.edu.buaa.leochrist.model.Award;
import cn.edu.buaa.leochrist.service.AwardManager;
import cn.edu.buaa.leochrist.service.generic.GenericManagerImpl;

public class AwardManagerImpl extends
		GenericManagerImpl<Award, Integer> implements AwardManager {

	AwardDao awardDao;

	public AwardManagerImpl(AwardDao awardDao) {
		super(awardDao);
		this.awardDao = awardDao;
	}

}