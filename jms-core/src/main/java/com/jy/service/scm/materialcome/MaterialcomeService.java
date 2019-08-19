package com.jy.service.scm.materialcome;

import java.util.List;
import java.util.Map;

import com.jy.entity.base.SelectData;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.service.base.BaseService;

public interface MaterialcomeService extends BaseService<Materialcome> {
	
	int updateStatus(Materialcome materialcome);
	
	Map<String, Object> delBatch(String chks);
	
	Map<String,Object> findDetail(Materialcome materialcome);
	
	void insertMaterialcome(String data) throws Exception;
	
	//审核来料
	void aduit(Materialcome materialcome);
	
	//获取收货人
	List<SelectData> getReceiver(String orgId);
	
	//查询导入
	List<Materialcome> getMaterialcomeUpload(String userId);
	
	//导入添加
	int batchImport(List<Materialcome> list);
	
	//修改来料
	Map<String, Object> updateMaterial(String json);
	
	//查询采购单号是否存在
	Map<String, Object> findPurchaseNo(String code);
	
	//获取拨入单位
	Materialcome getOrg();
	
	//获取来货
	Materialcome getMaterial(String id);

	//获取明细
	List<Materialcome> getDetail(String id);
}
