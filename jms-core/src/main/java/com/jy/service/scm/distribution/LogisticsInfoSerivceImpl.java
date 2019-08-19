package com.jy.service.scm.distribution;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.distribution.LogisticsInfoDao;
import com.jy.dao.scm.purorder.PurOutStorageDao;
import com.jy.entity.scm.distribution.LogisticsInfo;
import com.jy.entity.scm.purorder.PurOSOrder;
import com.jy.service.base.BaseServiceImp;

@Service("LogisticsInfoSerivce")
public class LogisticsInfoSerivceImpl extends BaseServiceImp<LogisticsInfo> implements LogisticsInfoSerivce {

	@Autowired
	private LogisticsInfoDao logisticsInfoDao;
	
	@Autowired
	private PurOutStorageDao purOutStorageDao;
	
	@Override
	public LogisticsInfo edits(String id) {
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		LogisticsInfo info=new LogisticsInfo();
		info.setBussnessId(id);
		info.setType(orgId);
		List<LogisticsInfo> logisticsInfos=logisticsInfoDao.find(info);
		if (logisticsInfos.size()>0) {
			return logisticsInfos.get(0);
		}
		List<LogisticsInfo> infos=logisticsInfoDao.findByOutbound(id,orgId);
		return infos.get(0);
	}

	@Override
	@Transactional
	public void addOrUpdate(LogisticsInfo info,String type,String outId) {
		if(type!=null && type.length()>0){
			logisticsInfoDao.delete(info);
		}else{
			if(info.getId().isEmpty()){
				info.setId(UuidUtil.get32UUID());
				info.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				info.setStauts(Constant.MATERIALIN_STATUES_01);
				logisticsInfoDao.insert(info);
				PurOSOrder order=new PurOSOrder();
				order.setOutBoundNo(info.getOutboundNo());
				List<PurOSOrder> list=purOutStorageDao.find(order);
				if(list.size()>0){
					list.get(0).setStatus(Constant.PURENTERY_STATUS_06);
					purOutStorageDao.updatePurOSOrder(list.get(0));
				}
				
			}else{
				info.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				logisticsInfoDao.update(info);
			}
		}
	}


}
