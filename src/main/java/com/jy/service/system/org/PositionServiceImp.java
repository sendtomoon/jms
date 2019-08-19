package com.jy.service.system.org;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.mybatis.Page;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.dao.system.org.PositionDao;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.AccountPosition;
import com.jy.entity.system.org.Position;
import com.jy.service.base.BaseServiceImp;

@Service("PositionService")
public class PositionServiceImp extends BaseServiceImp<Position> implements PositionService{

	@Override
	public List<ZNodes> getOrgAndPositionTree() {	
		return ((PositionDao)baseDao).getOrgAndPositionTree();
	}

	@Override
	public List<ZNodes> getPreOrgTree() {
		return ((PositionDao)baseDao).getPreOrgTree();
	}

	@Override
	public void saveAccountPosition(String posId,String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] chk =ids.split(",");
			List<AccountPosition> aps=new ArrayList<AccountPosition>();
			for(String id:chk){
				AccountPosition ap=new AccountPosition(id,posId);
				aps.add(ap);
			}				
			((PositionDao)baseDao).insertAccountPosition(aps);
		}
	}

	@Override
	public void deleteAccPosByAccId(String accountId) {
		if(StringUtils.isNotBlank(accountId)){
			((PositionDao)baseDao).deleteAccPosByAccId(accountId);	
		}
	}

	@Override
	public Page<Account> findArrangeAccByPage(Position o,Page<Account> page) {
		page.setResults(((PositionDao)baseDao).findArrangeAccByPage(o,page));
		return page;
	}

	@Override
	public Page<Account> findPosByPage(Position o, Page<Account> page) {
		page.setResults(((PositionDao)baseDao).findPosByPage(o,page));
		return page;
	}

	@Override@Transactional
	public void deletePos(Position o) {
		PositionDao dao=(PositionDao)baseDao;
		dao.delete(o);
		dao.deleteAccPosByPosId(o.getId());
	}

}
