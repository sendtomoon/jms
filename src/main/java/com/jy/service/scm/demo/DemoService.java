package com.jy.service.scm.demo;

import com.jy.entity.base.BatchVO;
import com.jy.entity.scm.demo.Demo;
import com.jy.service.base.BaseService;

public interface DemoService extends BaseService<Demo>{

	void saveBatch(BatchVO<Demo> batchVO);

}
