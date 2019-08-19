package com.jy.common.utils.weixin.vo.event;

import org.nutz.json.*;
import java.util.*;

public class SendPhotosEvent extends MenuEvent
{
    private SendPicsInfo sendPicsInfo;
    
    public SendPhotosEvent() {
    }
    
    public SendPhotosEvent(final Map<String, String> values) {
        super(values);
        final List<PicItem> items = (List<PicItem>)Json.fromJsonAsList((Class)PicItem.class, (CharSequence)values.get("picList"));
        this.sendPicsInfo = new SendPicsInfo(Integer.parseInt(values.get("count")), items);
    }
    
    public SendPicsInfo getSendPicsInfo() {
        return this.sendPicsInfo;
    }
    
    public void setSendPicsInfo(final SendPicsInfo sendPicsInfo) {
        this.sendPicsInfo = sendPicsInfo;
    }
    
    @Override
    public String toString() {
        return "ScanSysPhotoEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", eventKey=" + this.eventKey + ", sendPicsInfo=" + this.sendPicsInfo + "]";
    }
}
