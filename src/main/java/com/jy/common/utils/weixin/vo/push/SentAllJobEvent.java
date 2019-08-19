package com.jy.common.utils.weixin.vo.push;

import java.util.*;

public class SentAllJobEvent extends SentTmlJobEvent
{
    private int totalCnt;
    private int filterCnt;
    private int sentCnt;
    private int errorCnt;
    
    public SentAllJobEvent() {
    }
    
    public SentAllJobEvent(final Map<String, String> values) {
        super(values);
        this.totalCnt = Integer.parseInt(values.get("totalCount"));
        this.filterCnt = Integer.parseInt(values.get("filterCount"));
        this.sentCnt = Integer.parseInt(values.get("sentCount"));
        this.errorCnt = Integer.parseInt(values.get("errorCount"));
    }
    
    public int getTotalCnt() {
        return this.totalCnt;
    }
    
    public void setTotalCnt(final int totalCnt) {
        this.totalCnt = totalCnt;
    }
    
    public int getFilterCnt() {
        return this.filterCnt;
    }
    
    public void setFilterCnt(final int filterCnt) {
        this.filterCnt = filterCnt;
    }
    
    public int getSentCnt() {
        return this.sentCnt;
    }
    
    public void setSentCnt(final int sentCnt) {
        this.sentCnt = sentCnt;
    }
    
    public int getErrorCnt() {
        return this.errorCnt;
    }
    
    public void setErrorCnt(final int errorCnt) {
        this.errorCnt = errorCnt;
    }
    
    @Override
    public String toString() {
        return "SenAllJobEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", eventKey=" + this.eventKey + ", totalCnt=" + this.totalCnt + ", filterCnt=" + this.filterCnt + ", sentCnt=" + this.sentCnt + ", errorCnt=" + this.errorCnt + "]";
    }
}
