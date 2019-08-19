package com.jy.service.scm.transfer;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.history.TradeHisDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.dao.scm.transfer.TransferDao;
import com.jy.dao.scm.transfer.TransferDetailDao;
import com.jy.entity.scm.history.TradeHis;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.report.Report;
import com.jy.entity.scm.transfer.Transfer;
import com.jy.entity.scm.transfer.TransferDetail;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.product.ProductService;
import com.jy.service.system.tool.SerialNumberService;
import net.sf.json.JSONArray;

@Service("TransferService")
public class TransferServiceImp extends BaseServiceImp<Transfer> implements TransferService {
		
	@Autowired
	private TransferDao dao;
	
	@Autowired 
	private SerialNumberService serialNumberService;
	@Autowired
	private TransferDetailDao transferDetailDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private TradeHisDao tradeHisDao;
	

	@Override
	public Transfer getByTransferStatus(String id) {
		
		return dao.getByTransferStatus(id);
	}

	@Override
	public int findByCode(String code) {
//		Integer res = dao.findByCode(code);
//		if (res > 0) {
//			return true;
//		}
		return dao.findByCode(code);
	}



	@Override
	@Transactional
	public void insertTransfer(Transfer transfer,String t) {
		String code=AccountShiroUtil.getCurrentUser().getDistCode();
		String transferNo=serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_YK+code);
		transfer.setId(UuidUtil.get32UUID());
		transfer.setTransferNo(transferNo);
		transfer.setCatgory(Constant.PURENTERY_TYPE_01);
		transfer.setType(Constant.TRANSFER_TYPE_1);
		transfer.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		JSONArray jsonarray = JSONArray.fromObject(t);
		List<TransferDetail> details=(List<TransferDetail>) JSONArray.toCollection(jsonarray,TransferDetail.class);
		for (TransferDetail transferDetail : details) {
			//查询入库的商品是否存在
			List<Product> product=productDao.findProductCode(transferDetail.getCode());
			List<TransferDetail>  detail=transferDetailDao.findByCode(transferDetail.getCode(),AccountShiroUtil.getCurrentUser().getOrgId());
			if(product.size()>0 && detail.size()>0){
				transferDetail.setId(UuidUtil.get32UUID());
				transferDetail.setTransferId(transfer.getId());
				transferDetail.setTransferNo(transferNo);
				transferDetail.setNum(detail.get(0).getNum());
				transferDetail.setWeight(detail.get(0).getWeight());
				transferDetail.setOutWarehouseId(detail.get(0).getOutWarehouseId());
				transferDetail.setOutLocationId(detail.get(0).getOutLocationId());
				//调用借口改变商品状态
				Product setStatus=new Product();
				setStatus.setId(product.get(0).getId());
				setStatus.setStatus(Constant.PRODUCT_STATE_C);
				productService.updateProductState(setStatus);
			}
			
		}
		dao.insert(transfer);
		transferDetailDao.batchInsert(details);
	}
	
	@Override
	@Transactional
	public void updateTransfer(Transfer f, TransferDetail t) {
		dao.update(f);
	}
	

	@Override
	public void deleteTransfer(Transfer f) {
		dao.delete(f);
	}
	
	@Override
	@Transactional
	public Map<String, Object> del(String cheks) {
		Map<String, Object> map=new HashMap<>();
		String[] chk =cheks.split(",");
		Integer count=0;
		Integer fail=0;
		for (String string : chk) {
			Transfer transfer=new Transfer();
			transfer.setId(string);
			List<Transfer> list = dao.find(transfer);
			if(list.size()>0){
				if(list.get(0).getCreateUser().equals(AccountShiroUtil.getCurrentUser().getAccountId())){
					if (list.size() > 0 && list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_01) || list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_05)) {
						TransferDetail details=new TransferDetail();
						details.setTransferId(list.get(0).getId());
						List<TransferDetail> purenterydetails=transferDetailDao.find(details);
						for (TransferDetail transferDetail : purenterydetails) {
							//查询入库的商品是否存在
							List<Product> product=productDao.findProductCode(transferDetail.getCode());
							if(product.size()>0){
								//调用借口改变商品状态
								Product setStatus=new Product();
								setStatus.setId(product.get(0).getId());
								setStatus.setStatus(Constant.PRODUCT_STATE_B);
								productService.updateProductState(setStatus);
							}
						}
						transferDetailDao.deleteBatch(purenterydetails);
						dao.delete(list.get(0));
						count=count+1;
				    }else{
				    	fail = fail+1;
				    }
				}else{
					fail = fail+1;
				}
			}else{
				fail = fail+1;
			}
		}
		map.put("success", count.toString());
		map.put("fail", fail.toString());
		return map;
	}

	@Override
	@Transactional
	public String proAuditing(Transfer f) {
		//String userOrgId=AccountShiroUtil.getCurrentUser().getOrgId();
		// TODO 同时调用历史记录接口，插入历史记录表
		List<Transfer> list = dao.find(f);
		if (list.size() > 0 && list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)) {
			if(f.getStatus().equals(Constant.PURENTERY_STATUS_06)){
				TransferDetail details=new TransferDetail();
				details.setTransferId(list.get(0).getId());
				List<TransferDetail> purenterydetails=transferDetailDao.find(details);
				for (TransferDetail transferDetail : purenterydetails) {
					//查询入库的商品是否存在
					List<Product> product=productDao.findProductCode(transferDetail.getCode());
					if(product.size()>0){
						//商品历史记录表信息
						Product proTwo=product.get(0);
						//商品信息
						proTwo.setWarehouseId(list.get(0).getInWarehouseId());
						proTwo.setLocationId(list.get(0).getInLocationId());
						proTwo.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
						productDao.updateWarehouse(proTwo);
						//调用借口改变商品状态
						Product setStatus=new Product();
						setStatus.setId(proTwo.getId());
						setStatus.setStatus(Constant.PRODUCT_STATE_B);
						productService.updateProductState(setStatus);
						/*增加历史记录*/
						/*productDao.insertTradeHis(pro.getId(), orgId, purenteries.get(0).getLocationid());*/
						/*TradeHis his=new TradeHis();
						his.setId(UuidUtil.get32UUID());
						//条码
						his.setCode(product.get(0).getCode());
						//产品id
						his.setProductid(product.get(0).getId());
						//类型（字典）
						his.setType(Constant.SCM_HIS_TYPE_03);
						//交易订单号
						his.setTradeorder(list.get(0).getTransferNo());
						//TODO 商品历史表数据的产品数据不是很准确
						//数量
						his.setTradenum(product.get(0).getCount());
						//重量
						his.setTradeweight(product.get(0).getTotalWeight());
						//金价
						his.setTradegoldprice(product.get(0).getGoldCost());
						//基础工费
						his.setTradebasicwage(product.get(0).getWageBasic());
						//附加工费
						his.setTradeaddwage(product.get(0).getWageCw());
						//其他工费
						his.setTradeotherwage(product.get(0).getWageOw());
						//吊牌价
						his.setTradeunitprice(product.get(0).getPrice());
						//总额（吊牌价*数量）
						his.setTradetotalprice(product.get(0).getPrice());
						//实售价
						his.setTradeactureprice(product.get(0).getPriceSuggest());
						//批发价
						his.setTradewholesale(product.get(0).getPriceTrade());
						//采购成本价
						his.setTradecostprice(product.get(0).getCostPur());
						//核价成本
						his.setTradecheckprice(product.get(0).getCostChk());
						//财务成本（采购成本+税）
						his.setTradefinanceprice(product.get(0).getCostFin());
						//拨入机构id
						his.setInorgid(AccountShiroUtil.getCurrentUser().getOrgId());
						//拨入仓库id
						his.setInwarehouseid(list.get(0).getInWarehouseId());
						//拨出单位id
						his.setOutorgid(product.get(0).getOrgId());
						//拨出仓库id
						his.setOutwarehouseid(product.get(0).getWarehouseId());
						his.setCreateTime(new Date());
						his.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
						tradeHisDao.insert(his);*/
					}
				}
			}	
			f.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
			dao.check(f);
			return "";
		}
		return "审核失败";
	}

	@Override
	public Map<String, Object> view(Transfer transfer) {
		Map<String, Object> map=new HashMap<>();
		List<Transfer> list=dao.find(transfer);
		if (list.size()>0) {
			TransferDetail transferDetail = new TransferDetail();
			transferDetail.setTransferId(list.get(0).getId());
			List<TransferDetail> details=transferDetailDao.find(transferDetail);
			if(details.size()>0){
				map.put("list", list.get(0));
				map.put("details", details);
				map.put("result", "");
				return map;
			}
		}
		map.put("result", "找不到审核数据");
		return map;
	}
	
	@Override
	public Map<String, Object> viewProduct(Transfer transfer) {
		Map<String, Object> map=new HashMap<>();
		List<Transfer> list=dao.find(transfer);
		if (list.size()>0) {
			//当前操作修改
			if(transfer.getFlag()!=null && transfer.getFlag().equals(Constant.PURENTERY_STATUS_02)){
				if(list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02) || list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_03) || list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_04) || list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_06)){
					map.put("result", "该状态不支持修改");
					return map;
				}
			//当前操作审核
			}else if(transfer.getFlag()!=null && transfer.getFlag().equals(Constant.PURENTERY_STATUS_03)){
				if(!list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)){
					map.put("result", "该状态不支持审核");
					return map;
				}
			}
			TransferDetail transferDetail = new TransferDetail();
			transferDetail.setTransferId(list.get(0).getId());
			List<TransferDetail> details=transferDetailDao.findProduct(transferDetail);
			if(details.size()>0){
				map.put("list", list.get(0));
				map.put("details", details);
				map.put("result", "");
				return map;
			}
		}
		map.put("result", "找不到审核数据");
		return map;
	}

	@Override
	@Transactional
	public String editProduct(Transfer transfer, String t) {
		String result="";
		List<Transfer> list=dao.find(transfer);
		String userId=AccountShiroUtil.getCurrentUser().getAccountId();
		if (list.size()>0){
			if(list.get(0).getCreateUser().equals(userId)){
				transfer.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				JSONArray jsonarray = JSONArray.fromObject(t);
				List<TransferDetail> details=(List<TransferDetail>) JSONArray.toCollection(jsonarray,TransferDetail.class);
				List<TransferDetail> update=new ArrayList<>();
				List<TransferDetail> insert=new ArrayList<>();
				for (TransferDetail transferDetail : details) {
					//修改或添加
					if(!StringUtils.isEmpty(transferDetail.getId())){
						List<TransferDetail> listDetails=transferDetailDao.find(transferDetail);
						if(listDetails.size()>0){
							TransferDetail updateDetail=listDetails.get(0);
							updateDetail.setDescription(transferDetail.getDescription());
							update.add(updateDetail);
						}
					}else{
						//查询入库的商品是否存在
						List<Product> product=productDao.findProductCode(transferDetail.getCode());
						List<TransferDetail>  detail=transferDetailDao.findByCode(transferDetail.getCode(),AccountShiroUtil.getCurrentUser().getOrgId());
						if(product.size()>0 && detail.size()>0){
							transferDetail.setId(UuidUtil.get32UUID());
							transferDetail.setTransferId(list.get(0).getId());
							transferDetail.setTransferNo(list.get(0).getTransferNo());
							transferDetail.setNum(detail.get(0).getNum());
							transferDetail.setWeight(detail.get(0).getWeight());
							transferDetail.setOutWarehouseId(detail.get(0).getOutWarehouseId());
							transferDetail.setOutLocationId(detail.get(0).getOutLocationId());
							//调用借口改变商品状态
							Product setStatus=new Product();
							setStatus.setId(product.get(0).getId());
							setStatus.setStatus(Constant.PRODUCT_STATE_C);
							productService.updateProductState(setStatus);
							insert.add(transferDetail);
						}
					}
				}
				List<TransferDetail> delete=transferDetailDao.byDeleteBatch(details,transfer.getId());
				//批量增加
				if(!CollectionUtils.isEmpty(insert)){
					transferDetailDao.batchInsert(insert);
				}
				//批量修改
				if(!CollectionUtils.isEmpty(update)){
					transferDetailDao.updateAuditing(update);
				}
				//批量删除
				if(!CollectionUtils.isEmpty(delete)){
					for (TransferDetail transferDetail : delete) {
						//查询入库的商品是否存在
						List<Product> product=productDao.findProductCode(transferDetail.getCode());
						if(product.size()>0){
							//调用借口改变商品状态
							Product setStatus=new Product();
							setStatus.setId(product.get(0).getId());
							setStatus.setStatus(Constant.PRODUCT_STATE_B);
							productService.updateProductState(setStatus);
						}
					}
					transferDetailDao.deleteBatch(delete);
				}
				dao.update(transfer);
			}else{
				result=Constant.TRANSFER_ERROR_2;
			}
		}else{
			result=Constant.TRANSFER_ERROR_1;
		}
		return result;
	}
}
