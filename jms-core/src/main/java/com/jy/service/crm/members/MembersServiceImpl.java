package com.jy.service.crm.members;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.dao.crm.members.MembersDao;
import com.jy.entity.crm.members.Members;
import com.jy.service.base.BaseServiceImp;
@Service("membersService")
public class MembersServiceImpl extends BaseServiceImp<Members> implements MembersService {
	@Autowired
	private MembersDao dao;
	@Override
	public void updatePwd(Members o) {
		dao.updatePwd(o);
	}
	@Override
	public int findMobile(Members o) {
		return dao.findMobile(o);
	}

}
