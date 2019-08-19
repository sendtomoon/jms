package com.jy.common.tool;

import com.jy.common.utils.base.Const;

public class Constant extends Const {
	public static final String SUNDAY = "SUNDAY";
	
	
	/**
	 * 订单标签0：未处理
	 * */
	public static final String ORDER_LABEL_0="0";
	/**
	 * 订单标签1:已汇总
	 * */
	public static final String ORDER_LABEL_1="1";
	/**
	 * 订单标签2:已拆分
	 * */
	public static final String ORDER_LABEL_2="2";
	/**
	 *拆分成功
	 */
	public static final String ORDER_LABEL_SUCCEED ="拆分成功";
	/**
	 *拆分失败
	 */
	public static final String ORDER_LABEL_FAIL="拆分失败";
	/**
	 *审核成功
	 */
	public static final String CHECK_SUCCEED = "审核成功";
	/**
	 *审核失败
	 */
	public static final String CHECK_FAIL = "审核失败";
	
	/**
	 *反审核成功
	 */
	public static final String U_CHECK_SUCCEED = "反审核成功";
	/**
	 *反审核失败
	 */
	public static final String U_CHECK_FAIL = "反审核失败";
	
	/**
	 *提交成功
	 */
	public static final String SUBMIT_SUCCEED = "提交成功";
	/**
	 *提交失败
	 */
	public static final String SUBMIT_FAIL = "提交失败";
	
	/**
	 * 草稿
	 */
	public static final String PRODUCT_STATE_0 = "0";
	/**
	 * 审核
	 */
	public static final String PRODUCT_STATE_A = "A";
	/**
	 * 可售
	 */
	public static final String PRODUCT_STATE_B = "B";
	/**
	 * 锁定
	 */
	public static final String PRODUCT_STATE_C = "C";
	/**
	 * 销售
	 */
	public static final String PRODUCT_STATE_D = "D";
	/**
	 * 销号
	 */
	public static final String PRODUCT_STATE_E = "E";
	/**
	 * 退厂
	 */
	public static final String PRODUCT_STATE_F = "F";
	/**
	 * 待审
	 */
	public static final String PRODUCT_STATE_1 = "1";
	/**
	 * 删除
	 */
	public static final String PRODUCT_STATE_9 = "9";
	
	/**
	 * 匹配
	 */
	public static final String SPLIT_STATE_0="0";
	
	/**
	 * 出库
	 */
	public static final String SPLIT_STATE_1="1";
	
	/**
	 * 销售（历史）
	 */
	public static final String SCM_HIS_TYPE_01="01";
	/**
	 * 销退（历史）
	 */
	public static final String SCM_HIS_TYPE_02="02";
	/**
	 * 采购入库（历史）
	 */
	public static final String SCM_HIS_TYPE_03="03";
	/**
	 * 采购退货（历史）
	 */
	public static final String SCM_HIS_TYPE_04="04";
	/**
	 * 调拨出库（历史）
	 */
	public static final String SCM_HIS_TYPE_05="05";
	/**
	 * 调拨入库（历史）
	 */
	public static final String SCM_HIS_TYPE_06="06";
	
	
	/**
	 *拆分成功
	 */
	public static final String SPLIT_SUCCEED = "拆分成功";
	
	/**
	 *拆分失败
	 */
	public static final String SPLIT_FAIL = "拆分失败";
	
	
	/**
	 * 机构等级(0:公司)
	 */
	public static final String ORGGRADE_01 = "0";
	
	/**
	 * 机构等级(1:部门)
	 */
	public static final String ORGGRADE_02 = "1";
	
	/**
	 * 机构等级(4:门店)
	 */
	public static final String ORGGRADE_03 = "4";
	

	/**
	 * 仓库或仓位（不默认、系统定义）
	 */
	public static final String WAREHOUSE_DEFAULT_01 = "0";
	
	
	/**
	 * 仓库或仓位（默认、用户定义）
	 */
	public static final String WAREHOUSE_DEFAULT_02 = "1";

	/**
	 * 仓库仓位默认值：0000
	 */
	public static final String WAREHOUSE_DEFAULT = "0000";
	
	/**
	 * 原料入库状态（审核中）
	 */
	public static final String MATERIALIN_STATUES_01 = "1";
	/**
	 * 原料入库状态（可销售）
	 */
	public static final String MATERIALIN_STATUES_02 = "3";
	
	public static final int MATERIALIN_NON_KEY = 0;
	public static final String MATERIALIN_NON_VALUE = "操作失败：该记录不存在";
	
	public static final int MATERIALIN_SUCCESS_KEY = 1;
	public static final String MATERIALIN_SUCCESS_VALUE = "操作成功";
		
	public static final int MATERIALIN_PARAMS_LACK_KEY = -1;
	public static final String MATERIALIN_PARAMS_LACK_VALUE = "操作失败：参数缺失";
	
	public static final int MATERIALIN_NOT_MATCH_KEY = -2;
	public static final String MATERIALIN_NOT_MATCH_VALUE = "操作失败：不可用状态";
	
	public static final int MATERIALIN_NOT_ENOUGH_KEY = -3;
	public static final String MATERIALIN_NOT_ENOUGH_VALUE = "操作失败：库存不足";
	
	public static final int MATERIALIN_ILLEGAL_PARAMS_KEY = -4;
	public static final String MATERIALIN_ILLEGAL_PARAMS_VALUE = "操作失败：非法参数";
	
	public static final String ORDER_MATE_SUCCEED_PART="未能完全匹配";
	
	/**
	 * 下单失败
	 */
	public static final String ORDER_DOW_FAIL="下单失败";
	
	/**
	 * 下单成功
	 */
	public static final String ORDER_DOW_SUCCEED="下单成功";
	
	/**
	 * 入库单状态（草稿）
	 */
	public static final String PURENTERY_STATUS_01 = "0";
	/**
	 * 入库单状态（待审核）
	 */
	public static final String PURENTERY_STATUS_02 = "1";
	/**
	 * 入库单状态（已审核）
	 */
	public static final String PURENTERY_STATUS_03 = "2";
	/**
	 * 入库单状态（已拒絕）
	 */
	public static final String PURENTERY_STATUS_05 = "4";
	/**
	 * 入库单状态（已完成）
	 */
	public static final String PURENTERY_STATUS_06 = "3";
	/**
	 * 入库单状态（已删除）
	 */
	public static final String PURENTERY_STATUS_04 = "9";
	
	/**
	 * 入库单类型（一码一件）
	 */
	public static final String PURENTERY_TYPE_01 = "0";
	/**
	 * 入库单类型（一码多件）
	 */
	public static final String PURENTERY_TYPE_02 = "1";

	/*-------------------单据前缀定义begin-------------------*/
	/**
	 * 条码前缀：JY
	 */
	public static final String SCM_CODE_PREFIX="JY";
	
	/**
	 * 订单前缀：出库,CK
	 */
	public static final String SCM_BILL_PREFIX_CK="CK";
	
	/**
	 * 订单前缀：入库,RK
	 */
	public static final String SCM_BILL_PREFIX_RK="RK";
	
	/**
	 * 订单前缀：销售,XS
	 */
	public static final String SCM_BILL_PREFIX_XS="XS";
	
	/**
	 * 订单前缀：销售退货,XT
	 */
	public static final String SCM_BILL_PREFIX_XT="XT";
	
	/**
	 * 订单前缀：售后,SH
	 */
	public static final String SCM_BILL_PREFIX_SH="SH";
	
	/**
	 * 订单前缀：采购退货,CT
	 */
	public static final String SCM_BILL_PREFIX_CT="CT";
	/**
	 * 订单前缀：采购,CG
	 */
	public static final String SCM_BILL_PREFIX_CG="CG";
	/**
	 * 订单前缀：采购,CG
	 */
	public static final String SCM_BILL_PREFIX_TH="TH";
	/**
	 * 订单前缀：移库库,YK
	 */
	public static final String SCM_BILL_PREFIX_YK="YK";
	/**
	 * 盘点前缀：盘点,PD
	 */
	public static final String SCM_BILL_PREFIX_PD="PD"; 
	/**
	 * 通知单：Recieve the notice（收货通知）
	 */
	public static final String SCM_BILL_PREFIX_RN ="RN";
	/**
	 * 流转单：Handover（移交、流转）
	 */
	public static final String SCM_BILL_PREFIX_HO ="HO";
	/**
	 * 质检单：Quality Checking
	 */
	public static final String SCM_BILL_PREFIX_QC ="QC";
	/**
	 * 退厂单：Returned to the factory
	 */
	public static final String SCM_BILL_PREFIX_RF ="RF";
	
	/**
	 * 定金单：,DJ
	 */
	public static final String SCM_BILL_PREFIX_DJ="DJ";
	/**
	 * 定金单退款：,DJTK
	 */
	public static final String SCM_BILL_PREFIX_DJTK="DJTK";
	
	/*-------------------单据前缀定义end-------------------*/
	
	/**
	 * 计费方式：按克 2
	 */
	public static final String CHARGE_TYPE_GRAM="2";
	
	/**
	 * 计费方式：按件 1
	 */
	public static final String CHARGE_TYPE_PIECE="1";
	/**
	 * 出库删除
	 */
	public static final String SPLIT_DELETETAG_1="1";
	/**
	 * 出库正常
	 */
	public static final String SPLIT_DELETETAG_0="0";
	
	/**
	 * 条码区分：商品表的
	 */
	public static final String TABLE_TYPE_PRODUCT = "0";
	/**
	 * 条码区分：原料库存表的
	 */
	public static final String TABLE_TYPE_MATERIAL = "1";
	
	/**
	 * 
	 */
	public static final String TABLE_TYPE_PRIMARY = "2";
	
	/**
	 * 移库类型：0_移库
	 */
	public static final String TRANSFER_TYPE_1="0";
	
	/**
	 * 移库错误信息
	 */
	public static final String TRANSFER_ERROR_1="找不到数据！";
	public static final String TRANSFER_ERROR_2="不是创建用户，无权限！";
	public static final String TRANSFER_ERROR_3="找不到数据！";
	public static final String TRANSFER_ERROR_4="数据获取失败！";
	
	/**
	 * 移库状态:0_失效
	 */
	public static final String TRANSFER_STATUES_01="0";
	/**
	 * 移库状态:1_有效
	 */
	public static final String TRANSFER_STATUES_02="1";
	/**
	 * 移库状态:9_删除
	 */
	public static final String TRANSFER_STATUES_03="9";
	
	/**
	 * 删除标记：0_正常
	 */
	public static final String DELETE_TAG_0="0";
	/**
	 * 删除标记：1_删除
	 */
	public static final String DELETE_TAG_1="1";
	
	/**
	 * 出库（结价类型：采购成本）
	 */
	public static final String PRICE_TYPE_1="1";
	/**
	 * 出库（结价类型：财务成本）
	 */
	public static final String PRICE_TYPE_2="2";
	/**
	 * 出库（结价类型：牌价）
	 */
	public static final String PRICE_TYPE_3="3";
	/**
	 * 出库（结价类型：原牌价）
	 */
	public static final String PRICE_TYPE_4="4";
	/**
	 * 出库（结价类型：重量）
	 */
	public static final String PRICE_TYPE_5="5";

	/**
	 * 0-原材料，
	 */
	public static final String MOUDLE_CATYPE_0="0";
	
	/**
	 * 1-成品，
	 */
	public static final String MOUDLE_CATYPE_1="1";
	/**
	 * 采购出库（出库常量）
	 */
	public static final String SCM_OUTBOUND_TYPE_1="1";
	/**
	 * 销售出库（出库常量）
	 */
	public static final String SCM_OUTBOUND_TYPE_2="2";
	/**
	 * 调拨出库（出库常量）
	 */
	public static final String SCM_OUTBOUND_TYPE_3="3";
	
	/**
	 * 采购单状态（草稿）
	 */
	public static final String ORDER_STATUS_01 = "0";
	/**
	 * 采购单状态（待审核）
	 */
	public static final String ORDER_STATUS_02 = "1";
	/**
	 * 采购单状态（已审核）
	 */
	public static final String ORDER_STATUS_03 = "2";
	/**
	 * 采购单状态（已完成）
	 */
	public static final String ORDER_STATUS_04 = "3";
	/**
	 * 采购单状态（已拒绝）
	 */
	public static final String ORDER_STATUS_05 = "4";
	/**
	 * 采购单状态（已删除）
	 */
	public static final String ORDER_STATUS_09 = "9";
	
	/**
	 * 采购订单来源（系统新增）
	 */
	public static final String ORDER_SOURCE_1="1";
	/**
	 * 采购订单来源（珠宝通）
	 */
	public static final String ORDER_SOURCE_2="2";
	
	/**
	 * 克重
	 */
	public static final String ATTR_WEIGHT="ATTR_WEIGHT";
	/**
	 * 材质
	 */
	public static final String ATTR_GOLDTYPE="ATTR_GOLDTYPE";
	/**
	 * 主石颜色
	 */
	public static final String ATTR_COLOR="ATTR_COLOR";
	/**
	 * 主石净度
	 */
	public static final String ATTR_CLARITY="ATTR_CLARITY";
	/**
	 * 主石大小
	 */
	public static final String ATTR_STONESIZE="ATTR_STONESIZE";
	/**
	 * 切工
	 */
	public static final String ATTR_CUT="ATTR_CUT";
	/**
	 * 圈口
	 */
	public static final String ATTR_CIRCEL="ATTR_CIRCEL";
	/**
	 * 计价方式
	 */
	public static final String ATTR_FEETYPE="ATTR_FEETYPE";
	/**
	 * 内部要货成品
	 */
	public static final String ORDER_TYPE_0="0";
	/**
	 * 供应商采购成品
	 */
	public static final String ORDER_TYPE_1="1";
	/**
	 * 内部要货物料
	 */
	public static final String ORDER_TYPE_2="2";
	/**
	 * 供应商采购物料
	 */
	public static final String ORDER_TYPE_3="3";
	
	/**
	 *Excel商品导入路径
	 */
	public static final String Excel_UPLOADTEMP="uploadTemp";
	
	/**
	 * 来料删除
	 */
	public static final String MATERIALCOME_DELETETAG_0="0";
	
	/**
	 * 来料正常
	 */
	public static final String MATERIALCOME_DELETETAG_1="1";
	
	/**
	 * 来料状态草稿
	 */
	public static final String MATERIALCOME_STATUS_0="0";
	
	/**
	 * 来料状态待审核
	 */
	public static final String MATERIALCOME_STATUS_1="1";
	
	/**
	 * 来料状态已审核
	 */
	public static final String MATERIALCOME_STATUS_2="2";
	
	/**
	 * 来料状态已拒绝
	 */
	public static final String MATERIALCOME_STATUS_3="3";
	
	/**
	 * 来料状态已删除
	 */
	public static final String MATERIALCOME_STATUS_9="9";
	
	/**
	 * 来料素金
	 */
	public static final String MATERIALCOME_FLAG_0="0";
	
	/**
	 * 来料镶嵌
	 */
	public static final String MATERIALCOME_FLAG_1="1";
	
	/**
	 * 来料前缀TZ
	 */
	public static final String  SCM_CODE_MATERIALCOME="TZD";
	/**
	 * 流转未接收
	 */
	public static final String CIRCULATION_FLAG_0="0";
	/**
	 * 流转已接收
	 */
	public static final String CIRCULATION_FLAG_1="1";
	
	/**
	 * 入库通知单（正则）
	 */
	public static final String MATERIALCOME_EXEC = "/^RN[0-1][0-9]{13}$/";
	/**
	 * 石单位，ct
	 */
	public static final String SCM_DATA_STONEUNIT_CT="ct";
	/**
	 * 石单位，g
	 */
	public static final String SCM_DATA_STONEUNIT_G="g";
	/**
	 * 石单位，pc
	 */
	public static final String SCM_DATA_STONEUNIT_PC="pc";
	/**
	 * 商品管理状态排除选项
	 */
	public static final String PRODUCT_IMS_STATUS="可售,锁定,已售,销号,";
	/**
	 * 商品库存状态排除选项
	 */
	public static final String PRODUCT_PUR_STATUS="草稿,待审,已审,退厂,删除,";

	/**
	 * 零售管理 金价管理 编号前缀 PA
	 */
	public static final String POS_GOLDPRICE_PA="PA";
	
	/**
	 * 销售开单前缀
	 */
	public static final String POS_BILLING_XS="XS";
	
	/**
	 * 销售类型 销售
	 */
	public static final String POS_BILLING_TYPE_1="1";
	
	/**
	 * 销售类型 以旧换新
	 */
	public static final String POS_BILLING_TYPE_2="2";
	
	/**
	 * 销售类型 旧收本号
	 */
	public static final String POS_BILLING_TYPE_3="3";
	
	/**
	 * 销售类型 旧收外号
	 */
	public static final String POS_BILLING_TYPE_4="4";
	
	/**
	 * 销售类型 旧收截料
	 */
	public static final String POS_BILLING_TYPE_5="5";
	
	/**
	 * 销售类型 定金
	 */
	public static final String POS_BILLING_TYPE_6="6";
	
	/**
	 * 销售状态 草稿
	 */
	public static final String POS_BILLING_STATUS_0="0";
	
	/**
	 * 销售状态 待审核
	 */
	public static final String POS_BILLING_STATUS_1="1";
	
	/**
	 * 销售状态 已审核
	 */
	public static final String POS_BILLING_STATUS_2="2";
	
	/**
	 * 销售状态 待付款
	 */
	public static final String POS_BILLING_STATUS_3="3";
	
	/**
	 * 销售状态 红冲
	 */
	public static final String POS_BILLING_STATUS_4="4";
	
	/**
	 * 销售状态 已付款
	 */
	public static final String POS_BILLING_STATUS_9="9";
	/**
	 * 销售订单
	 */
	public static final String PAYMENTS_TYPE_1="1";
	/**
	 * 押金单
	 */
	public static final String PAYMENTS_TYPE_2="2";
	
	
	/**
	 * 定金单状态 0：不可用
	 */
	public static final String POS_EARNEST_STATUS_01="0";
	
	/**
	 * 定金单状态 1：可用
	 */
	public static final String POS_EARNEST_STATUS_02="1";
	/**
	 * 定金单状态 2：已使用
	*/
	public static final String POS_EARNEST_STATUS_03="2";
	/**
	 * 定金单状态 3：待付款
	*/
	public static final String POS_EARNEST_STATUS_04="3";
	
	/**
	 * 定金单类型 0：退款单
	 */
	public static final String POS_EARNEST_TYPE_01="0";
	/**
	 * 定金单类型 1：定金单
	 */
	public static final String POS_EARNEST_TYPE_02="1";
	
	
	/**
	 * 付款类别：销售单1
	 */
	public static final String POS_PAYMENTS_TYPE_01="1";
	/**
	 * 付款类别：定金单2
	 */
	public static final String POS_PAYMENTS_TYPE_02="2";
	
	/**
	 * 会员积分操作失败
	 */
	public static final int MEMBER_POINTS_NON_KEY = 0;
	/**
	 * 会员积分操作成功
	 */
	public static final int MEMBER_POINTS_SUCCESS_KEY = 1;
	/**
	 * 会员积分操作失败（原因）
	 */
	public static final String MEMBER_POINTS_NON_01 = "会员不存在";
	public static final String MEMBER_POINTS_NON_02 = "系统参数为空";
	public static final String MEMBER_POINTS_NON_03 = "模块参数为空";
	public static final String MEMBER_POINTS_NON_04 = "积分来源为空";
	public static final String MEMBER_POINTS_NON_05 = "明细类型为空";
	public static final String MEMBER_POINTS_NON_06 = "积分为空";
	public static final String MEMBER_POINTS_NON_07 = "会员参数为空";
	public static final String MEMBER_POINTS_NON_08 = "会员积分不够";
	public static final String MEMBER_POINTS_NON_09 = "用户有多条积分记录";
	public static final String MEMBER_POINTS_SUCCESS_01 = "积分操作成功";
	
	/**
	 * 积分明细类型:获得(0)
	 */
	public static final String MEMBER_POINTS_TYPE_01 = "0";
	
	/**
	 * 积分明细类型:消费(1)
	 */
	public static final String MEMBER_POINTS_TYPE_02 = "1";
	
	/**
	 * 积分来源（店面消费）
	 */
	public static final String MEMBER_POINTS_SOURCE_01 = "1";
	
	/**
	 * 积分 用于过期清算的：未结算(0)
	 */
	public static final String MEMBER_POINTS_CLEARING_01 = "0";

	/**
	 * 积分 用于过期清算的：已结算(1)
	 */
	public static final String MEMBER_POINTS_CLEARING_02 = "1";
	
}
