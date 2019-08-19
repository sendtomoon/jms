package com.jy.common.utils.weixin.vo.api;

import org.nutz.json.*;
import org.nutz.lang.*;

public class AccessToken
{
    @JsonField("access_token")
    private String accessToken;
    @JsonField("expires_in")
    private long expiresIn;
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }
    
    public long getExpiresIn() {
        return this.expiresIn;
    }
    
    public void setExpiresIn(final long expiresIn) {
        this.expiresIn = (expiresIn - 30L) * 1000L;
    }
    
    public boolean isAvailable() {
        return !Lang.isEmpty((Object)this.accessToken) || this.expiresIn >= System.currentTimeMillis();
    }
    
    @Override
    public String toString() {
        return "AccessToken [accessToken=" + this.accessToken + ", expiresIn=" + this.expiresIn + "]";
    }
}
