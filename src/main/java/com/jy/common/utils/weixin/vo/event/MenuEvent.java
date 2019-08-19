package com.jy.common.utils.weixin.vo.event;

import java.util.*;

public class MenuEvent extends BasicEvent
{
    public MenuEvent() {
    }
    
    public MenuEvent(final Map<String, String> values) {
        super(values);
    }
    
    @Override
    public String toString() {
        return "MenuEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", eventKey=" + this.eventKey + "]";
    }
}
