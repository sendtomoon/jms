package com.jy.service.scm.transfer;

import java.util.List;

import com.jy.entity.scm.transfer.TransferDetail;
import com.jy.service.base.BaseService;

public interface TransferDetaiService extends BaseService<TransferDetail> {
	/**
	 * 修该信息
	 */
	public void updateTransferDetail(TransferDetail t);
	/**
	 * 判断原料代码是否存在
	 * @param code
	 * @return
	 */
	List<TransferDetail> findByCode(String code);
	


}
