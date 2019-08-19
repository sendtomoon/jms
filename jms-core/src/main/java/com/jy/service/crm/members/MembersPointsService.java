package com.jy.service.crm.members;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.jy.entity.crm.members.PointsDetail;
import com.jy.service.base.BaseService;

public interface MembersPointsService extends BaseService<PointsDetail>{
	public Map<String, Object> updatePoints(PointsDetail pointsDetail,HttpServletRequest request);
}
