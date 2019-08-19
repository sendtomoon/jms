package com.jy.service.crm.members;

import com.jy.entity.crm.members.Members;
import com.jy.service.base.BaseService;

public interface MembersService extends BaseService<Members> {
	/**
	 * 修改密码
	 * @param o
	 */
	void updatePwd(Members o);
	/**
	 * 验证唯一
	 * @param o
	 * @return
	 */
	int findMobile(Members o);
}
