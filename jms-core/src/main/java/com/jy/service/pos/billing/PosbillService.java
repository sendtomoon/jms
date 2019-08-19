package com.jy.service.pos.billing;

import java.util.List;
import java.util.Map;
import com.jy.entity.pos.billing.PosBill;
import com.jy.entity.pos.billing.PosBillDetail;
import com.jy.entity.crm.members.Members;
import com.jy.service.base.BaseService;

public interface PosbillService extends BaseService<PosBill> {
	
	Map<String, Object> findDetail(PosBill posBill);
	
	void aduit(PosBill posBill);
	
	void updatePosbill(String data);
	
	void insertPosBill(String posbill,String detail,String bills);
	
	Map<String, Object> findProduct(String barCode);
	
	PosBill getOrg();
	
	Map<String, Object> getDetailByCode(PosBillDetail posBillDetail);
	
	PosBillDetail getGoldPrice(String goldType);
	
	Map<String, Object> getEarnest(String earnest);
	
	List<PosBill> getStore();
	
	List<PosBill> getAssistant();
	
	Map<String, Object> deletePosBill(String chks);
	
	Members getVip(String cardNo);
	
	void offset(PosBill posBill);
	
}
