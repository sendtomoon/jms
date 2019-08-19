package com.jy.service.scm.materialcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jy.common.excel.IExcelRowReader;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.materialcome.MaterialcomeDao;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.system.tool.SerialNumberService;

import net.sf.json.JSONArray;

@Service("materialcomeService")
public class MaterialcomeServiceImpl extends BaseServiceImp<Materialcome> implements MaterialcomeService,IExcelRowReader {

	private final static ReentrantLock updateLock = new ReentrantLock();
	
	@Autowired
	private MaterialcomeDao materialcomeDao;
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Override
	public int updateStatus(Materialcome materialcome) {
		try{
			updateLock.lock();
			materialcome.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
			return materialcomeDao.updateStatus(materialcome);
		}finally{
			updateLock.unlock();
		}
	}

	@Override
	public void getRows(int arg0, int arg1, List<String> arg2) {
		
	}

	@Override
	public Map<String, Object> delBatch(String chks) {
		Materialcome m = new Materialcome();
		m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		m.setStatus(Constant.MATERIALCOME_STATUS_9);
		Map<String, Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(chks)){
			String[] chk = chks.split(",");
//			List<Materialcome> updateList=new ArrayList<Materialcome>();
			List<Materialcome> deleteList=new ArrayList<Materialcome>();
			List<Materialcome> allList = new ArrayList<Materialcome>();
			for (String s : chk) {
				Materialcome materialcome = materialcomeDao.getMaterial(s);
				materialcome.setId(s);
				if (materialcome.getStatus().equals(Constant.MATERIALCOME_STATUS_1)||materialcome.getStatus().equals(Constant.MATERIALCOME_STATUS_2)||materialcome.getStatus().equals(Constant.MATERIALCOME_STATUS_9)) {
					map.put("result", "待审核、已审核状态不支持删除");
					return map;
				}
				if (materialcome.getStatus().equals(Constant.MATERIALCOME_STATUS_0)||materialcome.getStatus().equals(Constant.MATERIALCOME_STATUS_3)) {
					deleteList.add(materialcome);
				}
				allList.add(materialcome);
			}
//			if (!updateList.isEmpty()) {
//				materialcomeDao.updateDelflag(updateList, m);
//			}
			materialcomeDao.deleteDetail(allList);
			materialcomeDao.deleteBatch(deleteList);
		}
		return map;
	}

	@Override
	public Map<String,Object> findDetail(Materialcome materialcome) {
		Map<String,Object> map = new HashMap<String,Object>();
		Materialcome material = materialcomeDao.getMaterial(materialcome.getId());
		if (materialcome.getFlag().equals(Constant.MATERIALCOME_STATUS_1)) {
			if(material.getStatus().equals(Constant.MATERIALCOME_STATUS_1)||material.getStatus().equals(Constant.MATERIALCOME_STATUS_2)||material.getStatus().equals(Constant.MATERIALCOME_STATUS_9)){
				map.put("result", "该状态不支持修改");
				return map;
			}
		}
		if (materialcome.getFlag().equals(Constant.MATERIALCOME_STATUS_2)) {
			if(material.getStatus().equals(Constant.MATERIALCOME_STATUS_0)||material.getStatus().equals(Constant.MATERIALCOME_STATUS_2)||material.getStatus().equals(Constant.MATERIALCOME_STATUS_3)||material.getStatus().equals(Constant.MATERIALCOME_STATUS_9)){
				map.put("result", "该状态不支持审核");
				return map;
			}
		}
		map.put("list", materialcomeDao.findDetail(materialcome));
		
		return map;
	}

	@Transactional
	@Override
	public void insertMaterialcome(String data) throws Exception {
		List<Materialcome> list = JSONArray.toList(JSONArray.fromObject(data),Materialcome.class);
		String str = serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_RN+list.get(0).getFlag());
		if (!list.isEmpty()) {
			list.get(0).setId(UuidUtil.get32UUID());
			Materialcome materialcome2 = materialcomeDao.getFranchisee(list.get(0));
			if (null != materialcome2) {
				list.get(0).setSurpplyId(materialcome2.getSurpplyId());
			}
			
			Materialcome material = materialcomeDao.getOperator(list.get(0));
			if (null != material) {
				list.get(0).setOperator(material.getOperator());;
				list.get(0).setOperatorId(material.getOperatorId());
			}
			for (Materialcome materialcome : list) {
				materialcome.setNoticeNo(str);
				materialcome.setNoticeId(list.get(0).getId());
				materialcome.setDetailId(UuidUtil.get32UUID());
				materialcome.setDelFlag(Constant.MATERIALCOME_DELETETAG_1);
				materialcome.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				materialcome.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			}
			materialcomeDao.insert(list.get(0));
			materialcomeDao.insertDetail(list);
		}
	}

	
	
	@Override
	public void aduit(Materialcome materialcome) {
		String checkUser = AccountShiroUtil.getCurrentUser().getAccountId();
		materialcome.setCheckUser(checkUser);
		materialcomeDao.aduit(materialcome);
	}

	@Override
	public List<SelectData> getReceiver(String orgId) {
		return materialcomeDao.getReceiver(orgId);
	}

	@Override
	public List<Materialcome> getMaterialcomeUpload(String userId) {
		return materialcomeDao.getMaterialcomeUpload(userId);
	}

	/**
	 * 导入添加
	 */
	@Transactional
	@Override
	public int batchImport(List<Materialcome> list) {
		for (Materialcome materialcome : list) {
			Materialcome m = materialcomeDao.findPurchaseNo(materialcome.getPurchaseNo());
			if (m == null) {
				return 0;
			}
		}
		
		Double totalCount=0.0;
		Double totalWt = 0.0;
		Double sumBasicCost = 0.0;
		Double sumAddCost = 0.0;
		Double sumOtherCost = 0.0;
		
		String str = serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_RN+list.get(0).getFlag());
		Materialcome materialcome2 = new Materialcome();
		Materialcome materialcome3 = materialcomeDao.getFranchisee(list.get(0));
		if (null != materialcome3) {
			materialcome2.setSurpplyId(materialcome3.getSurpplyId());
		}
		Materialcome material = materialcomeDao.getOperator(list.get(0));
		if (null != material) {
			materialcome2.setOperator(material.getOperator());
			materialcome2.setOperatorId(material.getOperatorId());
		}
		materialcome2.setNoticeNo(str);
		materialcome2.setFlag(list.get(0).getFlag());
		materialcome2.setId(UuidUtil.get32UUID());
		materialcome2.setDelFlag(Constant.MATERIALCOME_DELETETAG_1);
		materialcome2.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		materialcome2.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		materialcome2.setStatus(Constant.MATERIALCOME_STATUS_0);
		materialcome2.setPurchaseNo(list.get(0).getPurchaseNo());
		materialcome2.setReceiverId(AccountShiroUtil.getCurrentUser().getAccountId());
		for (Materialcome materialcome : list) {
			materialcome.setNoticeId(materialcome2.getId());
			materialcome.setDetailId(UuidUtil.get32UUID());
			totalCount += materialcome.getCount();
			sumBasicCost += materialcome.getSumBasicCost();
			sumAddCost += materialcome.getSumAddCost();
			sumOtherCost += materialcome.getSumOtherCost();
			if ("0".equals(list.get(0).getFlag())) {
				totalWt += materialcome.getActualWt();
			}else if("1".equals(list.get(0).getFlag())){
				totalWt += materialcome.getRequireWt();
			}
			
//			if (materialcome.getRequireWt()<(materialcome.getStoneWt()+materialcome.getGoldWt())) {
//				return 2;
//			}
		}
		materialcome2.setTotalCount(totalCount);
		materialcome2.setTotalWt(totalWt);
		materialcome2.setSumAddCost(sumAddCost);
		materialcome2.setSumBasicCost(sumBasicCost);
		materialcome2.setSumOtherCost(sumOtherCost);
		materialcomeDao.insert(materialcome2);
		materialcomeDao.insertDetail(list);
		return 1;
	}

	@Override
	public Map<String, Object> updateMaterial(String json) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<Materialcome> list = JSONArray.toList(JSONArray.fromObject(json),Materialcome.class);
		if (list.isEmpty()) {
			map.put("result", "请至少保留一条数据");
			return map;
		}
		List<Materialcome> updateList = new ArrayList<Materialcome>();
		List<Materialcome> addList = new ArrayList<Materialcome>();
		List<Materialcome> deleteList = new ArrayList<Materialcome>();
		List<Materialcome> delete = new ArrayList<Materialcome>();
		if (!list.isEmpty()) {
			list.get(0).setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			list.get(0).setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			list.get(0).setDelFlag(Constant.MATERIALCOME_DELETETAG_1);
			Materialcome materialcome = materialcomeDao.getFranchisee(list.get(0));
			if (null != materialcome) {
				list.get(0).setSurpplyId(materialcome.getSurpplyId());
			}
			Materialcome material = materialcomeDao.getOperator(list.get(0));
			if (null != material) {
				list.get(0).setOperator(material.getOperator());;
				list.get(0).setOperatorId(material.getOperatorId());
			}
			for (Materialcome m : list) {
				if (StringUtils.isNotBlank(m.getNoticeId())) {
					delete.add(m);
				}
			}
			if (!delete.isEmpty()) {
				deleteList = materialcomeDao.getDelete(delete, list.get(0).getId());
			}else if (delete.isEmpty()) {
				deleteList = materialcomeDao.getDelete1(list.get(0).getId());
			}
			
			for (Materialcome materialcome1 : list) {
				if (StringUtils.isNotEmpty(materialcome1.getNoticeId())) {
					updateList.add(materialcome1);
				}else{
					materialcome1.setNoticeId(materialcome1.getId());
					materialcome1.setDetailId(UuidUtil.get32UUID());
					addList.add(materialcome1);
				}
			}
			if(!CollectionUtils.isEmpty(updateList)){
				materialcomeDao.updateDetail(updateList);
			}
			if(!CollectionUtils.isEmpty(addList)){
				materialcomeDao.insertDetail(addList);
			}
			if(!CollectionUtils.isEmpty(deleteList)){
				materialcomeDao.deleteDetailById(deleteList);
			}
			materialcomeDao.update(list.get(0));
		}
		return map;
	}

	@Override
	public Map<String, Object> findPurchaseNo(String code) {
		Map<String, Object> map = new HashMap<String,Object>();
		Materialcome material = materialcomeDao.findPurchaseNo(code);
		if (null == material) {
			map.put("opertion", "fail");
			return map;
		}
		map.put("opertion", "success");
		map.put("material", material);
		return map;
	}

	@Override
	public Materialcome getOrg() {
		return materialcomeDao.getOrg(AccountShiroUtil.getCurrentUser().getOrgId());
	}

	@Override
	public Materialcome getMaterial(String id) {
		return materialcomeDao.getMaterial(id);
	}

	@Override
	public List<Materialcome> getDetail(String id) {
		Materialcome materialcome = new Materialcome();
		materialcome.setId(id);
		return materialcomeDao.findDetail(materialcome);
	}
	

}
