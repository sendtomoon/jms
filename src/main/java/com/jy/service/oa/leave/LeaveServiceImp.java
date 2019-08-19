package com.jy.service.oa.leave;

import com.jy.service.base.*;
import com.jy.entity.oa.leave.*;
import org.springframework.stereotype.*;
import com.jy.dao.oa.leave.*;
import java.util.*;

@Service("OaLeaveService")
public class LeaveServiceImp extends BaseServiceImp<Leave> implements LeaveService
{
    @Override
    public Leave findLeaveByPId(final String pId) {
        return ((LeaveDao)this.baseDao).findLeaveByPId(pId);
    }
    
    @Override
    public void updateRejectReason(final String pId, final String rejectReason) {
        final Leave l = new Leave();
        l.setpId(pId);
        l.setRejectReason(rejectReason);
        l.setUpdateTime(new Date());
        ((LeaveDao)this.baseDao).updateRejectReason(l);
    }
    
    @Override
    public void updateDescription(final String pId, final String description) {
        final Leave l = new Leave();
        l.setpId(pId);
        l.setDescription(description);
        l.setUpdateTime(new Date());
        ((LeaveDao)this.baseDao).updateDescription(l);
    }
}
