package com.jy.common.utils.weixin.vo.message;

import java.util.*;

public class ImageMsg extends BasicMsg
{
    private String picUrl;
    private String mediaId;
    
    public ImageMsg() {
        this.msgType = "image";
    }
    
    public ImageMsg(final Map<String, String> values) {
        super(values);
        this.picUrl = values.get("picUrl");
        this.mediaId = values.get("mediaId");
    }
    
    public String getPicUrl() {
        return this.picUrl;
    }
    
    public void setPicUrl(final String picUrl) {
        this.picUrl = picUrl;
    }
    
    public String getMediaId() {
        return this.mediaId;
    }
    
    public void setMediaId(final String mediaId) {
        this.mediaId = mediaId;
    }
    
    @Override
    public String toString() {
        return "ImageMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", msgId=" + this.msgId + ", picUrl=" + this.picUrl + ", mediaId=" + this.mediaId + "]";
    }
}
