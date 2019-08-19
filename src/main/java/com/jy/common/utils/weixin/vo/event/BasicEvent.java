package com.jy.common.utils.weixin.vo.event;

import java.util.*;

public class BasicEvent
{
    protected String toUserName;
    protected String fromUserName;
    protected int createTime;
    protected String msgType;
    protected String event;
    protected String eventKey;
    
    public BasicEvent() {
        this.msgType = "event";
    }
    
    public BasicEvent(final Map<String, String> values) {
        this.fromUserName = values.get("fromUserName");
        this.toUserName = values.get("toUserName");
        this.createTime = Integer.parseInt(values.get("createTime"));
        this.msgType = "event";
        this.event = values.get("event");
        this.eventKey = values.get("eventKey");
    }
    
    public String getToUserName() {
        return this.toUserName;
    }
    
    public void setToUserName(final String toUserName) {
        this.toUserName = toUserName;
    }
    
    public String getFromUserName() {
        return this.fromUserName;
    }
    
    public void setFromUserName(final String fromUserName) {
        this.fromUserName = fromUserName;
    }
    
    public int getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(final int createTime) {
        this.createTime = createTime;
    }
    
    public String getMsgType() {
        return this.msgType;
    }
    
    public void setMsgType(final String msgType) {
        this.msgType = msgType;
    }
    
    public String getEvent() {
        return this.event;
    }
    
    public void setEvent(final String event) {
        this.event = event;
    }
    
    public String getEventKey() {
        return this.eventKey;
    }
    
    public void setEventKey(final String eventKey) {
        this.eventKey = eventKey;
    }
}
