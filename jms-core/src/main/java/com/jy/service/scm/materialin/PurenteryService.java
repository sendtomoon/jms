package com.jy.service.scm.materialin;

import java.util.List;
import java.util.Map;
import com.jy.entity.scm.materialin.Purentery;
import com.jy.entity.scm.materialin.Purenterydetail;
import com.jy.service.base.BaseService;

public interface PurenteryService extends BaseService<Purentery>{
	/**
	 * 增加入库单号和入库明细(物料)
	 * @param pageMaterialin
	 */
	void insertMaterialin(String myData,Purentery purentery);
	
	/**
	 * 增加入库单号和入库明细(商品)
	 * @param pageMaterialin
	 */
	void insertProductin(String myData,Purentery purentery);
	
	Map<String, Object> view(Purentery purentery);
	
	String checkMaterialin(Purentery purentery); 
	
	String checkProductin(Purentery purentery); 
	
	Purenterydetail findPurenterydetail(Purenterydetail purenterydetail);
	
	String del(Purentery purentery);
	
	public Map<String, Object> proDel(String cheks);
	void updateMaterialin(String myData, Purentery purenterys);
	
	void updateProductin(String myData, Purentery purenterys);
	
	List<Purenterydetail> findSetMaterialin(Purentery purentery);
	
	List<Purenterydetail> findSetProductin(Purentery purentery);
	
	/*void delDetail(String chks);*/
	
	/**
	 * 判断原料代码是否存在
	 * @param code
	 * @return
	 */
	List<Purenterydetail> findMaterialCode(Purentery purentery);
	
	/**
	 * 判断商品代码是否存在
	 * @param code
	 * @return
	 */
	List<Purenterydetail> findProductCode(Purentery purentery);
}
