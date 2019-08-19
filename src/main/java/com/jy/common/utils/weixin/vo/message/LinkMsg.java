package com.jy.common.utils.weixin.vo.message;

import java.util.*;

public class LinkMsg extends BasicMsg
{
    private String title;
    private String description;
    private String url;
    
    public LinkMsg() {
        this.msgType = "link";
    }
    
    public LinkMsg(final Map<String, String> values) {
        super(values);
        this.title = values.get("title");
        this.description = values.get("description");
        this.url = values.get("url");
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
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "LinkMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", msgId=" + this.msgId + ", title=" + this.title + ", description=" + this.description + ", url=" + this.url + "]";
    }
}
