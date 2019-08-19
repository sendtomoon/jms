package com.jy.common.utils.weixin.vo.event;

import java.util.*;

public class ScanCodeEvent extends BasicEvent
{
    private String scanType;
    private String scanResult;
    
    public ScanCodeEvent() {
    }
    
    public ScanCodeEvent(final Map<String, String> values) {
        super(values);
        this.scanType = values.get("scanType");
        this.scanResult = values.get("scanResult");
    }
    
    public String getScanType() {
        return this.scanType;
    }
    
    public void setScanType(final String scanType) {
        this.scanType = scanType;
    }
    
    public String getScanResult() {
        return this.scanResult;
    }
    
    public void setScanResult(final String scanResult) {
        this.scanResult = scanResult;
    }
    
    @Override
    public String toString() {
        return "ScanCodeEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", eventKey=" + this.eventKey + ", scanType=" + this.scanType + ", scanResult=" + this.scanResult + "]";
    }
}
