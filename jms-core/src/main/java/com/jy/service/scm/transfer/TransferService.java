package com.jy.service.scm.transfer;

import java.util.Map;

import com.jy.entity.scm.transfer.Transfer;
import com.jy.entity.scm.transfer.TransferDetail;
import com.jy.service.base.BaseService;

public interface TransferService extends BaseService<Transfer> {


	
	

	/**
	 * 修该信息
	 */
	public void updateTransfer(Transfer f, TransferDetail t);
	
	/**
	 * 删除信息
	 */
	public void deleteTransfer(Transfer f);
	
	/**
	 * 根据ID查询状态
	 */
	public Transfer getByTransferStatus(String id);

	/**
	 * 判断原料代码是否存在
	 * @param code
	 * @return
	 */
	int findByCode(String code);


	void insertTransfer(Transfer f, String t);
	
	/**
	 * 删除
	 * @param transfer
	 * @return
	 */
	Map<String, Object> del(String cheks);
	
	/**
	 * 审核
	 * @param F
	 * @return
	 */
	String proAuditing(Transfer F);
	
	Map<String, Object> view(Transfer transfer);
	
	/**
	 * 商品移库查看
	 * @param transfer
	 * @return
	 */
	Map<String, Object> viewProduct(Transfer transfer);
	
	String editProduct(Transfer f, String t);
}
