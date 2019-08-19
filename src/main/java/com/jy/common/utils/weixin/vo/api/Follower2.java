package com.jy.common.utils.weixin.vo.api;

public class Follower2
{
    private String openid;
    private String lang;
    
    public Follower2() {
    }
    
    public Follower2(final String openid) {
        this(openid, "zh_CN");
    }
    
    public Follower2(final String openid, final String lang) {
        this.openid = openid;
        this.lang = lang;
    }
    
    public String getOpenid() {
        return this.openid;
    }
    
    public void setOpenid(final String openid) {
        this.openid = openid;
    }
    
    public String getLang() {
        return this.lang;
    }
    
    public void setLang(final String lang) {
        this.lang = lang;
    }
    
    @Override
    public String toString() {
        return "Follower2 [openid=" + this.openid + ", lang=" + this.lang + "]";
    }
}
