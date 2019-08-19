package com.jy.service.scm.materialin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.history.TradeHisDao;
import com.jy.dao.scm.materialin.MaterialinDao;
import com.jy.dao.scm.materialin.PurenteryDao;
import com.jy.dao.scm.materialin.PurenterydetailDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.dao.scm.warehouse.WarehouseDao;
import com.jy.dao.scm.warehouse.WarehouseLocationDao;
import com.jy.entity.base.SelectData;
import com.jy.entity.scm.history.TradeHis;
import com.jy.entity.scm.materialin.Materialin;
import com.jy.entity.scm.materialin.Purentery;
import com.jy.entity.scm.materialin.Purenterydetail;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.report.Report;
import com.jy.entity.scm.warehouse.WarehouseLocation;
import com.jy.entity.system.org.Org;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.product.ProductService;
import com.jy.service.system.org.OrgService;
import com.jy.service.system.tool.SerialNumberService;
import net.sf.json.JSONArray;

@Service
public class PurenteryServiceImpl extends BaseServiceImp<Purentery> implements PurenteryService {
	@Autowired
	private MaterialinDao materialinDao;
	
	@Autowired
	private PurenterydetailDao purenterydetailDao;
	
	@Autowired
	private PurenteryDao purenteryDao;
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private TradeHisDao tradeHisDao;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MaterialinService materialinService;
	
	@Autowired
	private WarehouseLocationDao warehouseLocationDao;
	
	@Autowired
	private OrgService orgService;
	
	@Override
	@Transactional
	public void insertMaterialin(String myData,Purentery purentery) {
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List list = (List)JSONArray.toCollection(jsonarray, Purenterydetail.class);  
        Iterator it = list.iterator();
		List<Purenterydetail> purenterydetails=new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.0000");
		Double prices=0.0,purcosts=0.0,saleprices=0.0,finacosts=0.0,diffweights=0.0;
		Integer totalcount=0;
		String code=AccountShiroUtil.getCurrentUser().getDistCode();
		String enteryno=serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_RK+code);
		Set<String> set=new HashSet<>(); 
		while(it.hasNext()){  
        	Purenterydetail purenterydetail = (Purenterydetail)it.next(); 
        	purenterydetail.setId(UuidUtil.get32UUID());
			purenterydetail.setEnteryno(enteryno);
			purenterydetails.add(purenterydetail);
			prices=Double.parseDouble(df.format(prices+purenterydetail.getPrice()));
			purcosts=Double.parseDouble(df.format(purcosts+purenterydetail.getPurcost()));
			saleprices=Double.parseDouble(df.format(saleprices+purenterydetail.getSaleprice()));
			finacosts=Double.parseDouble(df.format(finacosts+purenterydetail.getFinacost()));
			diffweights=Double.parseDouble(df.format(diffweights+purenterydetail.getDiffweight()));
			Integer findCode = materialinDao.findByCode(purenterydetail.getCode());
			if (findCode > 0) {
				purenterydetail.setType(Constant.PURENTERY_TYPE_02);
				//按件（只添加数量），按克（只添加数量）
				/*if(purenterydetail.getFeeType().equals(Constant.CHARGE_TYPE_GRAM)){
					purenterydetail.setWeight(purenterydetail.getTypeWeight());
					purenterydetail.setNum(0);
				}else if(purenterydetail.getFeeType().equals(Constant.CHARGE_TYPE_PIECE)){
					purenterydetail.setWeight(0.0);
					purenterydetail.setNum(purenterydetail.getTypeWeight().intValue());
				}*/
				totalcount=totalcount+1;
				set.add(purenterydetail.getCode());
			}
		}
		purentery.setId(UuidUtil.get32UUID());
		purentery.setEnteryno(enteryno);
		purentery.setType(Constant.PURENTERY_TYPE_02);
		purentery.setTotalnum(set.size());
		purentery.setTotalcount(totalcount);
		purentery.setPurcost(purcosts);
		purentery.setCheckcost(0.0);
		purentery.setFinacost(finacosts);
		purentery.setTotalprice(prices);
		purentery.setDiffweight(diffweights);
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		purentery.setOrgId(orgId);
		purentery.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		purentery.setCreateorgid(AccountShiroUtil.getCurrentUser().getOrgId());
		List<SelectData> location=warehouseLocationDao.findWarehouseLocationAll(purentery.getWarehouseid());
		if(location.size()>0){
			purentery.setLocationid(location.get(0).getKey());
		}
		purenteryDao.insert(purentery);
		purenterydetailDao.batchInsert(purenterydetails);
	}
	
	@Override
	@Transactional
	public void insertProductin(String myData,Purentery purentery) {
        //Iterator it = list.iterator();
		List<Purenterydetail> purenterydetails=new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.0000");
		Double prices=0.0,purcosts=0.0,saleprices=0.0,finacosts=0.0,diffweights=0.0,checkcost=0.0;
		Integer totalcount=0;
		String code=AccountShiroUtil.getCurrentUser().getDistCode();
		String enteryno=serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_RK+code);
		//Set<String> set=new HashSet<>(); 
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List<Purenterydetail> list = (List<Purenterydetail>)JSONArray.toCollection(jsonarray, Purenterydetail.class); 
		for (Purenterydetail purenterydetail : list) {
        	//Purenterydetail purenterydetail = (Purenterydetail)it.next();  
        	Purentery purentery2=new Purentery();
        	/*Org org=new Org();
			org.setId(AccountShiroUtil.getCurrentUser().getOrgId());
			org=orgService.find(org).get(0);
			if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
				purentery2.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
			}else{
				purentery2.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
			}*/
        	purentery2.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
        	purentery2.setCode(purenterydetail.getCode());
    		List<Purenterydetail> details=purenterydetailDao.findProductCode(purentery2);
    		purenterydetail=details.get(0);
        	purenterydetail.setId(UuidUtil.get32UUID());
			purenterydetail.setEnteryno(enteryno);
			purenterydetails.add(purenterydetail);
			prices=Double.parseDouble(df.format(prices+purenterydetail.getPrice()));
			purcosts=Double.parseDouble(df.format(purcosts+purenterydetail.getPurcost()));
			saleprices=Double.parseDouble(df.format(saleprices+purenterydetail.getSaleprice()));
			finacosts=Double.parseDouble(df.format(finacosts+purenterydetail.getFinacost()));
			diffweights=Double.parseDouble(df.format(diffweights+purenterydetail.getDiffweight()));
			checkcost=Double.parseDouble(df.format(checkcost+purenterydetail.getCheckcost()));
			Integer productCode = materialinDao.findProductCode(purenterydetail.getCode());
			if(productCode>0){
				purenterydetail.setType(Constant.PURENTERY_TYPE_01);
				purenterydetail.setWeight(purenterydetail.getWeight());
				purenterydetail.setNum(purenterydetail.getNum());
				totalcount=totalcount+1;
				//set.add(purenterydetail.getCode());
			}
		}
		purentery.setId(UuidUtil.get32UUID());
		purentery.setEnteryno(enteryno);
		purentery.setType(Constant.PURENTERY_TYPE_01);
		purentery.setTotalnum(totalcount);
		purentery.setTotalcount(totalcount);
		purentery.setPurcost(purcosts);
		purentery.setCheckcost(checkcost);
		purentery.setFinacost(finacosts);
		purentery.setTotalprice(prices);
		purentery.setDiffweight(diffweights);
		purentery.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		purentery.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		purentery.setCreateorgid(AccountShiroUtil.getCurrentUser().getOrgId());
		purenteryDao.insert(purentery);
		purenterydetailDao.batchInsert(purenterydetails);
	}

	@Override
	public Map<String, Object> view(Purentery purentery) {
		List<Purentery> purenteries=purenteryDao.find(purentery);
		Map<String, Object> map=new HashMap<>();
		if (purenteries.size()>0) {
			//当前操作修改
			if(purentery.getType()!=null && purentery.getType().equals(Constant.PURENTERY_STATUS_02)){
				if(purenteries.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02) || purenteries.get(0).getStatus().equals(Constant.PURENTERY_STATUS_06) || purenteries.get(0).getStatus().equals(Constant.PURENTERY_STATUS_04) || purenteries.get(0).getStatus().equals(Constant.PURENTERY_STATUS_03)){
					map.put("result", "该状态不支持修改");
					return map;
				}
			//当前操作审核
			}else if(purentery.getType()!=null && purentery.getType().equals(Constant.PURENTERY_STATUS_03)){
				if(!purenteries.get(0).getStatus().equals(Constant.PURENTERY_STATUS_02)){
					map.put("result", "该状态不支持审核");
					return map;
				}
			}
			Purenterydetail details=new Purenterydetail();
			details.setEnteryno(purenteries.get(0).getEnteryno());
			List<Purenterydetail> purenterydetails=purenterydetailDao.find(details);
			if (purenteries.size()>0) {
				map.put("purenteries", purenteries.get(0));
				map.put("purenterydetails", purenterydetails);
				map.put("result", "");
				return map;
			}
		}
		map.put("result", "找不到数据");
		return map;
	}


	
	@Override
	@Transactional
	public String checkMaterialin(Purentery purentery) {
		List<Purentery> purenteries=purenteryDao.find(purentery);
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		if (purenteries.size()>0) {
			//审核是否通过（审核通过）
			if(purentery.getStatus().equals(Constant.PURENTERY_STATUS_06)){
				Purenterydetail details=new Purenterydetail();
				details.setEnteryno(purenteries.get(0).getEnteryno());
				List<Purenterydetail> purenterydetails=purenterydetailDao.find(details);
				DecimalFormat df = new DecimalFormat("#.0000");
				if (purenteries.size()>0) {
					for (Purenterydetail purenterydetail : purenterydetails) {
						Integer findCode = materialinDao.findByCode(purenterydetail.getCode());
						if (findCode > 0) {
							Materialin ma = new Materialin();
							ma.setCode(purenterydetail.getCode());
							ma.setOrgId(orgId);
							ma.setWarehouseId(purenteries.get(0).getWarehouseid());
							ma.setLocationId(purenteries.get(0).getLocationid());
							List<Materialin> materialins = materialinDao.find(ma);
							// 在同一仓库和仓位下，原料已入库则修改入库原料的信息
							if (materialins.size() > 0) {
								ma.setId(materialins.get(0).getId());
								ma.setPrice(Double.parseDouble(df.format(purenterydetail.getPrice())));
								ma.setPurcost(Double.parseDouble(df.format((materialins.get(0).getPurcost()*materialins.get(0).getWeight()+purenterydetail.getPurcost())/(purenterydetail.getWeight()+materialins.get(0).getWeight()))));
								ma.setSaleprice(Double.parseDouble(df.format(purenterydetail.getSaleprice())));
								ma.setRemarks(purenterydetail.getRemarks());
								ma.setBatchnum(purenteries.get(0).getPurno());
								ma.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
								ma.setStatus(Constant.MATERIALIN_STATUES_02);
								ma.setAvailNum(purenterydetail.getNum()+materialins.get(0).getNum());
								ma.setAvailWeight(Double.parseDouble(df.format(purenterydetail.getWeight()+ materialins.get(0).getWeight())));
								Materialin materialin=new Materialin();
								materialin.setCode(ma.getCode());
								materialin.setOrgId(ma.getOrgId());
								materialin.setWarehouseId(purenteries.get(0).getWarehouseid());
								materialin.setLocationId(ma.getLocationId());
								materialin.setNum(purenterydetail.getNum());
								materialin.setWeight(Double.parseDouble(df.format(purenterydetail.getWeight())));
								try {
									materialinService.increaseInventory(materialin);
								} catch (Exception e) {
									logger.error("增加库存操作出错！", e);
									new RuntimeException("操作出错！");
								}
								materialinDao.update(ma);
							} else {
								// 原料第一次提交
								Materialin materialin = new Materialin();
								materialin.setId(UuidUtil.get32UUID());
								materialin.setCode(purenterydetail.getCode());
								materialin.setOrgId(orgId);
								materialin.setWarehouseId(purenteries.get(0).getWarehouseid());
								materialin.setLocationId(purenteries.get(0).getLocationid());
								materialin.setNum(purenterydetail.getNum());
								materialin.setWeight(purenterydetail.getWeight());
								materialin.setPrice(purenterydetail.getPrice());
								materialin.setPurcost(Double.parseDouble(df.format(purenterydetail.getPurcost() / purenterydetail.getWeight())));
								materialin.setSaleprice(purenterydetail.getSaleprice());
								materialin.setRemarks(purenterydetail.getRemarks());
								materialin.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
								materialin.setStatus(Constant.MATERIALIN_STATUES_02);
								materialin.setAvailNum(purenterydetail.getNum());
								materialin.setAvailWeight(purenterydetail.getWeight());
								materialin.setType(purenterydetails.get(0).getFeeType());
								materialin.setBatchnum(purenteries.get(0).getPurno());
								materialinDao.insert(materialin);
							}
						}
					}
					purenteries.get(0).setStatus(Constant.PURENTERY_STATUS_06);
					purenteries.get(0).setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
					purenteries.get(0).setCheckorgid(AccountShiroUtil.getCurrentUser().getOrgId());
					purenteries.get(0).setCheckTime(new Date());
					purenteryDao.updateCheck(purenteries.get(0));
					return "";
					
				}
				//审核不通过
			}else if(purentery.getStatus().equals(Constant.PURENTERY_STATUS_05)){
				purenteries.get(0).setStatus(Constant.PURENTERY_STATUS_05);
				purenteries.get(0).setDescription(purentery.getDescription());
				purenteryDao.update(purenteries.get(0));
				return "";
			}
		}
		return "审核失败";
	}
	
	
	@Override
	@Transactional
	public String checkProductin(Purentery purentery) {
		List<Purentery> purenteries=purenteryDao.find(purentery);
		String orgId = AccountShiroUtil.getCurrentUser().getOrgId();
		if (purenteries.size()>0) {
			//审核是否通过（审核通过）
			if(purentery.getStatus().equals(Constant.PURENTERY_STATUS_06)){
				Purenterydetail details=new Purenterydetail();
				details.setEnteryno(purenteries.get(0).getEnteryno());
				List<Purenterydetail> purenterydetails=purenterydetailDao.find(details);
				List<Purenterydetail> delete=new ArrayList<>();
				String str="";
				if (purenteries.size()>0) {
					for (Purenterydetail purenterydetail : purenterydetails) {
						Integer productCode = materialinDao.findProductCode(purenterydetail.getCode());
						//查询入库的商品是否存在
						List<Product> product=productDao.findProductCode(purenterydetail.getCode());
						if (product.size()>0 && !product.get(0).getStatus().equals(Constant.PRODUCT_STATE_F)) {
							//if(productCode>0){
								//商品历史记录表信息
								Product proTwo=product.get(0);
								//商品信息
								proTwo.setWarehouseId(purenteries.get(0).getWarehouseid());
								proTwo.setLocationId(purenteries.get(0).getLocationid());
								proTwo.setOrgId(orgId);
								//proTwo.setStatus(Constant.PRODUCT_STATE_B);
								proTwo.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
								if(StringUtils.isEmpty(proTwo.getInWarehouseNum())){
									proTwo.setInWarehouseNum(purenteries.get(0).getEnteryno());
								}
								productDao.updateWarehouse(proTwo);
								//调用借口改变商品状态
								Product setStatus=new Product();
								setStatus.setId(proTwo.getId());
								setStatus.setStatus(Constant.PRODUCT_STATE_B);
								productService.updateProductState(setStatus);
								/*增加历史记录*/
								/*productDao.insertTradeHis(pro.getId(), orgId, purenteries.get(0).getLocationid());*/
								TradeHis his=new TradeHis();
								his.setId(UuidUtil.get32UUID());
								//条码
								his.setCode(product.get(0).getCode());
								//产品id
								his.setProductid(product.get(0).getId());
								//类型（字典）
								his.setType(Constant.SCM_HIS_TYPE_03);
								//交易订单号
								his.setTradeorder(purenteries.get(0).getPurno());
								//TODO 商品历史表数据的产品数据不是很准确
								//数量
								his.setTradenum(purenterydetail.getNum().intValue());
								//重量
								his.setTradeweight(purenterydetail.getWeight());
								//金价
								his.setTradegoldprice(product.get(0).getGoldCost());
								//基础工费
								his.setTradebasicwage(product.get(0).getWageBasic());
								//附加工费
								his.setTradeaddwage(product.get(0).getWageCw());
								//其他工费
								his.setTradeotherwage(product.get(0).getWageOw());
								//吊牌价
								his.setTradeunitprice(purenterydetail.getPrice());
								//总额（吊牌价*数量）
								his.setTradetotalprice(product.get(0).getPrice());
								//实售价
								his.setTradeactureprice(product.get(0).getPriceSuggest());
								//批发价
								his.setTradewholesale(product.get(0).getPriceTrade());
								//采购成本价
								his.setTradecostprice(purenterydetail.getPurcost());
								//核价成本
								his.setTradecheckprice(product.get(0).getCostChk());
								//财务成本（采购成本+税）
								his.setTradefinanceprice(purenterydetail.getFinacost());
								//拨入机构id
								his.setInorgid(orgId);
								//拨入仓库id
								his.setInwarehouseid(purenteries.get(0).getWarehouseid());
								//拨出单位id
								his.setOutorgid(product.get(0).getOrgId());
								//拨出仓库id
								his.setOutwarehouseid(product.get(0).getWarehouseId());
								his.setCreateTime(new Date());
								his.setCreateUser(AccountShiroUtil.getCurrentUser().getAccountId());
								tradeHisDao.insert(his);
							//}
						}else{
							purenteries.get(0).setTotalprice(purenteries.get(0).getTotalprice()-purenterydetail.getPrice());
							purenteries.get(0).setPurcost(purenteries.get(0).getPurcost()-purenterydetail.getPurcost());
							purenteries.get(0).setFinacost(purenteries.get(0).getFinacost()-purenterydetail.getFinacost());
							purenteries.get(0).setDiffweight(purenteries.get(0).getDiffweight()-purenterydetail.getDiffweight());
							purenteries.get(0).setCheckcost(purenteries.get(0).getCheckcost()-purenterydetail.getCheckcost());
							purenteries.get(0).setTotalcount(purenteries.get(0).getTotalcount()-1);
							purenteries.get(0).setTotalnum(purenteries.get(0).getTotalnum()-1);
							delete.add(purenterydetail);
							str=str+purenterydetail.getCode()+"、";
						}
					}
					//批量删除删除的数据
					if(delete.size()>0){
						purenterydetailDao.deleteBatchById(delete);
					}
					purenteries.get(0).setStatus(Constant.PURENTERY_STATUS_06);
					purenteries.get(0).setCheckUser(AccountShiroUtil.getCurrentUser().getAccountId());
					purenteries.get(0).setCheckorgid(AccountShiroUtil.getCurrentUser().getOrgId());
					purenteries.get(0).setCheckTime(new Date());
					purenteryDao.updateCheck(purenteries.get(0));
					if(str.length()>0){
						str="以下商品已被退厂："+str.substring(0, str.length()-1);
					}
					return str;
				}
				//审核不通过
			}else if(purentery.getStatus().equals(Constant.PURENTERY_STATUS_05)){
				purenteries.get(0).setStatus(Constant.PURENTERY_STATUS_05);
				purenteries.get(0).setDescription(purentery.getDescription());
				purenteryDao.update(purenteries.get(0));
				return "";
			}
		}
		return "审核失败";
	}
	

	@Override
	public Purenterydetail findPurenterydetail(Purenterydetail purenterydetail) {
		List<Purenterydetail> list=purenterydetailDao.find(purenterydetail);
		return list.get(0);
	}

	@Override
	@Transactional
	public String del(Purentery purentery) {
		List<Purentery> list = purenteryDao.find(purentery);
		if (list.size() > 0) {
			if (!list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_03)) {
				purenteryDao.delete(list.get(0));
				Purenterydetail details=new Purenterydetail();
				details.setEnteryno(list.get(0).getEnteryno());
				List<Purenterydetail> purenterydetails=purenterydetailDao.find(details);
				if(purenterydetails.size()>0){
					purenterydetailDao.deleteBatchById(purenterydetails);
				}
				return "";
			}
			return "删除失败，状态错误";
		}
		return "找不到原料信息";
	}
	
	
	public Map<String, Object> proDel(String cheks){
		Map<String, Object> map=new HashMap<>();
		String[] chk =cheks.split(",");
		Integer count=0;
		Integer fail=0;
		for (String string : chk) {
			Purentery purentery=new Purentery();
			purentery.setId(string);
			List<Purentery> list = purenteryDao.find(purentery);
			if (list.size() > 0) {
				if(list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_01) || list.get(0).getStatus().equals(Constant.PURENTERY_STATUS_05)){
					purenteryDao.delete(list.get(0));
					Purenterydetail details=new Purenterydetail();
					details.setEnteryno(list.get(0).getEnteryno());
					List<Purenterydetail> purenterydetails=purenterydetailDao.find(details);
					if(purenterydetails.size()>0){
						purenterydetailDao.deleteBatchById(purenterydetails);
					}
					count=count+1;
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
	
	
	public List<Purenterydetail> findSetMaterialin(Purentery purentery){
		return purenterydetailDao.findSetMaterialin(purentery);
	}
	
	public List<Purenterydetail> findSetProductin(Purentery purentery){
		return purenterydetailDao.findSetProductin(purentery);
	}

	@Override
	@Transactional
	public void updateMaterialin(String myData, Purentery purenterys) {
		JSONArray jsonarray = JSONArray.fromObject(myData);  
		List list = (List)JSONArray.toCollection(jsonarray, Purenterydetail.class);  
        Iterator it = list.iterator();
		List<Purentery> purenteries=purenteryDao.find(purenterys);
		List<Purenterydetail> lists=new ArrayList<>();
		List<Purenterydetail> listsTwo=new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.0000");
		Double prices=0.0,purcosts=0.0,saleprices=0.0,finacosts=0.0,diffweights=0.0,checkcost=0.0;
		Integer totalcount=0;
		if (purenteries.size()>0) {
			Set<String> set=new HashSet<>();  
			while(it.hasNext()){  
	        	Purenterydetail purenterydetail = (Purenterydetail)it.next();  
	        	Integer findCode = materialinDao.findByCode(purenterydetail.getCode());
				if (findCode > 0) {
		        	purenterydetail.setEnteryno(purenteries.get(0).getEnteryno());
					if (StringUtils.isEmpty(purenterydetail.getId())) {
						purenterydetail.setType(Constant.PURENTERY_TYPE_02);
						purenterydetail.setId(UuidUtil.get32UUID());
						lists.add(purenterydetail);
						listsTwo.add(purenterydetail);
					}else{
						purenterydetailDao.update(purenterydetail);
						listsTwo.add(purenterydetail);
					}
					prices=Double.parseDouble(df.format(prices+purenterydetail.getPrice()));
					purcosts=Double.parseDouble(df.format(purcosts+purenterydetail.getPurcost()));
					saleprices=Double.parseDouble(df.format(saleprices+purenterydetail.getSaleprice()));
					finacosts=Double.parseDouble(df.format(finacosts+purenterydetail.getFinacost()));
					diffweights=Double.parseDouble(df.format(diffweights+purenterydetail.getDiffweight()));
					/*checkcost=Double.parseDouble(df.format(checkcost+purenterydetail.getCheckcost()));*/
					totalcount=totalcount+1;
					set.add(purenterydetail.getCode());
				}
			}
			//批量新增的数据
			if (lists.size()>0) {
				purenterydetailDao.batchInsert(lists);
			}
			//批量删除删除的数据
			if(listsTwo.size()>0){
				purenterydetailDao.deleteBatch(listsTwo, purenteries.get(0).getEnteryno());
			}
			purenteries.get(0).setStatus(purenterys.getStatus());
			purenteries.get(0).setWarehouseid(purenterys.getWarehouseid());
			List<SelectData> location=warehouseLocationDao.findWarehouseLocationAll(purenterys.getWarehouseid());
			if(location.size()>0){
				purenteries.get(0).setLocationid(location.get(0).getKey());
			}
			purenteries.get(0).setRemarks(purenterys.getRemarks());
			purenteries.get(0).setTotalnum(set.size());
			purenteries.get(0).setTotalcount(totalcount);
			purenteries.get(0).setPurcost(purcosts);
			purenteries.get(0).setCheckcost(0.0);
			purenteries.get(0).setFinacost(finacosts);
			purenteries.get(0).setTotalprice(prices);
			purenteries.get(0).setDiffweight(diffweights);
			purenteries.get(0).setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			
			purenteryDao.update(purenteries.get(0));
		}
	}

	
	@Override
	@Transactional
	public void updateProductin(String myData, Purentery purenterys) {
        ///Iterator it = list.iterator();
		List<Purentery> purenteries=purenteryDao.find(purenterys);
		if (purenteries.size()>0) {
			List<Purenterydetail> lists=new ArrayList<>();
			List<Purenterydetail> listsTwo=new ArrayList<>();
			DecimalFormat df = new DecimalFormat("#.0000");
			Double prices=0.0,purcosts=0.0,saleprices=0.0,finacosts=0.0,diffweights=0.0,checkcost=0.0;
			Integer totalcount=0;
			//Set<String> set=new HashSet<>(); 
			JSONArray jsonarray = JSONArray.fromObject(myData);  
			List<Purenterydetail> list = (List<Purenterydetail>)JSONArray.toCollection(jsonarray, Purenterydetail.class);  
			for (Purenterydetail purenterydetail : list) {
				//Purenterydetail purenterydetail = (Purenterydetail)it.next(); 
				if (StringUtils.isEmpty(purenterydetail.getId())) {
					Purentery purentery2=new Purentery();
					/*Org org=new Org();
					org.setId(AccountShiroUtil.getCurrentUser().getOrgId());
					org=orgService.find(org).get(0);
					if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
						purentery2.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
					}else{
						purentery2.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
					}*/
		        	purentery2.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		        	purentery2.setCode(purenterydetail.getCode());
		    		List<Purenterydetail> details=purenterydetailDao.findProductCode(purentery2);
					if(details.size()>0){
						purenterydetail=details.get(0);
						purenterydetail.setEnteryno(purenteries.get(0).getEnteryno());
						purenterydetail.setType(Constant.PURENTERY_TYPE_01);
						purenterydetail.setId(UuidUtil.get32UUID());
						lists.add(purenterydetail);
						listsTwo.add(purenterydetail);
					}
				}else{
					purenterydetail.setEnteryno(purenteries.get(0).getEnteryno());
					List<Purenterydetail> list2=purenterydetailDao.findDetail(purenterydetail);
					if(list2.size()>0){
						purenterydetail=list2.get(0);
						purenterydetailDao.update(purenterydetail);
						listsTwo.add(purenterydetail);
					}
				}
				prices=Double.parseDouble(df.format(prices+purenterydetail.getPrice()));
				purcosts=Double.parseDouble(df.format(purcosts+purenterydetail.getPurcost()));
				saleprices=Double.parseDouble(df.format(saleprices+purenterydetail.getSaleprice()));
				finacosts=Double.parseDouble(df.format(finacosts+purenterydetail.getFinacost()));
				diffweights=Double.parseDouble(df.format(diffweights+purenterydetail.getDiffweight()));
				checkcost=Double.parseDouble(df.format(checkcost+purenterydetail.getCheckcost()));
				totalcount=totalcount+1;
				//set.add(purenterydetail.getCode());
			
			}
			//批量新增的数据
			if (lists.size()>0) {
				purenterydetailDao.batchInsert(lists);
			}
			//批量删除删除的数据
			if(listsTwo.size()>0){
				purenterydetailDao.deleteBatch(listsTwo, purenteries.get(0).getEnteryno());
			}
			purenteries.get(0).setStatus(purenterys.getStatus());
			purenteries.get(0).setWarehouseid(purenterys.getWarehouseid());
			purenteries.get(0).setLocationid(purenterys.getLocationid());
			purenteries.get(0).setRemarks(purenterys.getRemarks());
			purenteries.get(0).setTotalnum(totalcount);
			purenteries.get(0).setTotalcount(totalcount);
			purenteries.get(0).setPurcost(purcosts);
			purenteries.get(0).setCheckcost(checkcost);
			purenteries.get(0).setFinacost(finacosts);
			purenteries.get(0).setTotalprice(prices);
			purenteries.get(0).setDiffweight(diffweights);
			purenteries.get(0).setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
			purenteries.get(0).setFeeType(purenterys.getFeeType());
			purenteryDao.update(purenteries.get(0));
		}
	}
	
	@Override
	public List<Purenterydetail> findMaterialCode(Purentery purentery) {
		return purenterydetailDao.findMaterialCode(purentery);
	}

	@Override
	public List<Purenterydetail> findProductCode(Purentery purentery) {
		/*Org org=new Org();
		org.setId(AccountShiroUtil.getCurrentUser().getOrgId());
		org=orgService.find(org).get(0);
		if(!Constant.ORGGRADE_03.equals(org.getOrgGrade())){
			purentery.setOrgId(AccountShiroUtil.getCurrentUser().getCompany());
		}else{
			purentery.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		}*/
		purentery.setOrgId(AccountShiroUtil.getCurrentUser().getOrgId());
		return purenterydetailDao.findProductCode(purentery);
	}
}
