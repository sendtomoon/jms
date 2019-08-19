package com.jy.service.scm.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Org;
import com.jy.entity.scm.store.Store;
import com.jy.dao.scm.store.StoreDao;
import com.jy.dao.system.org.OrgDao;
import com.jy.service.base.BaseServiceImp;

@Service("StoreService")
public class StoreServiceImp extends BaseServiceImp<Store> implements StoreService {
	
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private OrgDao orgDao;
	
	@Override
	@Transactional
	public int insertStore(Store store,String oid) throws Exception{
		int res=0;
		String code = store.getCode();
		//查询数据库是否已经存在用户名
		if(StringUtils.isNotBlank(code)&&(storeDao.findStoreRecordByCode(code)==0)){
			//根据orgid查询org对象
			Org org = storeDao.findOrgById(store.getOrgId());
			Account curUser = AccountShiroUtil.getCurrentUser();
//			String oid = UuidUtil.get32UUID();
			//组装一个新的org
			Org newOrg = new Org();
			newOrg.setId(oid);
			newOrg.setpId(store.getOrgId());
			newOrg.setName(store.getName());
			newOrg.setLongName(store.getLongName());
			newOrg.setOrgGrade(Const.ORG_GRADE_STORE);
			newOrg.setCode(store.getCode());
			newOrg.setDistcode(org.getDistcode());
			newOrg.setStatus(Const.USER_STATE_ACTIVE);
			newOrg.setCreateName(curUser.getLoginName());
			newOrg.setCreateUser(curUser.getAccountId());
			newOrg.setCreateTime(new Date());
			store.setOrgId(oid);
			store.setDistCode(org.getDistcode());
			//组织结构表新增一条
			storeDao.addOrg(newOrg);
			//插入门店信息表
			storeDao.insert(store);
			res=1;
		}
		return res;
	}

	@Override
	public void logicDelStore(Store store) throws Exception{
		storeDao.updateStoreState(store);
		storeDao.updateOrgState(store);
	}

	@Override
	public void logicDelBatchAccount(String chks) throws Exception{
		if(StringUtils.isNotBlank(chks)){
			String[] chk =chks.split(",");
			List<Store> list=new ArrayList<Store>();			
			Store store=new Store();
			store.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			store.setUpdateName(AccountShiroUtil.getCurrentUser().getLoginName());
			store.setStatus(Const.USER_STATE_INACTIVE);//失效状态
			for(String s:chk){
				String[] str = s.split("-");
				Store ac = new Store();
				ac.setId(str[0]);
				ac.setOrgId(str[1]);
				list.add(ac);
			}
			storeDao.batchUpdateStoreState(list,store);
			storeDao.batchUpdateOrgState(list,store);
		}
	}

	@Override
	@Transactional
	public void updateStoreInfo(Store store) throws Exception {
		//当status值变了时，同时修改org变状态
//		if(storeDao.countOrgState(store)==0){
//			storeDao.updateOrgState(store);
//		}
//		//如果组织结构变了，修改org表中门店记录的pid
//		if(!store.getpId().equals(store.getOrgId())){
//			storeDao.changeOrgId(store);
//		}
		Org newOrg = new Org();
		newOrg.setId(store.getOrgId());
		newOrg=orgDao.find(newOrg).get(0);
//		newOrg.setpId(store.getpId());
		newOrg.setName(store.getName());
		newOrg.setLongName(store.getLongName());
		newOrg.setOrgGrade(Const.ORG_GRADE_STORE);
		newOrg.setCode(store.getCode());
		newOrg.setDistcode(store.getDistCode());
		newOrg.setStatus(store.getStatus());
		newOrg.setUpdateTime(store.getUpdateTime());
		newOrg.setUpdateUser(store.getUpdateUser());
		orgDao.update(newOrg);
		storeDao.update(store);
	}

	


}
