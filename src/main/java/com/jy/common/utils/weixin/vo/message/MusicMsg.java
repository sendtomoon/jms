package com.jy.common.utils.weixin.vo.message;

import com.jy.common.utils.weixin.vo.event.*;
import java.util.*;

public class MusicMsg extends BasicMsg
{
    private String title;
    private String description;
    private String musicUrl;
    private String HQMusicUrl;
    private String thumbMediaId;
    
    public MusicMsg() {
        this.msgType = "music";
    }
    
    public MusicMsg(final BasicEvent event) {
        super(event);
        this.msgType = "music";
    }
    
    public MusicMsg(final BasicMsg msg) {
        super(msg);
        this.msgType = "music";
    }
    
    public MusicMsg(final Map<String, String> values) {
        super(values);
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
    
    public String getMusicUrl() {
        return this.musicUrl;
    }
    
    public void setMusicUrl(final String musicUrl) {
        this.musicUrl = musicUrl;
    }
    
    public String getHQMusicUrl() {
        return this.HQMusicUrl;
    }
    
    public void setHQMusicUrl(final String hQMusicUrl) {
        this.HQMusicUrl = hQMusicUrl;
    }
    
    public String getThumbMediaId() {
        return this.thumbMediaId;
    }
    
    public void setThumbMediaId(final String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
    
    @Override
    public String toString() {
        return "MusicMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", msgId=" + this.msgId + ", title=" + this.title + ", description=" + this.description + ", musicUrl=" + this.musicUrl + ", HQMusicUrl=" + this.HQMusicUrl + ", thumbMediaId=" + this.thumbMediaId + "]";
    }
}
