package com.jy.common.utils.weixin.vo.api;

import org.nutz.json.*;

public class QRTicket
{
    private String ticket;
    @JsonField("expire_seconds")
    private int expireSeconds;
    private String url;
    
    public String getTicket() {
        return this.ticket;
    }
    
    public void setTicket(final String ticket) {
        this.ticket = ticket;
    }
    
    public int getExpireSeconds() {
        return this.expireSeconds;
    }
    
    public void setExpireSeconds(final int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    @Override
    public String toString() {
        return "QRTicket [ticket=" + this.ticket + ", expireSeconds=" + this.expireSeconds + ", url=" + this.url + "]";
    }
}
