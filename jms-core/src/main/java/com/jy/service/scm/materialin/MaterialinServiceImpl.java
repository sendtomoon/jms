package com.jy.service.scm.materialin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.materialin.MaterialinDao;
import com.jy.dao.scm.materialin.PurenterydetailDao;
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.materialin.Matinventory;
import com.jy.entity.scm.materialin.Purenterydetail;
import com.jy.service.base.BaseServiceImp;

@Service
public class MaterialinServiceImpl extends BaseServiceImp<Materialin> implements MaterialinService {

	private static final Logger logger = LoggerFactory.getLogger(MaterialinServiceImpl.class);

	@Autowired
	private MaterialinDao materialinDao;

	@Autowired
	private PurenterydetailDao purenterydetailDao;

	private final ReentrantLock inventoryLock = new ReentrantLock();

	private final ReentrantLock inventoryLock2 = new ReentrantLock();

	private final ReentrantLock increaseLock = new ReentrantLock();

	private final ReentrantLock recoverLock = new ReentrantLock();

	private static Map<String, Object> resultMap = Collections.synchronizedMap(new HashMap<String, Object>());

	@Override
	public String check(Materialin materialin) {
		List<Materialin> list = materialinDao.find(materialin);
		if (list.size() > 0) {
			if (list.get(0).getStatus().equals(Constant.MATERIALIN_STATUES_01)) {
				list.get(0).setStatus(Constant.MATERIALIN_STATUES_02);
				materialinDao.update(list.get(0));
				return "";
			}
			return "审核失败，状态错误";
		}
		return "找不到原料信息";
	}

	public String del(Materialin materialin) {
		List<Materialin> list = materialinDao.find(materialin);
		if (list.size() > 0) {
			if (list.get(0).getStatus().equals(Constant.MATERIALIN_STATUES_01)) {
				materialinDao.delete(list.get(0));
				return "";
			}
			return "删除失败，状态错误";
		}
		return "找不到原料信息";
	}

	@Override
	public Map<String, Object> toLockInventory(Materialin materialin) throws Exception {
		try {
			inventoryLock.lock();
			resultMap.clear();
			resultMap.put("code", Constant.MATERIALIN_SUCCESS_KEY);
			resultMap.put("message", Constant.MATERIALIN_SUCCESS_VALUE);
			resultMap.put("count", 0);
			int availNum = materialin.getAvailNum().intValue();
			double availWeight = materialin.getAvailWeight().doubleValue();
			/*
			 * if (availWeight < 0 || availNum < 0) { logger.info("===========>"
			 * + Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE);
			 * resultMap.put("code", Constant.MATERIALIN_ILLEGAL_PARAMS_KEY);
			 * resultMap.put("message",
			 * Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE); return resultMap; }
			 */
			Materialin m = materialinDao.getMaterialinById(materialin.getId());
			if (!StringUtils.isEmpty(m)) {
				/* 状态不可用的 */
				if (!m.getStatus().equals(Constant.MATERIALIN_STATUES_02)) {
					logger.info("===========>" + m.getStatus() + " >> " + Constant.MATERIALIN_NOT_MATCH_VALUE);
					resultMap.put("code", Constant.MATERIALIN_NOT_MATCH_KEY);
					resultMap.put("message", Constant.MATERIALIN_NOT_MATCH_VALUE);
					return resultMap;
				}
				/* 库存不足的 */
				switch (m.getType()) {
				case Constant.CHARGE_TYPE_GRAM: {
					if (m.getAvailWeight().doubleValue() == 0) {
						logger.info("===========>" + Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						resultMap.put("code", Constant.MATERIALIN_NOT_ENOUGH_KEY);
						resultMap.put("message", Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						return resultMap;
					}
					if (m.getAvailWeight().doubleValue() < availWeight) {
						availWeight = m.getAvailWeight().doubleValue();
					}
					m.setAvailNum(null);
					m.setAvailWeight(availWeight);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.updateInventoryAvailNum(m);
					resultMap.put("count", availWeight);
					break;
				}
				case Constant.CHARGE_TYPE_PIECE: {
					if (m.getAvailNum().intValue() == 0) {
						logger.info("===========>" + Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						resultMap.put("code", Constant.MATERIALIN_NOT_ENOUGH_KEY);
						resultMap.put("message", Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						return resultMap;
					}
					if (m.getAvailNum().intValue() < availNum) {
						availNum = m.getAvailNum().intValue();
					}
					m.setAvailNum(availNum);
					m.setAvailWeight(null);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.updateInventoryAvailNum(m);
					resultMap.put("count", availNum);
					break;
				}
				}

			} else {
				logger.info("===========>" + Constant.MATERIALIN_NON_VALUE);
				resultMap.put("code", Constant.MATERIALIN_NON_KEY);
				resultMap.put("message", Constant.MATERIALIN_NON_VALUE);
				return resultMap;
			}
		} finally {
			inventoryLock.unlock();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> reduceInventory(Materialin materialin) throws Exception {
		Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());
		try {
			inventoryLock2.lock();
			map.clear();
			map.put("code", Constant.MATERIALIN_SUCCESS_KEY);
			map.put("message", Constant.MATERIALIN_SUCCESS_VALUE);
			map.put("count", 0);
			int num = materialin.getNum().intValue();
			double weight = materialin.getWeight().doubleValue();
			if (weight < 0 || num < 0) {
				logger.info("===========> " + Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE);
				map.put("code", Constant.MATERIALIN_ILLEGAL_PARAMS_KEY);
				map.put("message", Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE);
				return map;
			}
			Materialin m = materialinDao.getMaterialinById(materialin.getId());
			if (!StringUtils.isEmpty(m)) {
				/* 状态不可用的 */
				if (!m.getStatus().equals(Constant.MATERIALIN_STATUES_02)) {
					logger.info("===========>" + m.getStatus() + " >> " + Constant.MATERIALIN_NOT_MATCH_VALUE);
					map.put("code", Constant.MATERIALIN_NOT_MATCH_KEY);
					map.put("message", Constant.MATERIALIN_NOT_MATCH_VALUE);
					return map;
				}
				/* 库存不足的 */
				switch (m.getType()) {
				case Constant.CHARGE_TYPE_GRAM: {
					if (m.getWeight().doubleValue() == 0) {
						logger.info("===========>" + Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						map.put("code", Constant.MATERIALIN_NOT_ENOUGH_KEY);
						map.put("message", Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						return map;
					}
					if (m.getWeight().doubleValue() < weight) {
						weight = m.getWeight().doubleValue();
					}
					m.setNum(null);
					m.setWeight(weight);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.updateInventoryNum(m);
					map.put("count", weight);
					break;
				}
				case Constant.CHARGE_TYPE_PIECE: {
					if (m.getNum().intValue() == 0) {
						logger.info("===========>" + Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						map.put("code", Constant.MATERIALIN_NOT_ENOUGH_KEY);
						map.put("message", Constant.MATERIALIN_NOT_ENOUGH_VALUE);
						return map;
					}
					if (m.getNum().intValue() < num) {
						num = m.getNum().intValue();
					}
					m.setNum(num);
					m.setWeight(null);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.updateInventoryNum(m);
					map.put("count", num);
					break;
				}
				}
			} else {
				logger.info("===========>" + Constant.MATERIALIN_NON_VALUE);
				map.put("code", Constant.MATERIALIN_NON_KEY);
				map.put("message", Constant.MATERIALIN_NON_VALUE);
				return map;
			}
		} finally {
			inventoryLock2.unlock();
		}
		return map;
	}

	@Override
	public Map<String, Object> increaseInventory(Materialin materialin) throws Exception {
		Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());
		try {
			increaseLock.lock();
			map.clear();
			map.put("code", Constant.MATERIALIN_SUCCESS_KEY);
			map.put("message", Constant.MATERIALIN_SUCCESS_VALUE);
			map.put("count", 0);
			int num = materialin.getNum().intValue();
			double weight = materialin.getWeight().doubleValue();
			if (weight < 0 || num < 0) {
				logger.info("===========>" + Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE);
				map.put("code", Constant.MATERIALIN_ILLEGAL_PARAMS_KEY);
				map.put("message", Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE);
				return map;
			}
			Materialin m = materialinDao.getMaterialinByConditions(materialin);
			if (!StringUtils.isEmpty(m)) {
				/* 状态不可用的 */
				if (!m.getStatus().equals(Constant.MATERIALIN_STATUES_02)) {
					logger.info("===========>" + m.getStatus() + " >> " + Constant.MATERIALIN_NOT_MATCH_VALUE);
					map.put("code", Constant.MATERIALIN_NOT_MATCH_KEY);
					map.put("message", Constant.MATERIALIN_NOT_MATCH_VALUE);
					return map;
				}
				switch (m.getType()) {
				case Constant.CHARGE_TYPE_GRAM: {
					m.setNum(null);
					m.setWeight(weight);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.increaseInventory(m);
					map.put("count", weight);
					break;
				}
				case Constant.CHARGE_TYPE_PIECE: {
					m.setNum(num);
					m.setWeight(null);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.increaseInventory(m);
					map.put("count", num);
					break;
				}
				}
			} else {
				logger.info("===========>" + Constant.MATERIALIN_NON_VALUE);
				map.put("code", Constant.MATERIALIN_NON_KEY);
				map.put("message", Constant.MATERIALIN_NON_VALUE);
				return map;
			}
		} finally {
			increaseLock.unlock();
		}
		return map;
	}

	@Override
	public Page<Materialin> findMaterialinSplit(Materialin materialin, Page<Materialin> page) {
		page.setResults(materialinDao.findMaterialinSplit(materialin, page));
		return page;
	}

	@Override
	public Map<String, Object> recoverLockedInventory(Materialin materialin) throws Exception {
		Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());
		try {
			recoverLock.lock();
			map.clear();
			map.put("code", Constant.MATERIALIN_SUCCESS_KEY);
			map.put("message", Constant.MATERIALIN_SUCCESS_VALUE);
			map.put("count", 0);
			int availNum = materialin.getAvailNum().intValue();
			double availWeight = materialin.getAvailWeight().doubleValue();
			if (availWeight < 0 || availNum < 0) {
				logger.info("===========>" + Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE);
				map.put("code", Constant.MATERIALIN_ILLEGAL_PARAMS_KEY);
				map.put("message", Constant.MATERIALIN_ILLEGAL_PARAMS_VALUE);
				return map;
			}
			Materialin m = materialinDao.getMaterialinById(materialin.getId());
			if (!StringUtils.isEmpty(m)) {
				/* 状态不可用的 */
				if (!m.getStatus().equals(Constant.MATERIALIN_STATUES_02)) {
					logger.info("===========>" + m.getStatus() + " >> " + Constant.MATERIALIN_NOT_MATCH_VALUE);
					map.put("code", Constant.MATERIALIN_NOT_MATCH_KEY);
					map.put("message", Constant.MATERIALIN_NOT_MATCH_VALUE);
					return map;
				}
				switch (m.getType()) {
				case Constant.CHARGE_TYPE_GRAM: {
					m.setAvailNum(null);
					m.setAvailWeight(availWeight);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.recoverLockedInventory(m);
					map.put("count", availWeight);
					break;
				}
				case Constant.CHARGE_TYPE_PIECE: {
					m.setAvailNum(availNum);
					m.setAvailWeight(null);
					m.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
					materialinDao.recoverLockedInventory(m);
					map.put("count", availNum);
					break;
				}
				}
			} else {
				logger.info("===========>" + Constant.MATERIALIN_NON_VALUE);
				map.put("code", Constant.MATERIALIN_NON_KEY);
				map.put("message", Constant.MATERIALIN_NON_VALUE);
				return map;
			}
		} finally {
			recoverLock.unlock();
		}
		return map;
	}

	@Override
	public List<Matinventory> findMaterial(String id) {
		return materialinDao.findMaterial(id);
	}

}
