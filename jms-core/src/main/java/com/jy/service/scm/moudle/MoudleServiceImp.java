package com.jy.service.scm.moudle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.jy.common.utils.base.UuidUtil;
import com.jy.entity.base.SelectData;
import com.jy.entity.system.attribute.AttributeValue;
import com.jy.entity.scm.moudle.Moudle;
import com.jy.dao.scm.moudle.MoudleDao;
import com.jy.dao.system.attribute.AttributeDao;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.attachment.UploadFileService;

@Service("MoudleService")
public class MoudleServiceImp extends BaseServiceImp<Moudle> implements MoudleService {
	@Autowired
	private MoudleDao dao;
	@Autowired
	private AttributeDao attributeDao;
	@Autowired
	private UploadFileService fileService;
	
	@Override
	public void updateScmMoudleState(Moudle o) {
		dao.updateScmMoudleState(o);
	}

	@Override
	public void batchUpdateCategoryState(List<Moudle> list, Moudle moudle) {
		dao.batchUpdateCategoryState(list, moudle);
	}

	@Override
	public List<SelectData> findRoleList4Select() {
		return dao.findRoleList4Select();
	}

	@Override
	@Transactional
	public void insertMoudle(Moudle moudle,HttpServletRequest request) throws Exception {
		dao.insert(moudle);
		if(!CollectionUtils.isEmpty(moudle.getAttrValues())){
			List<AttributeValue> list=new ArrayList<AttributeValue>();
			for(AttributeValue a:moudle.getAttrValues()){
				a.setId(UuidUtil.get32UUID());
				a.setBusinessId(moudle.getId());
				list.add(a);
			}
			attributeDao.addAttrsValue(list);
		}
		if(!StringUtils.isEmpty(moudle.getImgId())){
			
			fileService.saveUploadFileMore(moudle.getImgId(), moudle.getId(), request);
		}
	}

	@Override
	@Transactional
	public void updateMoudle(Moudle moudle,HttpServletRequest request) throws IOException {
		dao.update(moudle);
		if(!CollectionUtils.isEmpty(moudle.getAttrValues())){
			List<AttributeValue> lista=new ArrayList<AttributeValue>();
			List<AttributeValue> listu=new ArrayList<AttributeValue>();
			for(AttributeValue a:moudle.getAttrValues()){
				if(StringUtils.isEmpty(a.getId())){
					a.setId(UuidUtil.get32UUID());
					a.setBusinessId(moudle.getId());
					lista.add(a);
				}else{
					listu.add(a);
				}
			}
			if(!CollectionUtils.isEmpty(lista)){
				attributeDao.addAttrsValue(lista);
			}
			if(!CollectionUtils.isEmpty(listu)){
				attributeDao.updateAttrsValue(listu);
			}
		}
		if(!StringUtils.isEmpty(moudle.getImgId())){
			fileService.saveUploadFileMore(moudle.getImgId(), moudle.getId(), request);
		}
	}

	@Override
	public List<Moudle> findScmMoudleByCode(String code) {
		return dao.findScmMoudleByCode(code);
	}

	@Override
	public List<Moudle> findAllList(Moudle moudle) {
		List<Moudle> list = new ArrayList<Moudle>();
		list=dao.findAllList(moudle);
		
		return list;
	}
	
}
