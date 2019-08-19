package com.jy.common.utils.weixin.vo.event;

import java.util.*;

public class ScanEvent extends BasicEvent
{
    private String ticket;
    
    public ScanEvent() {
    }
    
    public ScanEvent(final Map<String, String> values) {
        super(values);
    }
    
    public String getTicket() {
        return this.ticket;
    }
    
    public void setTicket(final String ticket) {
        this.ticket = ticket;
    }
    
    @Override
    public String toString() {
        return "ScanEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", eventKey=" + this.eventKey + ", ticket=" + this.ticket + "]";
    }
}
