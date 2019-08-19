package com.jy.dao.pos.billing;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.pos.billing.PosBillDetail;
import com.jy.entity.pos.payments.Payments;
import com.jy.entity.crm.members.Members;

@JYBatis
public interface PosbillDao extends BaseDao<PosBill> {
	
	List<PosBillDetail> findDetail(PosBill posBill);
	
	void updateProductStatus(@Param("barCode")String barCode,@Param("status")String status);
	
	void aduit(PosBill posBill);
	
	PosBillDetail findProduct(@Param("barCode")String barCode,@Param("orgId")String orgId);
	
	PosBill findPosBill(@Param("id")String id);
	
	PosBill getOrg(PosBill posBill);
	
	void insertDetail(List<PosBillDetail> list);
	
	PosBillDetail getDetailByCode(@Param("barCode")String barCode);
	
	PosBillDetail getGoldPrice(@Param("goldType")String goldType);
	
	void updateStatus(@Param("id")String id,@Param("status")String status);
	
	PosBill getEarnest(@Param("earnest")String earnest);
	
	List<PosBill> getStore(@Param("orgId")String orgId);
	
	List<PosBill> getAssistant(@Param("orgId")String orgId);
	
	void updateEarnest(@Param("earnest")String earnest,@Param("status")String status);
	
	void deletePosBill(List<PosBill> list);
	
	void deleteDetail(List<PosBillDetail> lsit);
	
	void updateEarnestList(List<PosBill> list);
	
	Members getVip(@Param("cardNo")String cardNo);
	
	List<Payments> findPay(@Param("orderId")String orderId,@Param("orderNo")String orderNo);
	
	void deleteBills(PosBill posBill);
	
}
