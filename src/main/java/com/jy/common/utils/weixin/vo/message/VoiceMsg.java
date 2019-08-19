package com.jy.common.utils.weixin.vo.message;

import com.jy.common.utils.weixin.vo.event.*;
import java.util.*;

public class VoiceMsg extends BasicMsg
{
    private String mediaId;
    private String format;
    private String recognition;
    
    public VoiceMsg() {
        this.msgType = "voice";
    }
    
    public VoiceMsg(final BasicEvent event) {
        super(event);
        this.msgType = "voice";
    }
    
    public VoiceMsg(final BasicMsg msg) {
        super(msg);
        this.msgType = "voice";
    }
    
    public VoiceMsg(final Map<String, String> values) {
        super(values);
        this.mediaId = values.get("mediaId");
        this.format = values.get("format");
        this.recognition = values.get("recognition");
    }
    
    public String getMediaId() {
        return this.mediaId;
    }
    
    public void setMediaId(final String mediaId) {
        this.mediaId = mediaId;
    }
    
    public String getFormat() {
        return this.format;
    }
    
    public void setFormat(final String format) {
        this.format = format;
    }
    
    public String getRecognition() {
        return this.recognition;
    }
    
    public void setRecognition(final String recognition) {
        this.recognition = recognition;
    }
    
    @Override
    public String toString() {
        return "VoiceMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", msgId=" + this.msgId + ", mediaId=" + this.mediaId + ", format=" + this.format + ", recognition=" + this.recognition + "]";
    }
}
