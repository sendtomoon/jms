package com.jy.service.scm.purorder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.history.TradeHisDao;
import com.jy.dao.scm.materialin.MaterialinDao;
import com.jy.dao.scm.materialin.PurenteryDao;
import com.jy.dao.scm.materialin.PurenterydetailDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.dao.scm.purorder.OrderSplitDao;
import com.jy.dao.scm.purorder.PurOutStorageDao;
import com.jy.entity.scm.CodeVO;
import com.jy.entity.scm.history.TradeHis;
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.materialin.Purentery;
import com.jy.entity.scm.materialin.Purenterydetail;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.purorder.OrderSplit;
import com.jy.entity.scm.purorder.PurOSOrder;
import com.jy.entity.scm.purorder.PurOSOrderDetail;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Org;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.common.CommonService;
import com.jy.service.scm.materialin.MaterialinService;
import com.jy.service.scm.product.ProductService;
import com.jy.service.system.org.OrgService;
import com.jy.service.system.tool.SerialNumberService;
import net.sf.json.JSONArray;

@Service("purOutStorageService")
public class PurOutStorageServiceImpl extends BaseServiceImp<PurOSOrder> implements PurOutStorageService {
	@Autowired
	private PurOutStorageDao dao;
	
	@Autowired
	private SerialNumberService service;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MaterialinService materialinService;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private MaterialinDao materialinDao;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private TradeHisDao tradeHisDao;
	
	@Autowired
	private OrderSplitDao orderSplitDao;
	
	@Autowired
	private PurenteryDao purenteryDao;
	
	@Autowired
	private OrgService orgService;
	
	@Autowired 
	private PurenterydetailDao purenterydetailDao;
	
	@Override
	public PurOSOrderDetail findByCode(String code) {
		PurOSOrderDetail result=dao.findByCode(code);
		return result;
	}
	@Override
	public List<CodeVO> findSetPro(CodeVO vo) {
		return dao.findSetPro(vo);
	}
	
	@Override
	public List<CodeVO> findSetMatin(CodeVO vo) {
		return dao.findSetMatin(vo);
	}
	
	@Override
	@Transactional
	public void insertPurOSOrder(String myData,PurOSOrder p) throws Exception {
		String id=UuidUtil.get32UUID();
		String code=AccountShiroUtil.getCurrentUser().getDistCode();
		String outBoundNo=service.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_CK+code);
		p.setId(id);
		p.setOutBoundNo(outBoundNo);
		p.setDeleteTag(Constant.DELETE_TAG_0);
		p.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		p.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		p.setOutOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		/*Org org=new Org();
		org.setId(AccountShiroUtil.getCurrentUser().getOrgId());
		org=orgService.find(org).get(0);
		if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
			p.setOutOrgId(AccountShiroUtil.getCurrentUser().getCompany());
		}else{
			p.setOutOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		}*/
		p.setCatgory(Constant.PURENTERY_TYPE_02);
		JSONArray jsonarray = JSONArray.fromObject(myData); 
		List<PurOSOrderDetail> listPurOSOrderDetail=new ArrayList<>();
		List<PurOSOrderDetail> list = (List<PurOSOrderDetail>) JSONArray.toCollection(jsonarray, PurOSOrderDetail.class); 
		Materialin materialin=new Materialin();
		for (PurOSOrderDetail d : list) {
			d.setType(Constant.PURENTERY_TYPE_01);
    		d.setOrgid(AccountShiroUtil.getCurrentUser().getOrgId());
    		//计算成本 批发价等
			List<PurOSOrderDetail> details=matinQueryCode(d,p);
			materialin.setCode(details.get(0).getCode());
			materialin.setWarehouseId(details.get(0).getOutWarehouseId());
			materialin.setLocationId(details.get(0).getOutLocationId());
			materialin.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			List<Materialin> tin=materialinDao.find(materialin);
			materialin=tin.get(0);
			materialin.setAvailNum(details.get(0).getNum());
			materialin.setAvailWeight(details.get(0).getWeight());
			//减少库存数
			Map<String, Object> map=materialinService.toLockInventory(materialin);
			if(!map.get("code").equals(Constant.MATERIALIN_SUCCESS_KEY)){
				logger.error((String) map.get("message"));
				new RuntimeException((String) map.get("message"));
			}
			d=details.get(0);
			String did = UuidUtil.get32UUID();
			d.setId(did);
			d.setOutBoundNo(p.getOutBoundNo());
			d.setOutBoundId(p.getId());
			listPurOSOrderDetail.add(d);
		}
		dao.insertPurOSOrder(p);
		dao.batchInsert(listPurOSOrderDetail);
	}
	
	
	@Override
	@Transactional
	public void insertProstorage(String myData,PurOSOrder p) throws Exception {
		String id=UuidUtil.get32UUID();
		String code=AccountShiroUtil.getCurrentUser().getDistCode();
		String outBoundNo=service.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_CK+code);
		p.setId(id);
		p.setOutBoundNo(outBoundNo);
		p.setDeleteTag("0");
		p.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		p.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		p.setCatgory(Constant.PURENTERY_TYPE_01);
		JSONArray jsonarray = JSONArray.fromObject(myData); 
		List<PurOSOrderDetail> listPurOSOrderDetail=new ArrayList<>();
		List<PurOSOrderDetail> list = (List<PurOSOrderDetail>) JSONArray.toCollection(jsonarray, PurOSOrderDetail.class); 
		for (PurOSOrderDetail d : list) {
			d.setOrgid(AccountShiroUtil.getCurrentUser().getOrgId());
			//计算成本 批发价等
			List<PurOSOrderDetail> details=dao.proQueryCode(d);
			d=count(details, p).get(0);
			//修改商品的状态
			List<Product> product=productDao.findProductCode(d.getCode());
			Product products=product.get(0);
			String did = UuidUtil.get32UUID();
			d.setId(did);
			d.setOutBoundNo(outBoundNo);
			d.setOutBoundId(id);
			d.setOutWarehouseId(products.getWarehouseId());
			d.setOutLocationId(products.getLocationId());
			listPurOSOrderDetail.add(d);
			//调用借口改变商品状态
			Product setStatus=new Product();
			setStatus.setId(products.getId());
			setStatus.setStatus(Constant.PRODUCT_STATE_C);
			productService.updateProductState(setStatus);
		}
		dao.insertPurOSOrder(p);
		dao.batchInsert(listPurOSOrderDetail);
	}
	
	@Override
	public List<PurOSOrderDetail> matinQueryCode(PurOSOrderDetail vo,PurOSOrder osOrder) {
		vo.setOutWarehouseId(osOrder.getWarehouseid());
		//搜索类型为条码搜索
		if(vo.getType().equals(Constant.PURENTERY_TYPE_01)){
			List<PurOSOrderDetail> list=dao.matinQueryCode(vo);
			PurOSOrderDetail purOSOrderDetail=new PurOSOrderDetail();
			if(!StringUtils.isEmpty(vo.getId())){
				purOSOrderDetail.setId(vo.getId());
			}else{
				purOSOrderDetail.setId(Constant.CHARGE_TYPE_GRAM);
			}
			List<PurOSOrderDetail> detail=dao.findPurOSOrderDetail(purOSOrderDetail);
			//条码是否存在
			if(list.size()>0){
				double price=0.0;
				//批发价=（成本*系数）* 数量和重量
				if(osOrder.getBalancetype().equals(Constant.PRICE_TYPE_1)){
					price=list.get(0).getCosting()*osOrder.getRatio();
				}else{
					price=list.get(0).getPrice()*osOrder.getRatio();
				}
				Integer num=vo.getNum();
				Double weight=vo.getWeight();
				//物料按件计算成本
				if(list.get(0).getFeeType().equals(Constant.CHARGE_TYPE_PIECE)){
					//物料是否已经出过库
					if(detail.size()>0){
						num=num-detail.get(0).getNum();
						if(num!=null && num<=list.get(0).getNum() && num!=0){
							detail.get(0).setCosting((num+detail.get(0).getNum())*list.get(0).getPrice());
							detail.get(0).setPradeprice((num+detail.get(0).getNum())*price);
							detail.get(0).setNum(num+detail.get(0).getNum());
							weight=weight-detail.get(0).getWeight();
							if(weight!=null && weight<=list.get(0).getWeight() && weight!=0){
								detail.get(0).setWeight(weight+detail.get(0).getWeight());
							}
							return detail;
						}else{
							weight=weight-detail.get(0).getWeight();
							if(weight!=null && weight<=list.get(0).getWeight() && weight!=0){
								detail.get(0).setWeight(weight+detail.get(0).getWeight());
							}
							return detail;
						}
					}else{
						list.get(0).setPradeprice(list.get(0).getNum()*price);
						if(num!=null && num<=list.get(0).getNum() && num!=0){
							list.get(0).setCosting(num*list.get(0).getPrice());
							list.get(0).setPradeprice(num*price);
							list.get(0).setNum(num);
						}
						if(weight!=null && weight<=list.get(0).getWeight() && weight!=0){
							list.get(0).setWeight(weight);
						}
					}
				//物料按克计算成本
				}else{
					//物料是否已经出过库
					if(detail.size()>0){
						weight=weight-detail.get(0).getWeight();
						if(weight!=null && weight<=list.get(0).getWeight() && weight!=0){
							detail.get(0).setCosting((weight+detail.get(0).getWeight())*list.get(0).getPrice());
							detail.get(0).setPradeprice((weight+detail.get(0).getWeight())*price);
							detail.get(0).setWeight(weight+detail.get(0).getWeight());
							num=num-detail.get(0).getNum();
							if(num!=null && num<=list.get(0).getNum() && num!=0){
								detail.get(0).setNum(num+detail.get(0).getNum());
							}
							return detail;
						}else{
							num=num-detail.get(0).getNum();
							if(num!=null && num<=list.get(0).getNum() && num!=0){
								detail.get(0).setNum(num+detail.get(0).getNum());
							}
							return detail;
						}
					}else{
						list.get(0).setPradeprice(list.get(0).getWeight()*price);
						if(weight!=null && weight<=list.get(0).getWeight() && weight!=0){
							list.get(0).setCosting(weight*list.get(0).getPrice());
							list.get(0).setPradeprice(weight*price);
							list.get(0).setWeight(weight);
						}
						if(num!=null && num<=list.get(0).getNum() && num!=0){
							list.get(0).setNum(num);
						}
					}
				}
			}
			return list;
		//搜索条件为入库单号
		}else{
			List<PurOSOrderDetail> list=dao.matinCode(vo);
			
			return list;
		}
	}
	
	/*
	 * 计算成本、工费、挂签费、批发价、牌价
	 */
	public List<PurOSOrderDetail> countMatin(List<PurOSOrderDetail> list,PurOSOrder osOrder){
		Double defult=0.0;
		for (PurOSOrderDetail purOSOrderDetail : list) {
			Materialin materialin=materialinDao.queryCode(purOSOrderDetail.getCode());
			if (materialin!=null) {
				//采购成本
				if(materialin.getPurcost()==null){
					materialin.setPurcost(defult);
				}
				//销售成本
				if(materialin.getSaleprice()==null){
					materialin.setSaleprice(defult);
				}
				//成本算法（结价类型选择采购成本，则为采购成本，其他类型都是财务成本）
				if(osOrder.getRatio().equals(Constant.PRICE_TYPE_1)){
					purOSOrderDetail.setCosting(materialin.getPurcost());
				}else{
					purOSOrderDetail.setCosting(materialin.getSaleprice());
				}
			}
		}
		return list;
	}
	
	
	@Override
	public List<PurOSOrderDetail> proQueryCode(PurOSOrderDetail vo,PurOSOrder osOrder) {
		//搜索类型为条码搜索
		if(vo.getType().equals(Constant.PURENTERY_TYPE_01)){
			List<PurOSOrderDetail> list=dao.proQueryCode(vo);
			return count(list, osOrder);
		}else{
			List<PurOSOrderDetail> list=dao.productinCode(vo);
			return count(list, osOrder);
		}
	}
	
	@Override
	public List<PurOSOrderDetail> updateCode(String codes, PurOSOrder osOrder) {
		List<PurOSOrderDetail> details=new ArrayList<>();
		Account curUser = AccountShiroUtil.getCurrentUser();
		JSONArray jsonarray = JSONArray.fromObject(codes); 
		List<PurOSOrderDetail> listPurOSOrderDetail = (List<PurOSOrderDetail>) JSONArray.toCollection(jsonarray, PurOSOrderDetail.class); 
		for (PurOSOrderDetail string : listPurOSOrderDetail) {
			PurOSOrderDetail osOrderDetail=new PurOSOrderDetail();
			osOrderDetail.setCode(string.getCode());
			/*Org org=new Org();
			org.setId(curUser.getOrgId());
			org=orgService.find(org).get(0);
			if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
				osOrderDetail.setOrgid(curUser.getCompany());
			}else{
				osOrderDetail.setOrgid(curUser.getOrgId());
			}*/
			osOrderDetail.setOrgid(curUser.getOrgId());
			List<PurOSOrderDetail> list=dao.proUpdateCode(osOrderDetail);
			if(list.size()>0){
				list.get(0).setId(string.getId());
				details.add(list.get(0));
			}
		}
		return count(details, osOrder);
	}
	
	
	/*
	 * 计算成本、工费、挂签费、批发价、牌价
	 */
	public List<PurOSOrderDetail> count(List<PurOSOrderDetail> list,PurOSOrder osOrder){
		Double defult=0.0;
			for (PurOSOrderDetail purOSOrderDetail : list) {
				List<Product> product=productDao.findProductCode(purOSOrderDetail.getCode());
				if (product.size()>0) {
					//采购成本
					if(product.get(0).getCostPur()==null){
						product.get(0).setCostPur(defult);
					}
					//财务成本
					if(product.get(0).getCostFin()==null){
						product.get(0).setCostFin(defult);
					}
					//基础工费
					if(product.get(0).getWageBasic()==null){
						product.get(0).setWageBasic(defult);
					}
					//喷砂工费
					if(product.get(0).getWageSw()==null){
						product.get(0).setWageSw(defult);
					}
					//其他工费
					if(product.get(0).getWageOw()==null){
						product.get(0).setWageOw(defult);
					}
					//附加工费
					if(product.get(0).getWageAdd()==null){
						product.get(0).setWageAdd(defult);
					}
					//牌价
					if(product.get(0).getPrice()==null){
						product.get(0).setPrice(defult);
					}
					//原牌价
					if(product.get(0).getPriceOld()==null){
						product.get(0).setPriceOld(defult);
					}
					//总重量
					if(product.get(0).getTotalWeight()==null){
						product.get(0).setTotalWeight(defult);
					}
					//TODO (类型辨认不出) 工费（素金（基础工费+喷砂工费+其他工费+附加工费），镶嵌（无工费））
					purOSOrderDetail.setWage(product.get(0).getWageBasic()+product.get(0).getWageSw()+product.get(0).getWageOw()+product.get(0).getWageAdd());
					if(!StringUtils.isEmpty(osOrder.getBalancetype()) && osOrder.getRatio()!=null){
						//成本算法（结价类型选择采购成本，则为采购成本，其他类型都是财务成本）
						if(osOrder.getBalancetype().equals(Constant.PRICE_TYPE_1)){
							purOSOrderDetail.setCosting(product.get(0).getCostPur());
						}else{
							purOSOrderDetail.setCosting(product.get(0).getCostFin());
						}
						//批发价=（成本*系数）+工费（商品各工费总和）+挂签费
						//采购成本 财务成本 牌价 原牌价 总重量
						if(osOrder.getBalancetype().equals(Constant.PRICE_TYPE_1)){
							purOSOrderDetail.setPradeprice((product.get(0).getCostPur()*osOrder.getRatio())+purOSOrderDetail.getWage());
						}else if(osOrder.getBalancetype().equals(Constant.PRICE_TYPE_2)){
							purOSOrderDetail.setPradeprice((product.get(0).getCostFin()*osOrder.getRatio())+purOSOrderDetail.getWage());
						}else if(osOrder.getBalancetype().equals(Constant.PRICE_TYPE_3)){
							purOSOrderDetail.setPradeprice((product.get(0).getPrice()*osOrder.getRatio())+purOSOrderDetail.getWage());
						}else if(osOrder.getBalancetype().equals(Constant.PRICE_TYPE_4)){
							purOSOrderDetail.setPradeprice((product.get(0).getPriceOld()*osOrder.getRatio())+purOSOrderDetail.getWage());
						}else if(osOrder.getBalancetype().equals(Constant.PRICE_TYPE_5)){
							purOSOrderDetail.setPradeprice((product.get(0).getTotalWeight()*osOrder.getRatio())+purOSOrderDetail.getWage());
						}
						DecimalFormat df = new DecimalFormat("#.0000");
						purOSOrderDetail.setPradeprice(Double.parseDouble(df.format(purOSOrderDetail.getPradeprice())));
					}
					//牌价
					purOSOrderDetail.setPrice(product.get(0).getPrice());
					//挂签费
					purOSOrderDetail.setTageprice(defult);
				
			}
		}
		return list;
	}
	
	@Override
	public Map<String, Object> findPurOSOrder(PurOSOrder p) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PurOSOrder> purOSOrder=dao.findPurOSOrder(p);
		if(purOSOrder.size()>0){
			//当前操作修改
			if(p.getType()!=null && p.getType().equals(Constant.PURENTERY_STATUS_02)){
				if(purOSOrder.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02) || purOSOrder.get(0).getStatus().equals(Constant.PURENTERY_STATUS_03) || purOSOrder.get(0).getStatus().equals(Constant.PURENTERY_STATUS_06)){
					result.put("result", "该状态不支持修改");
					return result;
				}
			//当前操作审核
			}else if(p.getType()!=null && !p.getType().equals(Constant.PURENTERY_STATUS_02)){
				if(!purOSOrder.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)){
					result.put("result", "该状态不支持审核");
					return result;
				}
			//TODO 当前操作删除
			}
			/*else if(p.getType()!=null && p.getType().equals(Constant.PURENTERY_STATUS_03)){
				if(!purOSOrder.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)){
					result.put("result", "该状态不支持审核");
					return result;
				}
			}*/
			result.put("purOSOrder", purOSOrder.get(0));
			PurOSOrderDetail detail=new PurOSOrderDetail();
			detail.setOutBoundId(purOSOrder.get(0).getId());
			List<PurOSOrderDetail> list=dao.findPurOSOrderDetail(detail);
			if(list.size()>0){
				result.put("detail", list);
			}
		}
		return result;
	}
	
	@Override
	@Transactional
	public void updateProOSOrder(String myData,PurOSOrder p) throws Exception{
		p.setUpdateTime(new Date());
		p.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		p.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		dao.updatePurOSOrder(p);
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List<PurOSOrderDetail> list = (List<PurOSOrderDetail>) JSONArray.toCollection(jsonarray, PurOSOrderDetail.class);  
        List<PurOSOrderDetail> listPurOSOrderDetail=new ArrayList<>();
    	for (PurOSOrderDetail d : list) {
    		//商品是新增还是修改
    		if(d.getId()==null || d.getId().length()<0 || d.getId().equals("")){
    			d.setOrgid(AccountShiroUtil.getCurrentUser().getOrgId());
    			//计算成本 批发价等
    			List<PurOSOrderDetail> details=dao.proQueryCode(d);
    			if(details.size()>0){
    				PurOSOrderDetail datils=count(details, p).get(0);
    				//修改商品的状态
    				List<Product> product=productDao.findProductCode(d.getCode());
    				Product products=product.get(0);
    				String did = UuidUtil.get32UUID();
    				datils.setId(did);
    				datils.setOutBoundNo(p.getOutBoundNo());
    				datils.setOutBoundId(p.getId());
    				datils.setOutWarehouseId(products.getWarehouseId());
    				datils.setOutLocationId(products.getLocationId());
    				listPurOSOrderDetail.add(datils);
    				//调用借口改变商品状态
    				Product setStatus=new Product();
    				setStatus.setId(products.getId());
    				setStatus.setStatus(Constant.PRODUCT_STATE_C);
    				productService.updateProductState(setStatus);
    			}
    		}else{
    			List<PurOSOrderDetail> listDetail=new ArrayList<>();
    			listDetail=dao.findPurOSOrderDetail(d);
    			listDetail=count(listDetail, p);
    			//批量修改
    	    	if(!CollectionUtils.isEmpty(listDetail)){
    	    		dao.updatePurOSOrderDetail(listDetail);
    	    	}
    		}
		}
    	//批量新增
    	if(!CollectionUtils.isEmpty(listPurOSOrderDetail)){    		
    		dao.batchInsert(listPurOSOrderDetail);
    	}
    	//将删除的商品改状态
    	list=dao.byDeleteBatch(list,p.getOutBoundNo());
    	for (PurOSOrderDetail purOSOrderDetail : list) {
    		//修改商品的状态
			List<Product> product=productDao.findProductCode(purOSOrderDetail.getCode());
			Product products=product.get(0);
			//调用借口改变商品状态
			Product setStatus=new Product();
			setStatus.setId(products.getId());
			setStatus.setStatus(Constant.PRODUCT_STATE_B);
			productService.updateProductState(setStatus);
		}
    	//批量删除
    	if(!CollectionUtils.isEmpty(list)){
    		dao.deleteBat(list);
    	}
	}
	
	@Override
	@Transactional
	public void updatePurOSOrder(String myData,PurOSOrder p) throws Exception{
		p.setUpdateTime(new Date());
		p.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		p.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List<PurOSOrderDetail> list = (List<PurOSOrderDetail>) JSONArray.toCollection(jsonarray, PurOSOrderDetail.class);  
        List<PurOSOrderDetail> listPurOSOrderDetail=new ArrayList<>();
    	for (PurOSOrderDetail d : list) {
    		d.setType(Constant.PURENTERY_TYPE_01);
    		d.setOrgid(AccountShiroUtil.getCurrentUser().getOrgId());
    		//计算成本 批发价等
			List<PurOSOrderDetail> details=matinQueryCode(d,p);
			Materialin materialin=new Materialin();
			materialin.setCode(d.getCode());
			materialin.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			materialin.setWarehouseId(p.getWarehouseid());
			List<Materialin> tin=materialinDao.find(materialin);
			if(details.size()>0 && tin.size()>0){
				Materialin mater=tin.get(0);
	    		//商品是新增还是修改
	    		if(StringUtils.isEmpty(d.getId())){
	    			String did = UuidUtil.get32UUID();
    				d.setId(did);
    				d.setName(details.get(0).getName());
    				d.setOutBoundNo(p.getOutBoundNo());
    				d.setOutBoundId(p.getId());
    				d.setOutWarehouseId(mater.getWarehouseId());
    				d.setOutLocationId(mater.getLocationId());
    				d.setPrice(mater.getPrice());
    				//减少库存数
    				mater.setAvailNum(d.getNum());
    				mater.setAvailWeight(d.getWeight());
    				Map<String, Object> map=materialinService.toLockInventory(mater);
    				if(!map.get("code").equals(Constant.MATERIALIN_SUCCESS_KEY)){
    					logger.error((String) map.get("message"));
    					new RuntimeException((String) map.get("message"));
    				}
    				//增加
    				listPurOSOrderDetail.add(d);
	    		}else if(!StringUtils.isEmpty(d.getId())){
	    			List<PurOSOrderDetail> detail=dao.findDetail(d.getId());
	    			//减少库存数
    				mater.setAvailNum(details.get(0).getNum()-detail.get(0).getNum());
    				mater.setAvailWeight(details.get(0).getWeight()-detail.get(0).getWeight());
    				Map<String, Object> map=materialinService.toLockInventory(mater);	
    				if(!map.get("code").equals(Constant.MATERIALIN_SUCCESS_KEY)){
    					logger.error((String) map.get("message"));
    					new RuntimeException((String) map.get("message"));
    				}
	    			//修改
	    	    	dao.updatePurOSOrderDetail(details);
	    		}
			}
		}
    	dao.updatePurOSOrder(p);
    	//批量新增
    	if(!CollectionUtils.isEmpty(listPurOSOrderDetail)){    		
    		dao.batchInsert(listPurOSOrderDetail);
    	}
    	//将删除的商品改状态    批量删除
    	list=dao.byDeleteBatch(list,p.getOutBoundNo());
    	if(!CollectionUtils.isEmpty(list)){
    		for (PurOSOrderDetail purOSOrderDetail : list) {
				Materialin materialin=new Materialin();
				materialin.setCode(purOSOrderDetail.getCode());
				materialin.setWarehouseId(purOSOrderDetail.getOutWarehouseId());
				materialin.setLocationId(purOSOrderDetail.getOutLocationId());
				materialin.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
				List<Materialin> tin=materialinDao.find(materialin);
				materialin=tin.get(0);
				materialin.setAvailNum(purOSOrderDetail.getNum());
				materialin.setAvailWeight(purOSOrderDetail.getWeight());
				Map<String, Object> map=materialinService.recoverLockedInventory(materialin);
				if(!map.get("code").equals(Constant.MATERIALIN_SUCCESS_KEY)){
					logger.error((String) map.get("message"));
					new RuntimeException((String) map.get("message"));
				}
			}
    		dao.deleteBat(list);
    	}
	}
	
	@Override
	@Transactional
	public boolean check(PurOSOrder p) {
		List<PurOSOrder> purOSOrder=dao.findPurOSOrder(p);
		if(purOSOrder.size()>0 && purOSOrder.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02) && p.getStatus().equals(Constant.PURENTERY_STATUS_03)){
			p.setCheckTime(new Date());
			p.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
			dao.updateTag(p);
			PurOSOrderDetail purOSOrderDetail=new PurOSOrderDetail();
			purOSOrderDetail.setOutBoundId(purOSOrder.get(0).getId());
			List<PurOSOrderDetail> details=dao.findPurOSOrderDetail(purOSOrderDetail);
			//入库单号
			String code=AccountShiroUtil.getCurrentUser().getDistCode();
			String enteryno=service.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_RK+code);
			//入库单详情
			List<Purenterydetail> purenterydetails=new ArrayList<>();
			DecimalFormat df = new DecimalFormat("#.0000");
			Double prices=0.0,purcosts=0.0,saleprices=0.0,finacosts=0.0,diffweights=0.0,checkcost=0.0;
			for (PurOSOrderDetail detail : details) {
				List<PurOSOrderDetail> listDetail=new ArrayList<>();
    			listDetail=dao.findPurOSOrderDetail(detail);
    			listDetail=count(listDetail, p);
    			//批量修改
    	    	if(!CollectionUtils.isEmpty(listDetail)){
    	    		dao.updatePurOSOrderDetail(listDetail);
    	    	}
    	    	detail=listDetail.get(0);
				//修改商品的状态
				List<Product> product=productDao.findProductCode(detail.getCode());
				Product products=product.get(0);
				products.setOrgId(purOSOrder.get(0).getInOrgId());
				products.setWarehouseId(Constant.WAREHOUSE_DEFAULT);
				products.setLocationId(Constant.WAREHOUSE_DEFAULT);
				products.setCostPur(detail.getPradeprice());
				products.setCostFin(detail.getPradeprice());
				products.setCostChk(detail.getPradeprice());
				productDao.updateProductInfo(products);
				/*nvl((p.FGOLDWEIGHT-p.TOTALWEIGHT),0)  as DIFFWEIGHT,nvl(p.price,0) as PRICE,nvl(p.COSTPUR,0) as PURCOST,nvl(p.COSTFIN,0) as FINACOST*/
				//下级入库单增加
				Purenterydetail detailPur=new Purenterydetail();
				detailPur.setId(UuidUtil.get32UUID());
				detailPur.setEnteryno(enteryno);
				detailPur.setCode(products.getCode());
				detailPur.setNum(products.getCount());
				detailPur.setWeight(products.getTotalWeight());
				detailPur.setPurcost(products.getCostPur());
				detailPur.setFinacost(products.getCostFin());
				detailPur.setPrice(products.getPrice());
				detailPur.setSaleprice(0.0);
				detailPur.setCheckcost(products.getCostChk());
				if(!StringUtils.isEmpty(products.getfGoldWeight()) && products.getTotalWeight()!=null){
					detailPur.setDiffweight(Double.parseDouble(products.getfGoldWeight())-products.getTotalWeight());
				}else{
					detailPur.setDiffweight(0.0);
				}
				detailPur.setType(Constant.PURENTERY_TYPE_01);
				purenterydetails.add(detailPur);
				if(detailPur.getPrice()!=null){
					prices=Double.parseDouble(df.format(prices+detailPur.getPrice()));
				}
				purcosts=Double.parseDouble(df.format(purcosts+detailPur.getPurcost()));
				saleprices=Double.parseDouble(df.format(saleprices+detailPur.getSaleprice()));
				finacosts=Double.parseDouble(df.format(finacosts+detailPur.getFinacost()));
				diffweights=Double.parseDouble(df.format(diffweights+detailPur.getDiffweight()));
				checkcost=Double.parseDouble(df.format(checkcost+detailPur.getCheckcost()));
				/*增加历史记录*/
				/*productDao.insertTradeHis(pro.getId(), orgId, purenteries.get(0).getLocationid());*/
				TradeHis his=new TradeHis();
				his.setId(UuidUtil.get32UUID());
				//条码
				his.setCode(product.get(0).getCode());
				//产品id
				his.setProductid(product.get(0).getId());
				//类型（字典）
				his.setType(Constant.SCM_HIS_TYPE_05);
				//交易订单号
				his.setTradeorder(purOSOrder.get(0).getOutBoundNo());
				//TODO 商品历史表数据的产品数据不是很准确
				//数量
				his.setTradenum(detail.getNum());
				//重量
				his.setTradeweight(detail.getWeight());
				//金价
				his.setTradegoldprice(product.get(0).getGoldCost());
				//基础工费
				his.setTradebasicwage(product.get(0).getWageBasic());
				//附加工费
				his.setTradeaddwage(product.get(0).getWageCw());
				//其他工费
				his.setTradeotherwage(product.get(0).getWageOw());
				//吊牌价
				his.setTradeunitprice(detail.getPrice());
				//总额（吊牌价*数量）
				his.setTradetotalprice(detail.getPrice()*detail.getNum());
				//实售价
				his.setTradeactureprice(detail.getPrice());
				//批发价
				his.setTradewholesale(detail.getPradeprice());
				//采购成本价
				his.setTradecostprice(product.get(0).getCostPur());
				//核价成本
				his.setTradecheckprice(product.get(0).getCostChk());
				//财务成本（采购成本+税）
				his.setTradefinanceprice(product.get(0).getCostFin());
				//拨入机构id
				his.setInorgid(purOSOrder.get(0).getInOrgId());
				//拨入仓库id
				his.setInwarehouseid(Constant.WAREHOUSE_DEFAULT);
				//拨出单位id
				his.setOutorgid(purOSOrder.get(0).getOutOrgId());
				//拨出仓库id
				his.setOutwarehouseid(detail.getOutWarehouseId());
				his.setCreateTime(new Date());
				his.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
				tradeHisDao.insert(his);
			}
			Purentery purentery=new Purentery();
			purentery.setId(UuidUtil.get32UUID());
			purentery.setEnteryno(enteryno);
			purentery.setStatus(Constant.PRODUCT_STATE_0);
			purentery.setType(Constant.PURENTERY_TYPE_01);
			purentery.setPurno(purOSOrder.get(0).getOrderNum());
			purentery.setTotalnum(details.size());
			purentery.setTotalcount(details.size());
			purentery.setPurcost(purcosts);
			purentery.setCheckcost(checkcost);
			purentery.setFinacost(finacosts);
			purentery.setTotalprice(prices);
			purentery.setDiffweight(diffweights);
			purentery.setWarehouseid(Constant.WAREHOUSE_DEFAULT);
			purentery.setLocationid(Constant.WAREHOUSE_DEFAULT);
			purentery.setOrgId(purOSOrder.get(0).getInOrgId());
			purentery.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			purentery.setCreateorgid(AccountShiroUtil.getCurrentUser().getOrgId());
			purenteryDao.insert(purentery);
			purenterydetailDao.batchInsert(purenterydetails);
			return true;
		}else{
			p.setCheckTime(new Date());
			p.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
			dao.updateTag(p);
			return true;
		}
	}
	
	@Override
	@Transactional
	public boolean checkPurOutStorage(PurOSOrder p) throws Exception {
		List<PurOSOrder> purOSOrder=dao.findPurOSOrder(p);
		if(purOSOrder.size()>0 && purOSOrder.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)){
			p.setCheckTime(new Date());
			p.setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
			if(p.getStatus().equals(Constant.PURENTERY_STATUS_03)){
				PurOSOrderDetail d=new PurOSOrderDetail();
				d.setOutBoundId(purOSOrder.get(0).getId());
				List<PurOSOrderDetail> details=dao.findPurOSOrderDetail(d);
				for (PurOSOrderDetail purOSOrderDetail : details) {
					Materialin materialin=new Materialin();
					materialin.setCode(purOSOrderDetail.getCode());
					materialin.setWarehouseId(purOSOrderDetail.getOutWarehouseId());
					materialin.setLocationId(purOSOrderDetail.getOutLocationId());
					materialin.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
					List<Materialin> materialins=materialinDao.find(materialin);
					materialin=materialins.get(0);
					materialin.setNum(purOSOrderDetail.getNum());
					materialin.setWeight(purOSOrderDetail.getWeight());
					Map<String, Object> map=materialinService.reduceInventory(materialin);
					if(!map.get("code").equals(Constant.MATERIALIN_SUCCESS_KEY)){
						logger.error((String) map.get("message"));
						new RuntimeException((String) map.get("message"));
					}
				}
			}
			dao.updateTag(p);
			return true;
		}
		return false;
	}
	
	public Map<String, Object> deletePurOutStorage(String cheks) throws Exception{
		Map<String, Object> mapHap=new HashMap<>();
		String[] chk =cheks.split(",");
		Integer count=0;
		Integer fail=0;
		for (String string : chk) {
			PurOSOrder p=new PurOSOrder();
			p.setId(string);
			List<PurOSOrder> osOrders=dao.findPurOSOrder(p);
			if(osOrders.size()>0){
				if(osOrders.get(0).getStatus().equals(Constant.PURENTERY_STATUS_01) || osOrders.get(0).getStatus().equals(Constant.PURENTERY_STATUS_05)){
					PurOSOrderDetail d=new PurOSOrderDetail();
					d.setOutBoundId(osOrders.get(0).getId());
					List<PurOSOrderDetail> details=dao.findPurOSOrderDetail(d);
					for (PurOSOrderDetail purOSOrderDetail : details) {
						Materialin materialin=new Materialin();
						materialin.setCode(purOSOrderDetail.getCode());
						materialin.setWarehouseId(purOSOrderDetail.getOutWarehouseId());
						materialin.setLocationId(purOSOrderDetail.getOutLocationId());
						materialin.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
						List<Materialin> tin=materialinDao.find(materialin);
						materialin=tin.get(0);
						materialin.setAvailNum(purOSOrderDetail.getNum());
						materialin.setAvailWeight(purOSOrderDetail.getWeight());
						Map<String, Object> map=materialinService.recoverLockedInventory(materialin);
						if(!map.get("code").equals(Constant.MATERIALIN_SUCCESS_KEY)){
							logger.error((String) map.get("message"));
							new RuntimeException((String) map.get("message"));
						}
					}
					if(details.size()>0){
						dao.deleteBat(details);
					}
					dao.delete(p);
					count=count+1;
			    }else{
			    	fail = fail+1;
			    }
			}else{
				fail = fail+1;
			}
		}
		mapHap.put("success", count.toString());
		mapHap.put("fail", fail.toString());
		return mapHap;
	}
	
	@Override
	@Transactional
	public Map<String, Object> delBatchProStorage(String cheks) {
		Map<String, Object> map=new HashMap<>();
		String[] chk =cheks.split(",");
		Integer count=0;
		Integer fail=0;
		List <String> list = new ArrayList<String>();
	
		for (String string : chk) {
			PurOSOrder p=new PurOSOrder();
			p.setId(string);
			List<PurOSOrder> osOrders=dao.findPurOSOrder(p);
			if(osOrders.size()>0){
				if(osOrders.get(0).getStatus().equals(Constant.PURENTERY_STATUS_01) || osOrders.get(0).getStatus().equals(Constant.PURENTERY_STATUS_05)){
					PurOSOrderDetail d=new PurOSOrderDetail();
					d.setOutBoundId(osOrders.get(0).getId());
					List<PurOSOrderDetail> details=dao.findPurOSOrderDetail(d);
					for (PurOSOrderDetail purOSOrderDetail : details) {
						List<Product> product=productDao.findProductCode(purOSOrderDetail.getCode());
						Product products=product.get(0);
						list.add(products.getId());
						//调用借口改变商品状态
						Product setStatus=new Product();
						setStatus.setId(products.getId());
						setStatus.setStatus(Constant.PRODUCT_STATE_B);
						productService.updateProductState(setStatus);
					}
					if(details.size()>0){
						dao.deleteBat(details);
					}
					dao.delete(p);
					count=count+1;
			    }else{
			    	fail = fail+1;
			    }
			}else{
				fail = fail+1;
			}
		}
		orderSplitDao.deleteBatchProductId(list);
		map.put("success", count.toString());
		map.put("fail", fail.toString());
		return map;
	}
	
	
	@Override
	@Transactional
	public boolean updateStatus(PurOSOrder p) {
		List<PurOSOrder> osOrders=dao.findPurOSOrder(p);
		if(osOrders.size()>0){
			if(osOrders.get(0).getStatus().equals(Constant.PURENTERY_STATUS_01) || osOrders.get(0).getStatus().equals(Constant.PURENTERY_STATUS_05) || osOrders.get(0).getStatus().equals(Constant.PURENTERY_STATUS_04)){
				PurOSOrderDetail d=new PurOSOrderDetail();
				d.setOutBoundId(osOrders.get(0).getId());
				List<PurOSOrderDetail> details=dao.findPurOSOrderDetail(d);
				for (PurOSOrderDetail purOSOrderDetail : details) {
					List<Product> product=productDao.findProductCode(purOSOrderDetail.getCode());
					Product products=product.get(0);
					//调用借口改变商品状态
					Product setStatus=new Product();
					setStatus.setId(products.getId());
					setStatus.setStatus(Constant.PRODUCT_STATE_B);
					productService.updateProductState(setStatus);
				}
				if(details.size()>0){
					dao.deleteBat(details);
				}
				dao.delete(p);
				return true;
		    }
		}
		return false;
	}
	
	@Override 
	@Transactional
	public void updateTag(PurOSOrder p) throws Exception{
		PurOSOrderDetail detail=new PurOSOrderDetail();
		List<PurOSOrder> findPurOSOrder=dao.findPurOSOrder(p);
		PurOSOrder purOSOrder=findPurOSOrder.get(0);
		detail.setOutBoundId(p.getId());
		List<PurOSOrderDetail> list=dao.findPurOSOrderDetail(detail);
		Map<String,Object> 	map=commonService.getProducts(list.get(0).getCode());
		Materialin materialin=new Materialin();
		Materialin mater=new Materialin();
		if(map.get("type").toString().equals(Constant.TABLE_TYPE_PRODUCT)){
			List<Product> product=productDao.findProductCode(list.get(0).getCode());
			Product products=product.get(0);
			products.setWarehouseId(Constant.WAREHOUSE_DEFAULT);
			products.setLocationId(Constant.WAREHOUSE_DEFAULT);
			products.setOrgId(purOSOrder.getInOrgId());
			productDao.updateProductInfo(products);
		}else if(map.get("type").toString().equals(Constant.TABLE_TYPE_MATERIAL)){
			materialin=materialinDao.queryCode(list.get(0).getCode());
			List<Materialin> tin=materialinDao.find(materialin);
			mater=tin.get(0);
			materialin.setWarehouseId(Constant.WAREHOUSE_DEFAULT);
			materialin.setLocationId(Constant.WAREHOUSE_DEFAULT);
			materialin.setOrgId(purOSOrder.getInOrgId());
			materialinService.reduceInventory(mater);
			materialinService.recoverLockedInventory(mater);
		} 
		dao.updateTag(p);
	}
	@Override
	public void goodsPurOSOrder(Map<String,Object> map)throws Exception {
		/*新增主表*/
		PurOSOrder p=new PurOSOrder();
		String id=UuidUtil.get32UUID();
		String code=AccountShiroUtil.getCurrentUser().getDistCode();
		String outBoundNo=service.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_CK+code);
		p.setId(id);
		p.setOutBoundNo(outBoundNo);
		p.setDeleteTag(Constant.DELETE_TAG_0);
		p.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		p.setOrderNum(map.get("orderNo").toString());
		p.setInOrgId(map.get("joinOrg").toString());
		p.setOutOrgId(map.get("outOrg").toString());
		p.setOrgId(map.get("outOrg").toString());
		p.setStatus(Constant.PURENTERY_STATUS_01);
		p.setType(Constant.SCM_OUTBOUND_TYPE_1);
		p.setCatgory(Constant.PURENTERY_TYPE_01);
		dao.insertPurOSOrder(p);
		/*新增详表*/
		List<OrderSplit> list=(List<OrderSplit>)map.get("list");
		List<PurOSOrderDetail> listPurOSOrderDetail=new ArrayList<>();
		for (OrderSplit orderSplit : list) {
//			List<CodeVO> vo=(List<CodeVO>) dao.findByCode(orderSplit.getProductId());
//			Map<String,Object> 	m=commonService.getProducts(vo.get(0).getCode());
//			if(map.get("type").toString().equals(Constant.TABLE_TYPE_PRODUCT)){
				Product pro =new Product();
				pro.setId(orderSplit.getProductId());
				pro=productDao.find(pro).get(0);
				PurOSOrderDetail d=new PurOSOrderDetail();
				String did = UuidUtil.get32UUID();
				d.setId(did);
				d.setOutBoundNo(outBoundNo);
				d.setOutBoundId(id);
				d.setCode(pro.getCode());
				d.setName(pro.getName());
				d.setOrgid(pro.getOrgId());
				d.setOutWarehouseId(pro.getWarehouseId());
				d.setOutLocationId(pro.getLocationId());
				d.setNum(pro.getCount());
				d.setWeight(pro.getTotalWeight());
				listPurOSOrderDetail.add(d);
//			}
		}
		dao.batchInsert(listPurOSOrderDetail);
		/*
				PurOSOrderDetail d=new PurOSOrderDetail();
				List<Product> product=productDao.find(id);
				Product products=product.get(0);
				String did = UuidUtil.get32UUID();
				d.setId(did);
				d.setOutBoundNo(outBoundNo);
				d.setOutBoundId(id);
				d.setCode(products.getCode());
				d.setName(products.getName());
				d.setOutWarehouseId(products.getWarehouseId());
				d.setOutLocationId(products.getLocationId());
				*/
//				materialin=materialinDao.queryCode(code2);nm    
//				mater=materialinDao.getMaterialinByConditions(materialin);
//				materialinService.toLockInventory(mater);
	}
	@Override
	public List<PurOSOrder> selectInOrgId(PurOSOrder p) {
		List<PurOSOrder> list=dao.selectInOrgId(p);
		return list;
	}
	@Override
	public List<PurOSOrderDetail> findWarehouse(PurOSOrderDetail vo) {
		return dao.findWarehouse(vo);
	}
	
	
	
}  
