package com.jy.common.utils.weixin.vo.message;

import com.jy.common.utils.weixin.vo.event.*;
import java.util.*;

public class BasicMsg
{
    protected String toUserName;
    protected String fromUserName;
    protected int createTime;
    protected String msgType;
    protected long msgId;
    
    public BasicMsg() {
        this.createTime = (int)(Object)Long.valueOf(System.currentTimeMillis() / 1000L);
    }
    
    public BasicMsg(final BasicMsg msg) {
        this();
        this.fromUserName = msg.getFromUserName();
        this.toUserName = msg.getToUserName();
    }
    
    public BasicMsg(final BasicEvent event) {
        this();
        this.fromUserName = event.getFromUserName();
        this.toUserName = event.getToUserName();
    }
    
    public BasicMsg(final Map<String, String> values) {
        this.fromUserName = values.get("fromUserName");
        this.toUserName = values.get("toUserName");
        this.createTime = Integer.parseInt(values.get("createTime"));
        this.msgType = values.get("msgType");
        this.msgId = Long.parseLong(values.get("msgId"));
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
    
    public long getMsgId() {
        return this.msgId;
    }
    
    public void setMsgId(final long msgId) {
        this.msgId = msgId;
    }
}
