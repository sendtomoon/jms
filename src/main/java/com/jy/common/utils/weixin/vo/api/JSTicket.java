package com.jy.common.utils.weixin.vo.api;

import org.nutz.json.*;
import org.nutz.lang.*;

public class JSTicket
{
    private String ticket;
    @JsonField("expires_in")
    private long expiresIn;
    
    public String getTicket() {
        return this.ticket;
    }
    
    public void setTicket(final String ticket) {
        this.ticket = ticket;
    }
    
    public long getExpiresIn() {
        return this.expiresIn;
    }
    
    public void setExpiresIn(final long expiresIn) {
        this.expiresIn = (expiresIn - 30L) * 1000L;
    }
    
    public boolean isAvailable() {
        return !Lang.isEmpty((Object)this.ticket) || this.expiresIn >= System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return "JSTicket [ticket=" + this.ticket + ", expiresIn=" + this.expiresIn + "]";
    }
}
