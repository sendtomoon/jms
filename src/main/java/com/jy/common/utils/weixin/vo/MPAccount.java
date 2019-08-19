package com.jy.common.utils.weixin.vo;

public class MPAccount
{
    private String mpId;
    private String nickName;
    private String appId;
    private String appSecret;
    private String token;
    private String AESKey;
    private String mpType;
    private boolean pass;
    private boolean turing;
    
    public String getMpId() {
        return this.mpId;
    }
    
    public void setMpId(final String mpId) {
        this.mpId = mpId;
    }
    
    public String getNickName() {
        return this.nickName;
    }
    
    public void setNickName(final String nickName) {
        this.nickName = nickName;
    }
    
    public String getAppId() {
        return this.appId;
    }
    
    public void setAppId(final String appId) {
        this.appId = appId;
    }
    
    public String getAppSecret() {
        return this.appSecret;
    }
    
    public void setAppSecret(final String appSecret) {
        this.appSecret = appSecret;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public void setToken(final String token) {
        this.token = token;
    }
    
    public String getAESKey() {
        return this.AESKey;
    }
    
    public void setAESKey(final String aESKey) {
        this.AESKey = aESKey;
    }
    
    public String getMpType() {
        return this.mpType;
    }
    
    public void setMpType(final String mpType) {
        this.mpType = mpType;
    }
    
    public boolean isPass() {
        return this.pass;
    }
    
    public void setPass(final boolean pass) {
        this.pass = pass;
    }
    
    @Override
    public String toString() {
        return "MPAccount [mpId=" + this.mpId + ", nickName=" + this.nickName + ", appId=" + this.appId + ", appSecret=" + this.appSecret + ", token=" + this.token + ", AESKey=" + this.AESKey + ", mpType=" + this.mpType + ", pass=" + this.pass + "]";
    }
    
    public boolean isTuring() {
        return this.turing;
    }
    
    public void setTuring(final boolean turing) {
        this.turing = turing;
    }
}
