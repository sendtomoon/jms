package com.jy.service.scm.returnbill;

import java.util.List;
import java.util.Map;

import com.jy.common.mybatis.Page;
import com.jy.entity.scm.returnbill.ReturnBill;
import com.jy.entity.scm.returnbill.ReturnBillDetail;
import com.jy.service.base.BaseService;

public interface ReturnBillService extends BaseService<ReturnBill> {

	/**
	 * 新增一张退货单
	 * 
	 * @param qb
	 */
	void insertReturnBill(ReturnBill rb);

	/**
	 * 查找退货单列表
	 * 
	 * @param list
	 * @return List<ReturnBill>
	 */
	List<ReturnBill> findReturnBillByIds(List<ReturnBill> list);

	/**
	 * 设置退货单为审核状态
	 * 
	 * @param rb
	 */
	void updateReturnBillNo(ReturnBill rb);

	/**
	 * 筛选退货单数据
	 * 
	 * @param rb
	 * @param page
	 * @return
	 */
	Page<ReturnBill> findByPageFilter(ReturnBill rb, Page<ReturnBill> page);

	/**
	 * 查找QC单并返回
	 * 
	 * @param i
	 * @return
	 */
	boolean addReturnBillFromQC(String str);

	List<ReturnBillDetail> queryCode(String code);


	ReturnBill getReturnBill(String id);

	List<ReturnBillDetail> getReturnBillDetail(String id);
	
	String getReturnBillStatus(String id);

	Map<String,Object> find(ReturnBillDetail rb, Page<ReturnBillDetail> page);
	
	List<ReturnBillDetail> queryNotice(String noticeNo,String enteryNo);

	void saveManualReturnBill(String rbdata, String rbddata);

	Map<String, Object> find(ReturnBillDetail rb);

	void delBatch(String cheks);
	
	Map<String, Object> findForModify(String id);

	void saveModifyReturnBill(String rbdata, String rbddata);
	

}





