package com.jy.service.scm.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.common.excel.IExcelRowReader;
import com.jy.common.mybatis.Page;
import com.jy.common.tool.Constant;
import com.jy.common.utils.base.UuidUtil;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.dao.scm.accessories.AccessoriesDao;
import com.jy.dao.scm.credential.CredentialDao;
import com.jy.dao.scm.franchisee.FranchiseeDao;
import com.jy.dao.scm.material.MaterialDao;
import com.jy.dao.scm.materialin.PurenteryDao;
import com.jy.dao.scm.materialin.PurenterydetailDao;
import com.jy.dao.scm.product.ProductDao;
import com.jy.entity.scm.MaterialVO;
import com.jy.entity.scm.accessories.Accessories;
import com.jy.entity.scm.credential.Credential;
import com.jy.entity.scm.materialcome.Materialcome;
import com.jy.entity.scm.materialin.Purentery;
import com.jy.entity.scm.materialin.Purenterydetail;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.entity.scm.product.Product;
import com.jy.entity.scm.product.ProductUpload;
import com.jy.entity.scm.purorder.OrderReturnDetail;
import com.jy.entity.system.account.Account;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.attachment.UploadFileService;
import com.jy.service.system.tool.SerialNumberService;


@Service("productService")
public class ProductServiceImpl extends BaseServiceImp<Product> implements ProductService,IExcelRowReader{

	private final static ReentrantLock updateLock = new ReentrantLock();
	
	private final static ReentrantLock batchUpLock = new ReentrantLock();
	
	private final static ReentrantLock recoverLock = new ReentrantLock(); 
	
	@Autowired
	private ProductDao dao;
	@Autowired
	private UploadFileService uploadService;
	@Autowired
	private SerialNumberService serialNumberService;
	@Autowired
	private FranchiseeDao franchiseeDao;
	@Autowired
	private AccessoriesDao accessoriesDao;
	@Autowired
	private MaterialDao materialDao;
	@Autowired
	private CredentialDao credentialDao;
	@Autowired
	private PurenteryDao purenteryDao;
	@Autowired
	private PurenterydetailDao purenterydetailDao;
	
	@Override
	@Transactional
	public void updateProductInfo(Product product,HttpServletRequest request)throws IOException {
		product.setUpdateUser(AccountShiroUtil.getCurrentUser().getAccountId());
		Account user = AccountShiroUtil.getCurrentUser();
		product.setOrgId(user.getOrgId());
		uploadService.saveUploadFileMore(product.getImgId(), product.getId(), request);
		dao.updateProductInfo(product);
	}

	@Override
	public int updateProductState(Product product) {
		try{
			updateLock.lock();
			return dao.updateProductState(product);
		}finally{
			updateLock.unlock();
		}
	}

	@Override
	public void logicDelBatchProduct(List<Product> list) {
			dao.batchDeleteProduct(list);
	}

	@Override
	public List<MoudleDetail> queryModelCode(String code) {
		return dao.queryModelCode(code);
	}

	@Override
	@Transactional
	public int insertProduct(Product product,HttpServletRequest request) throws IOException{
		int res = 0;
			Account user = AccountShiroUtil.getCurrentUser();
			product.setOrgId(user.getOrgId());
			product.setCreateUser(user.getAccountId());
			String code=serialNumberService.generateSerialNumber(product.getCateJewelryId()+product.getCateId());
			product.setCode(code);
			product.setWarehouseId(Constant.WAREHOUSE_DEFAULT);
			product.setLocationId(Constant.WAREHOUSE_DEFAULT);
			dao.insert(product);
			uploadService.saveUploadFileMore(product.getImgId(), product.getId(), request);
			res = 1;
		
		return res;
	}

	@Override
	public Page<Product> findSplit( Product product,Page<Product> page) {
		page.setResults(dao.findSplit(product,page));
		return page;
	}

	@Override
	public List<Product> findSplitOk(List<String> list) {
		return dao.findSplitOk(list);
	}

	@Override
	public List<Moudle> queryMoudleCode(String code) {
		return dao.queryMoudleCode(code);
	}

	@Override
	public List<Product> queryProductCode(String code) {
		return dao.queryProductCode(code);
	}

	@Override
	public List<Product> queryinWarehouseNum(String code) {
		return dao.queryinWarehouseNum(code);
	}

	@Override
	public Moudle query(String id) {
		return dao.query(id);
	}

	@Override
	public List<Product> batchUpdateProductState(List<Product> list, Product product) {
		try{
			batchUpLock.lock();
			List<Product> result = dao.queryLockedProdInList(list);
			dao.batchUpdateProductState(list, product);
			return result;
		}finally{
			batchUpLock.unlock();
		}
	}

	@Override
	public int recoverProductState(String orderDetailId) {
		try{
			recoverLock.lock();
			return dao.updateSplitStatus(orderDetailId);
		}finally{
			recoverLock.unlock();
		}
	}
	
	@Override
	public void getRows(int sheetIndex, int curRow, List<String> list) {
		
	}

	@Override
	@Transactional
	public void batchInsertIntoTempTable(List<ProductUpload> list) throws Exception{
		String id=AccountShiroUtil.getCurrentUser().getAccountId();
		int count=dao.deleteByUserId(id);
		logger.info("=========================>清空临时表上传历史数据："+count+"条。");
		dao.batchInsertIntoTempTable(list);
		dao.queryProductTemp(id,AccountShiroUtil.getCurrentUser().getOrgId());
	}

	@Override
	public List<ProductUpload> getProductUpload(String id) {
		List<ProductUpload> list=dao.getProductUpload(id);
		return list;
	}

	@Override
	@Transactional
	public void batchImport(List<ProductUpload> p) {
		List<Product> data=new ArrayList<>();
		List<Accessories> result=new ArrayList<>();
		List<Credential> list=new ArrayList<>();
		Account user = AccountShiroUtil.getCurrentUser();
		for (ProductUpload productUpload : p) {
			Product product=new Product();
			product.setStatus(Constant.PRODUCT_STATE_0);
			product.setId(UuidUtil.get32UUID());
			product.setCreateTime(productUpload.getCreatetime());
			product.setName(productUpload.getName());
			product.setCerNum(productUpload.getProcertificate());
			if(productUpload.getProcertificate()!=null&&!"0".equals(productUpload.getProcertificate())){
				 Credential ct=new Credential();
				 ct.setId(UuidUtil.get32UUID());
				 ct.setProductId(product.getId());
				 ct.setOrgId(user.getOrgId());
				 ct.setCerNo(productUpload.getProcertificate());
				 ct.setCerName("0");
				 ct.setStatus("1");
				 ct.setCreateUser(user.getAccountId());
				 list.add(ct);
			}
			product.setDescription(productUpload.getDescription());
			product.setCreateUser(user.getAccountId());
			product.setRemarks(productUpload.getRemarks());
			product.setMoudtlCode(productUpload.getMoucode());
			Moudle moudle=dao.querySuppmoucode(productUpload.getMoucode());
			product.setMouCode(moudle.getCode());
			product.setFranchiseeId(moudle.getId());
			product.setCircel(productUpload.getCircel());
			product.setWageBasic(productUpload.getWagebasic());
			product.setWageSe(productUpload.getWagese());
			product.setWageEw(productUpload.getWageew());
			product.setWageCw(productUpload.getWagecw());
			product.setWageOw(productUpload.getWageow());
			product.setCostCer(productUpload.getCostcer());
			product.setCostAdd(productUpload.getCostadd());
			product.setGoldCost(productUpload.getGoldcost());
			product.setTotalWeight(productUpload.getTotalweight());
			product.setGoldWeight(productUpload.getGoldweight());
			product.setGoldCostLose(productUpload.getGoldcostlose());
			product.setGoldSellLose(productUpload.getGoldselllose());
			product.setCostPur(productUpload.getCostfin());
			product.setCostFin(productUpload.getCostfin());
			product.setGoldType(productUpload.getGoldtype());
			product.setCateId(productUpload.getCateid());
			product.setPrimarycode(productUpload.getPrimarycode());
			product.setNoticeno(productUpload.getNoticeno());
			String catejewelryid=productUpload.getCatejewelryid();
			if(catejewelryid.indexOf(".")>-1){
				catejewelryid=catejewelryid.substring(0, catejewelryid.indexOf("."));
			}
			product.setCateJewelryId(catejewelryid);
			product.setWageMode(Constant.CHARGE_TYPE_PIECE);
			product.setControlType("0");
			String warehouseid=productUpload.getWarehouseid();
			if(warehouseid.indexOf(".")>-1){
				warehouseid=warehouseid.substring(0, warehouseid.indexOf("."));
			}
			product.setWarehouseId(warehouseid);
			String locationid=productUpload.getLocationid();
			if(locationid.indexOf(".")>-1){
				locationid=locationid.substring(0, locationid.indexOf("."));
			}
			product.setLocationId(locationid);
			product.setOrgId(user.getOrgId());
			product.setPriceSuggest(productUpload.getPricesuggest());
			product.setCostFin(productUpload.getCostfin());
			product.setCostChk(productUpload.getPrime());
			product.setPrice(productUpload.getPrice());
			product.setLabelType(productUpload.getLabeltype());
			product.setPurchaseNum(productUpload.getPurchasenum());
			product.setWholesale(productUpload.getWholesale());
			product.setStoneShape(productUpload.getStoneshapetype());
			product.setCount(1);
			String code=serialNumberService.generateSerialNumber(product.getCateJewelryId()+product.getCateId());
			product.setCode(code);
			if(productUpload.getStonecode()!=null){
				Accessories accessories =new Accessories();
				int count=accessoriesDao.findMax();
				accessories.setSort(String.valueOf(count+1));
				accessories.setId(UuidUtil.get32UUID());
				accessories.setStoneFlag("1");
				accessories.setProductId(product.getId());
				accessories.setStatus("1");
				accessories.setCreateUser(user.getAccountId());
				accessories.setCreateTime(new Date());
				accessories.setStoneUnit(productUpload.getJeweler());
				if(Constant.SCM_DATA_STONEUNIT_G.equals(accessories.getStoneUnit())){
					accessories.setPurPrice(productUpload.getPurcal()/(productUpload.getStoneweight()*5));
				}else if(Constant.SCM_DATA_STONEUNIT_CT.equals(accessories.getStoneUnit())){
					accessories.setPurPrice(productUpload.getPurcal()/productUpload.getStoneweight());
				}
				accessories.setStoneCode(productUpload.getStonecode());
				accessories.setStoneName(productUpload.getStonename());
				accessories.setStoneShape(productUpload.getStoneshapetype());
				accessories.setStoneWeight(productUpload.getStoneweight());
				accessories.setStoneCount(productUpload.getStonecount());
				accessories.setCostPrice(0);
				
				accessories.setCostCal(0);
				accessories.setPurcal(productUpload.getPurcal());
				accessories.setClarity(productUpload.getClarity());
				accessories.setColor(productUpload.getColor());
				accessories.setCut(productUpload.getCut());
				accessories.setStonePkgno(productUpload.getStonepkgno());
				if(productUpload.getStonepkgno()!=null&&!"0".equals(productUpload.getStonepkgno())){
					 List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories.setStoneShape(m.getStoneshape());
						 accessories.setStoneWeight(m.getWeight());
						 accessories.setPurcal(m.getPurcost());
						 accessories.setPurPrice(m.getPurcost()/m.getWeight());
						 accessories.setClarity(m.getClartity());
						 accessories.setColor(m.getColor());
						 accessories.setCut(m.getCut());
					 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
						 Credential c=new Credential();
						 c.setId(UuidUtil.get32UUID());
						 c.setProductId(product.getId());
						 c.setAccessorieId(accessories.getId());
						 c.setOrgId(user.getOrgId());
						 c.setCerNo(m.getCernum());
						 c.setCerName("GL");
						 c.setStatus("1");
						 c.setCreateUser(user.getAccountId());
						 list.add(c);
					 }
				}else{
					 if(productUpload.getCertificate()!=null&&!"0".equals(productUpload.getCertificate())){
						 Credential c=new Credential();
						 c.setId(UuidUtil.get32UUID());
						 c.setProductId(product.getId());
						 c.setAccessorieId(accessories.getId());
						 c.setOrgId(user.getOrgId());
						 c.setCerNo(productUpload.getCertificate());
						 c.setCerName("0");
						 c.setStatus("1");
						 c.setCreateUser(user.getAccountId());
						 list.add(c);
					 }
				 }
			}
				result.add(accessories);
		}
			if(productUpload.getStonecode1()!=null){
				Accessories accessories1 =new Accessories();
				int count=accessoriesDao.findMax();
				accessories1.setSort(String.valueOf(count+1));
				accessories1.setId(UuidUtil.get32UUID());
				accessories1.setStoneFlag("0");
				accessories1.setProductId(product.getId());
				accessories1.setStatus("1");
				accessories1.setCreateUser(user.getAccountId());
				accessories1.setCreateTime(new Date());
				accessories1.setStoneCode(productUpload.getStonecode1());
				accessories1.setStoneName(productUpload.getStonename1());
				accessories1.setStoneWeight(productUpload.getStoneweight1());
				accessories1.setStoneCount(productUpload.getStonecount1());
				accessories1.setCostPrice(0);
				accessories1.setPurPrice(productUpload.getPurcal1()/productUpload.getStoneweight1());;
				accessories1.setCostCal(0);
				accessories1.setPurcal(productUpload.getPurcal1());
				accessories1.setStoneUnit(productUpload.getJeweler1());
				accessories1.setStonePkgno(productUpload.getStonepkgno1());
				if(productUpload.getStonepkgno1()!=null&&!"0".equals(productUpload.getStonepkgno1())){
					List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories1.setStoneWeight(m.getWeight());
						 accessories1.setPurcal(m.getPurcost());
						 accessories1.setPurPrice(m.getPurcost()/m.getWeight());
					 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
							 Credential c1=new Credential();
							 c1.setId(UuidUtil.get32UUID());
							 c1.setProductId(product.getId());
							 c1.setAccessorieId(accessories1.getId());
							 c1.setOrgId(user.getOrgId());
							 c1.setCerNo(m.getCernum());
							 c1.setCerName("GL");
							 c1.setStatus("1");
							 c1.setCreateUser(user.getAccountId());
							 list.add(c1);
					 	}
					 }else{
						 if(productUpload.getCertificate1()!=null&&!"0".equals(productUpload.getCertificate1())){
							 Credential c1=new Credential();
							 c1.setId(UuidUtil.get32UUID());
							 c1.setProductId(product.getId());
							 c1.setAccessorieId(accessories1.getId());
							 c1.setOrgId(user.getOrgId());
							 c1.setCerNo(productUpload.getCertificate1());
							 c1.setCerName("0");
							 c1.setStatus("1");
							 c1.setCreateUser(user.getAccountId());
							 list.add(c1);
						 }
					 }
				}
				result.add(accessories1);
			}
			if(productUpload.getStonecode2()!=null){
				Accessories accessories2 =new Accessories();
				int count=accessoriesDao.findMax();
				accessories2.setSort(String.valueOf(count+1));
				accessories2.setId(UuidUtil.get32UUID());
				accessories2.setStoneFlag("0");
				accessories2.setProductId(product.getId());
				accessories2.setStatus("1");
				accessories2.setCreateUser(user.getAccountId());
				accessories2.setCreateTime(new Date());
				accessories2.setStoneCode(productUpload.getStonecode2());
				accessories2.setStoneName(productUpload.getStonename2());
				accessories2.setStoneWeight(productUpload.getStoneweight2());
				accessories2.setStoneCount(productUpload.getStonecount2());
				accessories2.setCostPrice(0);
				accessories2.setPurPrice(productUpload.getPurcal2()/productUpload.getStoneweight2());;
				accessories2.setCostCal(0);
				accessories2.setPurcal(productUpload.getPurcal2());
				accessories2.setStoneUnit(productUpload.getJeweler2());
				accessories2.setStonePkgno(productUpload.getStonepkgno2());
				if(productUpload.getStonepkgno2()!=null&&!"0".equals(productUpload.getStonepkgno2())){
					List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories2.setStoneWeight(m.getWeight());
						 accessories2.setPurcal(m.getPurcost());
						 accessories2.setPurPrice(m.getPurcost()/m.getWeight());
					 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
						 Credential c2=new Credential();
						 c2.setId(UuidUtil.get32UUID());
						 c2.setProductId(product.getId());
						 c2.setAccessorieId(accessories2.getId());
						 c2.setOrgId(user.getOrgId());
						 c2.setCerNo(m.getCernum());
						 c2.setCerName("GL");
						 c2.setStatus("1");
						 c2.setCreateUser(user.getAccountId());
						 list.add(c2);
					 }
					 }else{
						 if(productUpload.getCertificate2()!=null&&!"0".equals(productUpload.getCertificate2())){
							 Credential c2=new Credential();
							 c2.setId(UuidUtil.get32UUID());
							 c2.setProductId(product.getId());
							 c2.setAccessorieId(accessories2.getId());
							 c2.setOrgId(user.getOrgId());
							 c2.setCerNo(productUpload.getCertificate2());
							 c2.setCerName("0");
							 c2.setStatus("1");
							 c2.setCreateUser(user.getAccountId());
							 list.add(c2);
						 }
					 }
				}
				result.add(accessories2);
			}
			if(productUpload.getStonecode3()!=null){
				Accessories accessories3 =new Accessories();
				int count=accessoriesDao.findMax();
				accessories3.setSort(String.valueOf(count+1));
				accessories3.setId(UuidUtil.get32UUID());
				accessories3.setStoneFlag("0");
				accessories3.setProductId(product.getId());
				accessories3.setStatus("1");
				accessories3.setCreateUser(user.getAccountId());
				accessories3.setCreateTime(new Date());
				accessories3.setStoneCode(productUpload.getStonecode3());
				accessories3.setStoneName(productUpload.getStonename3());
				accessories3.setStoneWeight(productUpload.getStoneweight3());
				accessories3.setStoneCount(productUpload.getStonecount3());
				accessories3.setCostPrice(0);
				accessories3.setPurPrice(productUpload.getPurcal3()/productUpload.getStoneweight3());;
				accessories3.setCostCal(0);
				accessories3.setPurcal(productUpload.getPurcal3());
				accessories3.setStoneUnit(productUpload.getJeweler3());
				accessories3.setStonePkgno(productUpload.getStonepkgno3());
				if(productUpload.getStonepkgno3()!=null&&!"0".equals(productUpload.getStonepkgno3())){
					List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories3.setStoneWeight(m.getWeight());
						 accessories3.setPurcal(m.getPurcost());
						 accessories3.setPurPrice(m.getPurcost()/m.getWeight());
					 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
						 Credential c3=new Credential();
						 c3.setId(UuidUtil.get32UUID());
						 c3.setProductId(product.getId());
						 c3.setAccessorieId(accessories3.getId());
						 c3.setOrgId(user.getOrgId());
						 c3.setCerNo(m.getCernum());
						 c3.setCerName("GL");
						 c3.setStatus("1");
						 c3.setCreateUser(user.getAccountId());
						 list.add(c3);
					 	}
					 }else{
						 if(productUpload.getCertificate3()!=null&&!"0".equals(productUpload.getCertificate3())){
							 Credential c3=new Credential();
							 c3.setId(UuidUtil.get32UUID());
							 c3.setProductId(product.getId());
							 c3.setAccessorieId(accessories3.getId());
							 c3.setOrgId(user.getOrgId());
							 c3.setCerNo(productUpload.getCertificate3());
							 c3.setCerName("0");
							 c3.setStatus("1");
							 c3.setCreateUser(user.getAccountId());
							 list.add(c3);
						 }
					 }
				}
				result.add(accessories3);
			}
			if(productUpload.getStonecode4()!=null){
				Accessories accessories4 =new Accessories();
				int count=accessoriesDao.findMax();
				accessories4.setSort(String.valueOf(count+1));
				accessories4.setId(UuidUtil.get32UUID());
				accessories4.setStoneFlag("0");
				accessories4.setProductId(product.getId());
				accessories4.setStatus("1");
				accessories4.setCreateUser(user.getAccountId());
				accessories4.setCreateTime(new Date());
				accessories4.setStoneCode(productUpload.getStonecode4());
				accessories4.setStoneName(productUpload.getStonename4());
				accessories4.setStoneWeight(productUpload.getStoneweight4());
				accessories4.setStoneCount(productUpload.getStonecount4());
				accessories4.setCostPrice(0);
				accessories4.setPurPrice(productUpload.getPurcal4()/productUpload.getStoneweight4());;
				accessories4.setCostCal(0);
				accessories4.setPurcal(productUpload.getPurcal4());
				accessories4.setStoneUnit(productUpload.getJeweler4());
				accessories4.setStonePkgno(productUpload.getStonepkgno4());
				if(productUpload.getStonepkgno4()!=null&&!"0".equals(productUpload.getStonepkgno4())){
					List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno()); 
					if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories4.setStoneWeight(m.getWeight());
						 accessories4.setPurcal(m.getPurcost());
						 accessories4.setPurPrice(m.getPurcost()/m.getWeight());
					 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
						 Credential c4=new Credential();
						 c4.setId(UuidUtil.get32UUID());
						 c4.setProductId(product.getId());
						 c4.setAccessorieId(accessories4.getId());
						 c4.setOrgId(user.getOrgId());
						 c4.setCerNo(m.getCernum());
						 c4.setCerName("GL");
						 c4.setStatus("1");
						 c4.setCreateUser(user.getAccountId());
						 list.add(c4);
					 }
					 }else{
						 if(productUpload.getCertificate4()!=null&&!"0".equals(productUpload.getCertificate4())){
							 Credential c4=new Credential();
							 c4.setId(UuidUtil.get32UUID());
							 c4.setProductId(product.getId());
							 c4.setAccessorieId(accessories4.getId());
							 c4.setOrgId(user.getOrgId());
							 c4.setCerNo(productUpload.getCertificate4());
							 c4.setCerName("0");
							 c4.setStatus("1");
							 c4.setCreateUser(user.getAccountId());
							 list.add(c4);
						 }
					 }
				}
				result.add(accessories4);
			}
			data.add(product);
			
			
		}		
		dao.batchInsertProduct(data); 
		if(list.size()>0){
			credentialDao.batchInsert(list);
		}
		if(result.size()>0){
			accessoriesDao.batchAccessories(result);
		}
		
		
	}
	@Override
	public Page<Product> fundSplitOut(String orderDetailId,Page<Product> page) {
		page.setResults(dao.fundSplitOut(orderDetailId));
		return page;
	}

	@Override
	@Transactional
	public void batchStock(List<ProductUpload> p,Product pro) {
		Double purcost=0.0,finacost=0.0,totalprice=0.0,checkcost=0.0;
		Integer totalcount=0;
		List<Product> data=new ArrayList<>();
		List<Accessories> result=new ArrayList<>();
		List<Credential> list=new ArrayList<>();
		List<Purenterydetail> purenterydetailList=new ArrayList<>();
		Account user = AccountShiroUtil.getCurrentUser();
		//添加入库信息
		Purentery purentery=new Purentery();
		purentery.setId(UuidUtil.get32UUID());
		//生成入库单号
		String codeNo=AccountShiroUtil.getCurrentUser().getDistCode();
		String enteryno=serialNumberService.generateSerialNumberByBussinessKey(Constant.SCM_BILL_PREFIX_RK+codeNo);
		purentery.setEnteryno(enteryno);
		purentery.setStatus(Constant.PURENTERY_STATUS_02);
		purentery.setPurno(p.get(0).getPurchasenum());
		purentery.setOrgId(user.getOrgId());
		purentery.setCreateorgid(user.getOrgId());
		purentery.setCreateUser(user.getAccountId());
		purentery.setType(Constant.PURENTERY_TYPE_01);
		purentery.setFeeType(pro.getFeeType());
		for (ProductUpload productUpload : p) {
			Product product=new Product();
			Accessories accessories =new Accessories();
			Accessories accessories1 =new Accessories();
			Accessories accessories2 =new Accessories();
			Accessories accessories3 =new Accessories();
			Accessories accessories4 =new Accessories();
			String code=serialNumberService.generateSerialNumber(productUpload.getCatejewelryid()+productUpload.getCateid());
//			Franchisee  f=franchiseeDao.queryByCode(productUpload.getFranchiseecode());
			//添加入库详表信息
			Purenterydetail purenterydetail=new Purenterydetail();
			purenterydetail.setId(UuidUtil.get32UUID());
			purenterydetail.setEnteryno(enteryno);
			purenterydetail.setCode(code);
			purenterydetail.setNum(1);
			purenterydetail.setWeight(productUpload.getTotalweight());
			purenterydetail.setPrice(productUpload.getPrice());
			purenterydetail.setPurcost(productUpload.getGoldvalue());
			purenterydetail.setSaleprice(0.0);
			purenterydetail.setFinacost(productUpload.getCostfin());
			purenterydetail.setDiffweight(0.0);
			purenterydetail.setType(Constant.PURENTERY_TYPE_01);
			purenterydetail.setCheckcost(productUpload.getPrime());
			totalcount=purenterydetail.getNum()+totalcount;
			purcost=purenterydetail.getPurcost()+purcost;
			finacost=purenterydetail.getFinacost()+finacost;
			totalprice=totalprice+purenterydetail.getPrice();
			checkcost=checkcost+purenterydetail.getCheckcost();
			//添加商品
			product.setId(UuidUtil.get32UUID());
			product.setCode(code);
			product.setStatus(Constant.PRODUCT_STATE_A);
			product.setCreateTime(productUpload.getCreatetime());
			product.setName(productUpload.getName());
			product.setCerNum(productUpload.getProcertificate());
			if(productUpload.getProcertificate()!=null&&"0".equals(productUpload.getProcertificate())){
				 Credential c=new Credential();
				 c.setId(UuidUtil.get32UUID());
				 c.setProductId(product.getId());
				 c.setOrgId(user.getOrgId());
				 c.setCerNo(productUpload.getProcertificate());
				 c.setCerName("0");
				 c.setStatus("1");
				 c.setCreateUser(user.getAccountId());
				 list.add(c);
			}
			product.setDescription(productUpload.getDescription());
			product.setCreateUser(user.getAccountId());
			product.setRemarks(productUpload.getRemarks());
			product.setMoudtlCode(productUpload.getMoucode());
			Moudle moudle=dao.querySuppmoucode(productUpload.getMoucode());
			product.setMouCode(moudle.getCode());
			product.setFranchiseeId(moudle.getId());
			product.setCircel(productUpload.getCircel());
			product.setWageBasic(productUpload.getWagebasic());
			product.setWageSe(productUpload.getWagese());
			product.setWageEw(productUpload.getWageew());
			product.setWageCw(productUpload.getWagecw());
			product.setWageOw(productUpload.getWageow());
			product.setCostCer(productUpload.getCostcer());
			product.setCostAdd(productUpload.getCostadd());
			product.setGoldCost(productUpload.getGoldcost());
			product.setTotalWeight(productUpload.getTotalweight());
			product.setGoldWeight(productUpload.getGoldweight());
			product.setGoldCostLose(productUpload.getGoldcostlose());
			product.setGoldSellLose(productUpload.getGoldselllose());
			product.setGoldValue(productUpload.getGoldvalue());
			product.setGoldType(productUpload.getGoldtype());
			product.setCateId(productUpload.getCateid());
			product.setCostPur(productUpload.getCostfin());
			product.setCostFin(productUpload.getCostfin());
			product.setPrimarycode(productUpload.getPrimarycode());
			product.setNoticeno(productUpload.getNoticeno());
			product.setPrice(productUpload.getPrice());
			
			String catejewelryid=productUpload.getCatejewelryid();
			if(catejewelryid.indexOf(".")>-1){
				catejewelryid=catejewelryid.substring(0, catejewelryid.indexOf("."));
			}
			product.setCateJewelryId(catejewelryid);
			product.setWageMode(Constant.CHARGE_TYPE_PIECE);
			product.setControlType("0");
			String warehouseid=productUpload.getWarehouseid();
			if(warehouseid.indexOf(".")>-1){
				warehouseid=warehouseid.substring(0, warehouseid.indexOf("."));
			}
			product.setWarehouseId(warehouseid);
			String locationid=productUpload.getLocationid();
			if(locationid.indexOf(".")>-1){
				locationid=locationid.substring(0, locationid.indexOf("."));
			}
			product.setLocationId(locationid);
			product.setOrgId(user.getOrgId());
			product.setPriceSuggest(productUpload.getPricesuggest());
			product.setCostFin(productUpload.getCostfin());
			product.setCostChk(productUpload.getPrime());
			product.setPrice(productUpload.getPrice());
			product.setLabelType(productUpload.getLabeltype());
			product.setPurchaseNum(productUpload.getPurchasenum());
			product.setWholesale(productUpload.getWholesale());
			product.setStoneShape(productUpload.getStoneshapetype());
			product.setCount(1);
			//添加辅件
			if(productUpload.getStonecode()!=null){
				 int count=accessoriesDao.findMax();
					accessories.setSort(String.valueOf(count+1));
					accessories.setId(UuidUtil.get32UUID());
					accessories.setStoneFlag("1");
					accessories.setProductId(product.getId());
					accessories.setStatus("1");
					accessories.setCreateUser(user.getAccountId());
					accessories.setCreateTime(new Date());
					accessories.setStoneUnit(productUpload.getJeweler());
					if(Constant.SCM_DATA_STONEUNIT_G.equals(accessories.getStoneUnit())){
						accessories.setPurPrice(productUpload.getPurcal()/(productUpload.getStoneweight()*5));
					}else if(Constant.SCM_DATA_STONEUNIT_CT.equals(accessories.getStoneUnit())){
						accessories.setPurPrice(productUpload.getPurcal()/productUpload.getStoneweight());
					}
					accessories.setStoneCode(productUpload.getStonecode());
					accessories.setStoneName(productUpload.getStonename());
					accessories.setStoneShape(productUpload.getStoneshapetype());
					accessories.setStoneWeight(productUpload.getStoneweight());
					accessories.setStoneCount(productUpload.getStonecount());
					accessories.setCostPrice(0);
					accessories.setCostCal(0);
					accessories.setPurcal(productUpload.getPurcal());
					accessories.setClarity(productUpload.getClarity());
					accessories.setColor(productUpload.getColor());
					accessories.setCut(productUpload.getCut());
					accessories.setStonePkgno(productUpload.getStonepkgno());
				if(productUpload.getStonepkgno()!=null&&!"0".equals(productUpload.getStonepkgno())){
					 List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories.setStoneShape(m.getStoneshape());
						 accessories.setStoneWeight(m.getWeight());
						 accessories.setPurcal(m.getPurcost());
						 accessories.setPurPrice(m.getPurcost()/m.getWeight());
						 accessories.setClarity(m.getClartity());
						 accessories.setColor(m.getColor());
						 accessories.setCut(m.getCut());
						 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
							 Credential c=new Credential();
							 c.setId(UuidUtil.get32UUID());
							 c.setProductId(product.getId());
							 c.setAccessorieId(accessories.getId());
							 c.setOrgId(user.getOrgId());
							 c.setCerNo(m.getCernum());
							 c.setCerName("0");
							 c.setStatus("1");
							 c.setCreateUser(user.getAccountId());
							 list.add(c);
						 }
					 }else{
						 if(productUpload.getCertificate()!=null&&!"0".equals(productUpload.getCertificate())){
							 Credential c=new Credential();
							 c.setId(UuidUtil.get32UUID());
							 c.setProductId(product.getId());
							 c.setAccessorieId(accessories.getId());
							 c.setOrgId(user.getOrgId());
							 c.setCerNo(productUpload.getCertificate());
							 c.setCerName("0");
							 c.setStatus("1");
							 c.setCreateUser(user.getAccountId());
							 list.add(c);
						 }
					 }
				}
				result.add(accessories);
			}
			//添加辅件
			if(productUpload.getStonecode1()!=null){
				int count=accessoriesDao.findMax();
				accessories1.setSort(String.valueOf(count+1));
				accessories1.setId(UuidUtil.get32UUID());
				accessories1.setStoneFlag("0");
				accessories1.setProductId(product.getId());
				accessories1.setStatus("1");
				accessories1.setCreateUser(user.getAccountId());
				accessories1.setCreateTime(new Date());
				accessories1.setStoneCode(productUpload.getStonecode1());
				accessories1.setStoneName(productUpload.getStonename1());
				accessories1.setStoneWeight(productUpload.getStoneweight1());
				accessories1.setStoneCount(productUpload.getStonecount1());
				accessories1.setCostPrice(0);
				accessories1.setPurPrice(productUpload.getPurcal1()/productUpload.getStoneweight1());;
				accessories1.setCostCal(0);
				accessories1.setPurcal(productUpload.getPurcal1());
				accessories1.setStoneUnit(productUpload.getJeweler1());
				accessories1.setStonePkgno(productUpload.getStonepkgno1());
				if(productUpload.getStonepkgno1()!=null&&!"0".equals(productUpload.getStonepkgno1())){
					 List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories1.setStoneWeight(m.getWeight());
						 accessories1.setPurcal(m.getPurcost());
						 accessories1.setPurPrice(m.getPurcost()/m.getWeight());
						 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
							 Credential c=new Credential();
							 c.setId(UuidUtil.get32UUID());
							 c.setProductId(product.getId());
							 c.setAccessorieId(accessories.getId());
							 c.setOrgId(user.getOrgId());
							 c.setCerNo(m.getCernum());
							 c.setCerName("0");
							 c.setStatus("1");
							 c.setCreateUser(user.getAccountId());
							 list.add(c);
						 }
					 }else{
						 if(productUpload.getCertificate1()!=null&&!"0".equals(productUpload.getCertificate1())){
							 Credential c1=new Credential();
							 c1.setId(UuidUtil.get32UUID());
							 c1.setProductId(product.getId());
							 c1.setAccessorieId(accessories1.getId());
							 c1.setOrgId(user.getOrgId());
							 c1.setCerNo(productUpload.getCertificate1());
							 c1.setCerName("0");
							 c1.setStatus("1");
							 c1.setCreateUser(user.getAccountId());
							 list.add(c1);
						 }
					 }
				
				}
				 result.add(accessories1);
			}
			//添加辅件
			if(productUpload.getStonecode2()!=null){
				int count=accessoriesDao.findMax();
				accessories2.setSort(String.valueOf(count+1));
				accessories2.setId(UuidUtil.get32UUID());
				accessories2.setStoneFlag("0");
				accessories2.setProductId(product.getId());
				accessories2.setStatus("1");
				accessories2.setCreateUser(user.getAccountId());
				accessories2.setCreateTime(new Date());
				accessories2.setStoneCode(productUpload.getStonecode2());
				accessories2.setStoneName(productUpload.getStonename2());
				accessories2.setStoneWeight(productUpload.getStoneweight2());
				accessories2.setStoneCount(productUpload.getStonecount2());
				accessories2.setCostPrice(0);
				accessories2.setPurPrice(productUpload.getPurcal2()/productUpload.getStoneweight2());;
				accessories2.setCostCal(0);
				accessories2.setPurcal(productUpload.getPurcal2());
				accessories2.setStoneUnit(productUpload.getJeweler2());
				accessories2.setStonePkgno(productUpload.getStonepkgno2());
				//添加证书
				if(productUpload.getStonepkgno2()!=null&&!"0".equals(productUpload.getStonepkgno2())){
					 List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories2.setStoneWeight(m.getWeight());
						 accessories2.setPurcal(m.getPurcost());
						 accessories2.setPurPrice(m.getPurcost()/m.getWeight());
						 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
							 Credential c2=new Credential();
							 c2.setId(UuidUtil.get32UUID());
							 c2.setProductId(product.getId());
							 c2.setAccessorieId(accessories2.getId());
							 c2.setOrgId(user.getOrgId());
							 c2.setCerNo(m.getCernum());
							 c2.setCerName("0");
							 c2.setStatus("1");
							 c2.setCreateUser(user.getAccountId());
							 list.add(c2);
						 }
					 }else{
						 if(productUpload.getCertificate2()!=null&&!"0".equals(productUpload.getCertificate2())){
							 Credential c2=new Credential();
							 c2.setId(UuidUtil.get32UUID());
							 c2.setProductId(product.getId());
							 c2.setAccessorieId(accessories2.getId());
							 c2.setOrgId(user.getOrgId());
							 c2.setCerNo(productUpload.getCertificate2());
							 c2.setCerName("0");
							 c2.setStatus("1");
							 c2.setCreateUser(user.getAccountId());
							 list.add(c2);
						 }
					 }
					
				}
				result.add(accessories2);
			}
			//添加辅件
			if(productUpload.getStonecode3()!=null){
				
				int count=accessoriesDao.findMax();
				accessories3.setSort(String.valueOf(count+1));
				accessories3.setId(UuidUtil.get32UUID());
				accessories3.setStoneFlag("0");
				accessories3.setProductId(product.getId());
				accessories3.setStatus("1");
				accessories3.setCreateUser(user.getAccountId());
				accessories3.setCreateTime(new Date());
				accessories3.setStoneCode(productUpload.getStonecode3());
				accessories3.setStoneName(productUpload.getStonename3());
				accessories3.setStoneWeight(productUpload.getStoneweight3());
				accessories3.setStoneCount(productUpload.getStonecount3());
				accessories3.setCostPrice(0);
				accessories3.setPurPrice(productUpload.getPurcal3()/productUpload.getStoneweight3());;
				accessories3.setCostCal(0);
				accessories3.setPurcal(productUpload.getPurcal3());
				accessories3.setStoneUnit(productUpload.getJeweler3());
				accessories3.setStonePkgno(productUpload.getStonepkgno3());
				//添加证书
				if(productUpload.getStonepkgno3()!=null&&!"0".equals(productUpload.getStonepkgno3())){
					 List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories3.setStoneWeight(m.getWeight());
						 accessories3.setPurcal(m.getPurcost());
						 accessories3.setPurPrice(m.getPurcost()/m.getWeight());
						 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
							 Credential c3=new Credential();
							 c3.setId(UuidUtil.get32UUID());
							 c3.setProductId(product.getId());
							 c3.setAccessorieId(accessories3.getId());
							 c3.setOrgId(user.getOrgId());
							 c3.setCerNo(m.getCernum());
							 c3.setCerName("0");
							 c3.setStatus("1");
							 c3.setCreateUser(user.getAccountId());
							 list.add(c3);
						 }
					 }else{
						 if(productUpload.getCertificate3()!=null&&!"0".equals(productUpload.getCertificate3())){
							 Credential c3=new Credential();
							 c3.setId(UuidUtil.get32UUID());
							 c3.setProductId(product.getId());
							 c3.setAccessorieId(accessories3.getId());
							 c3.setOrgId(user.getOrgId());
							 c3.setCerNo(productUpload.getCertificate3());
							 c3.setCerName("0");
							 c3.setStatus("1");
							 c3.setCreateUser(user.getAccountId());
							 list.add(c3);
						 }
					 }
					 
				}
				result.add(accessories3);
			}
			//添加辅件
			if(productUpload.getStonecode4()!=null){
				int count=accessoriesDao.findMax();
				accessories4.setSort(String.valueOf(count+1));
				accessories4.setId(UuidUtil.get32UUID());
				accessories4.setStoneFlag("0");
				accessories4.setProductId(product.getId());
				accessories4.setStatus("1");
				accessories4.setCreateUser(user.getAccountId());
				accessories4.setCreateTime(new Date());
				accessories4.setStoneCode(productUpload.getStonecode4());
				accessories4.setStoneName(productUpload.getStonename4());
				accessories4.setStoneWeight(productUpload.getStoneweight4());
				accessories4.setStoneCount(productUpload.getStonecount4());
				accessories4.setCostPrice(0);
				accessories4.setPurPrice(productUpload.getPurcal4()/productUpload.getStoneweight4());;
				accessories4.setCostCal(0);
				accessories4.setPurcal(productUpload.getPurcal4());
				accessories4.setStoneUnit(productUpload.getJeweler4());
				accessories4.setStonePkgno(productUpload.getStonepkgno4());
				//添加证书
				if(productUpload.getStonepkgno4()!=null&&!"0".equals(productUpload.getStonepkgno4())){
					 List<MaterialVO> material=materialDao.queryByCode(productUpload.getStonepkgno());
					 if(material.size()>0){
						 MaterialVO m=material.get(0);
						 accessories4.setStoneWeight(m.getWeight());
						 accessories4.setPurcal(m.getPurcost());
						 accessories4.setPurPrice(m.getPurcost()/m.getWeight());
						 if(m.getCernum()!=null&&!"0".equals(m.getCernum())){
							 Credential c4=new Credential();
							 c4.setId(UuidUtil.get32UUID());
							 c4.setProductId(product.getId());
							 c4.setAccessorieId(accessories4.getId());
							 c4.setOrgId(user.getOrgId());
							 c4.setCerNo(m.getCernum());
							 c4.setCerName("0");
							 c4.setStatus("1");
							 c4.setCreateUser(user.getAccountId());
							 list.add(c4);
						 }
					 }else{
						 if(productUpload.getCertificate4()!=null&&!"0".equals(productUpload.getCertificate4())){
							 Credential c4=new Credential();
							 c4.setId(UuidUtil.get32UUID());
							 c4.setProductId(product.getId());
							 c4.setAccessorieId(accessories4.getId());
							 c4.setOrgId(user.getOrgId());
							 c4.setCerNo(productUpload.getCertificate4());
							 c4.setCerName("0");
							 c4.setStatus("1");
							 c4.setCreateUser(user.getAccountId());
							 list.add(c4);
						 }
					 }
					
					
				}
				result.add(accessories4);
			}
			data.add(product);
			purenterydetailList.add(purenterydetail);
			
		}
		purentery.setTotalnum(0);
		purentery.setTotalcount(totalcount);
		purentery.setPurcost(purcost);
		purentery.setCheckcost(checkcost);
		purentery.setFinacost(finacost);
		purentery.setTotalprice(totalprice);
		purentery.setDiffweight(0.0);
		purentery.setWarehouseid(pro.getWarehouseId());
		purentery.setLocationid(pro.getLocationId());
		purenteryDao.insert(purentery);
		purenterydetailDao.batchInsert(purenterydetailList);
		dao.batchInsertProduct(data); 
		if(list.size()>0){
			credentialDao.batchInsert(list);
		}
		if(result.size()>0){
			accessoriesDao.batchAccessories(result);
		}
		
	}

	@Override
	public List<Product> findForInventory(Product product) {
		return dao.findForInventory(product);
	}

	@Override
	public Materialcome findnoticeNo(String noticeNo) {
		
		return dao.findnoticeNo(noticeNo);
	}

	@Override
	public List<Product> queryProductNoticeno(String noticeNo) {
		List<Product> list =dao.queryProductNoticeno(noticeNo);
		return list;
	}

	@Override
	public int findProductNumByMoCode(Product o) {
		return dao.findProductNumByMoCode(o);
	}

	@Override
	public List<Product> findByProductCode(String code) {
		return dao.findByProductCode(code);
	}

	@Override
	public List<Product> queryLockedCodeInList(List<OrderReturnDetail> list) {
		return dao.queryLockedCodeInList(list);
	}

		
}
