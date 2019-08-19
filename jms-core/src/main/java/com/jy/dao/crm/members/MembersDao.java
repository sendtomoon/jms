package com.jy.dao.crm.members;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.crm.members.Members;
@JYBatis
public interface MembersDao extends BaseDao<Members> {
	/**
	 * 修改密码
	 * @param o
	 */
	void updatePwd(Members o);
	/**
	 * 验证唯一
	 * @param o
	 */
	int findMobile(Members o);
}
