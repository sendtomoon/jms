package com.jy.service.oa.leave;

import com.jy.service.base.*;
import com.jy.entity.oa.leave.*;

public interface LeaveService extends BaseService<Leave>
{
    Leave findLeaveByPId(final String p0);
    
    void updateRejectReason(final String p0, final String p1);
    
    void updateDescription(final String p0, final String p1);
}
