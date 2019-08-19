package com.jy.service.pos.earnest;

import java.util.Map;

import com.jy.entity.pos.earnest.EarnestOrder;
import com.jy.service.base.BaseService;

public interface EarnestOrderService extends BaseService<EarnestOrder>{
	/**
	 * 新增定金单
	 * @param earnestOrder
	 * @return
	 */
	public EarnestOrder insertEarnest(EarnestOrder earnestOrder);
	/**
	 * 新增退款单
	 * @param earnestOrder
	 * @return
	 */
	public Map<String, Object> refund(EarnestOrder earnestOrder);
	/**
	 * 查看定金单
	 * @param earnestOrder
	 * @return
	 */
	public Map<String, Object> findEarnestOrder(EarnestOrder earnestOrder);
	/**
	 * 查看退款单
	 * @param earnestOrder
	 * @return
	 */
	public Map<String, Object> refundView(EarnestOrder earnestOrder);
	
	/**
	 * 付款成功，修改定金状态
	 * @param id
	 */
	public void updateStatus(String id);
	
	/**
	 * 删除待付款的定金单
	 * @param cheks
	 * @return
	 */
	public Map<String, Object> deleteBth(String cheks);
	
	/**
	 * 查看
	 * @param earnestOrder
	 * @return
	 */
	public Map<String, Object> view(EarnestOrder earnestOrder);
	
	/**
	 * 修改
	 */
	public EarnestOrder updateEarnest(EarnestOrder earnestOrder);
}
