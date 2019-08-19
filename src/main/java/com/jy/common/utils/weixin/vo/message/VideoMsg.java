package com.jy.common.utils.weixin.vo.message;

import com.jy.common.utils.weixin.vo.event.*;
import java.util.*;

public class VideoMsg extends BasicMsg
{
    private String mediaId;
    private String thumbMediaId;
    private String title;
    private String description;
    
    public VideoMsg() {
        this.msgType = "video";
    }
    
    public VideoMsg(final BasicEvent event) {
        super(event);
        this.msgType = "video";
    }
    
    public VideoMsg(final BasicMsg msg) {
        super(msg);
        this.msgType = "video";
    }
    
    public VideoMsg(final Map<String, String> values) {
        super(values);
        this.mediaId = values.get("mediaId");
        this.thumbMediaId = values.get("thumbMediaId");
    }
    
    public String getMediaId() {
        return this.mediaId;
    }
    
    public void setMediaId(final String mediaId) {
        this.mediaId = mediaId;
    }
    
    public String getThumbMediaId() {
        return this.thumbMediaId;
    }
    
    public void setThumbMediaId(final String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "VideoMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", msgId=" + this.msgId + ", mediaId=" + this.mediaId + ", thumbMediaId=" + this.thumbMediaId + ", title=" + this.title + ", description=" + this.description + "]";
    }
}
