package com.jy.dao.scm.materialcome;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.dao.base.BaseDao;
import com.jy.dao.base.JYBatis;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.materialcome.Materialcome;

@JYBatis
public interface MaterialcomeDao extends BaseDao<Materialcome> {
	
	void insertDetail(@Param("list") List<Materialcome> list);
	
	void updateDetail(@Param("list") List<Materialcome> list);
	
	int updateStatus(Materialcome materialcome);
	
	//修改来料删除状态
	void updateDelflag(@Param("list")List<Materialcome> list,@Param("materialcome")Materialcome materialcome);
	
	//查看来料明细
	List<Materialcome> findDetail(Materialcome materialcome);
	
	void batchInsert(@Param("list") List<Materialcome> list);
	
	//审核来料
	void aduit(Materialcome materialcome);
	
	//获取供应商
	Materialcome getFranchisee(Materialcome materialcome);
	
	//获取收货人
	List<SelectData> getReceiver(String orgId);
	
	//查询导入
	List<Materialcome> getMaterialcomeUpload(String userId);
	
	//添加到临时表
	void batchIntoTempTable(@Param("list")List<Materialcome> list);
	
	//获取经办人
	Materialcome getOperator(Materialcome materialcome);
	
	//获取来料
	Materialcome getMaterial(@Param("id")String id);
	
	//删除明细
	void deleteDetail(@Param("list")List<Materialcome> list);
	
	//删除来料
	void deleteMaterialcome(@Param("list")List<Materialcome> list,@Param("materialcome")Materialcome materialcome);
	
	//获取删除的详情
	List<Materialcome> getDelete(@Param("list")List<Materialcome> list,@Param("noticeId")String noticeId);
	
	List<Materialcome> getDelete1(@Param("noticeId")String noticeId);
	
	void deleteDetailById(@Param("list")List<Materialcome> list);
	
	Materialcome findPurchaseNo(@Param("code")String code);
	
	Materialcome getOrg(@Param("orgId")String orgId);
}
