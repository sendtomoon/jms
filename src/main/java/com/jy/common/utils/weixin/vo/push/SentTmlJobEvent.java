package com.jy.common.utils.weixin.vo.push;

import com.jy.common.utils.weixin.vo.event.*;
import java.util.*;

public class SentTmlJobEvent extends BasicEvent
{
    private String msgId;
    private String status;
    
    public SentTmlJobEvent() {
    }
    
    public SentTmlJobEvent(final Map<String, String> values) {
        super(values);
        this.msgId = values.get("msgId");
        this.status = values.get("status");
    }
    
    public String getMsgId() {
        return this.msgId;
    }
    
    public void setMsgId(final String msgId) {
        this.msgId = msgId;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "TemplateJobEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", eventKey=" + this.eventKey + ", msgId=" + this.msgId + ", status=" + this.status + "]";
    }
}
