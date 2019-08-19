package com.jy.service.scm.moudle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jy.common.utils.base.UuidUtil;
import com.jy.entity.system.attribute.AttributeValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.jy.entity.scm.moudle.MoudleDetail;
import com.jy.dao.system.attribute.AttributeDao;
import com.jy.dao.scm.moudle.MoudleDetailDao;
import com.jy.service.base.BaseServiceImp;
import com.jy.service.scm.attachment.UploadFileService;

@Service("MoudleDetailService")
public class MoudleDetailServiceImp extends BaseServiceImp<MoudleDetail> implements MoudleDetailService {
	@Autowired
	private MoudleDetailDao dao;
	@Autowired
	private AttributeDao attributeDao;
	@Autowired
	private UploadFileService fileService;
	@Override
	public void updateScmMoudleDetailState(MoudleDetail moudleDetail) {
		dao.updateScmMoudleDetailState(moudleDetail);
	}

	@Override
	public void batchUpdateScmMoudleDetailState(List<MoudleDetail> list, MoudleDetail moudle) {
		dao.batchUpdateScmMoudleDetailState(list, moudle);
	}

	@Override
	@Transactional
	public int insertDetail(MoudleDetail moudleDetail,HttpServletRequest request) throws IOException {
		//主要工厂最多设置1个
		int count=1;
		if(!StringUtils.isEmpty(moudleDetail.getMoudleid()) && moudleDetail.getMajorFlag().equals("1")
				&& dao.queryMajorFactoryNum(moudleDetail.getMoudleid())>=1){
			return 2;
		}
		dao.insert(moudleDetail);
		if(!CollectionUtils.isEmpty(moudleDetail.getAttrValues())){
			List<AttributeValue> list=new ArrayList<AttributeValue>();
			for(AttributeValue a:moudleDetail.getAttrValues()){
				a.setId(UuidUtil.get32UUID());
				a.setBusinessId(moudleDetail.getId());
				list.add(a);
			}
			attributeDao.addAttrsValue(list);
		}
		if(!StringUtils.isEmpty(moudleDetail.getImgId())){
			fileService.saveUploadFileMore(moudleDetail.getImgId(), moudleDetail.getId(), request);
		}
		return count;
	}

	@Override
	@Transactional
	public int updateDetail(MoudleDetail moudleDetail,HttpServletRequest request) throws IOException {
		int count=1;
		if(!StringUtils.isEmpty(moudleDetail.getMajorFlag()) && moudleDetail.getMajorFlag().equals("1")){
			int num = dao.queryMajorFactoryNum(moudleDetail.getMoudleid());
			MoudleDetail md = dao.findMoudleById(moudleDetail.getId());
			if(!StringUtils.isEmpty(md) && num>=1 && !moudleDetail.getMajorFlag().equals(md.getMajorFlag())){
				return 2;
			}
		}
		dao.update(moudleDetail);
		if(!CollectionUtils.isEmpty(moudleDetail.getAttrValues())){
			List<AttributeValue> lista=new ArrayList<AttributeValue>();
			List<AttributeValue> listu=new ArrayList<AttributeValue>();
			for(AttributeValue a:moudleDetail.getAttrValues()){
				if(StringUtils.isEmpty(a.getId())){
					a.setId(UuidUtil.get32UUID());
					a.setBusinessId(moudleDetail.getId());
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
		if(!StringUtils.isEmpty(moudleDetail.getImgId())){
			fileService.saveUploadFileMore(moudleDetail.getImgId(), moudleDetail.getId(), request);
		}
		return count;
	}

	@Override
	public List<MoudleDetail> queryModelCode(MoudleDetail o) {
		return dao.queryModelCode(o);
	}

}
