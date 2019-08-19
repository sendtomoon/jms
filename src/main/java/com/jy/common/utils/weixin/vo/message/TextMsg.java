package com.jy.common.utils.weixin.vo.message;

import com.jy.common.utils.weixin.vo.event.*;
import java.util.*;

public class TextMsg extends BasicMsg
{
    private String content;
    
    public TextMsg() {
        this.msgType = "text";
    }
    
    public TextMsg(final BasicEvent event) {
        super(event);
        this.msgType = "text";
    }
    
    public TextMsg(final BasicMsg msg) {
        super(msg);
        this.msgType = "text";
    }
    
    public TextMsg(final Map<String, String> values) {
        super(values);
        this.content = values.get("content");
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(final String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "TextMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", content=" + this.content + ", msgId=" + this.msgId + "]";
    }
}
