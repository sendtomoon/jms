package com.jy.common.utils.weixin.vo.api;

import org.nutz.json.*;

public class Follower
{
    private int subscribe;
    private String openid;
    private String nickname;
    private int sex;
    private String city;
    private String country;
    private String province;
    private String language;
    private String headimgurl;
    @JsonField("subscribe_time")
    private long subscribeTime;
    private String unionid;
    private String remark;
    private int groupid;
    
    public int getSubscribe() {
        return this.subscribe;
    }
    
    public void setSubscribe(final int subscribe) {
        this.subscribe = subscribe;
    }
    
    public String getOpenid() {
        return this.openid;
    }
    
    public void setOpenid(final String openid) {
        this.openid = openid;
    }
    
    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }
    
    public int getSex() {
        return this.sex;
    }
    
    public void setSex(final int sex) {
        this.sex = sex;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(final String city) {
        this.city = city;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(final String country) {
        this.country = country;
    }
    
    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(final String province) {
        this.province = province;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public void setLanguage(final String language) {
        this.language = language;
    }
    
    public String getHeadimgurl() {
        return this.headimgurl;
    }
    
    public void setHeadimgurl(final String headimgurl) {
        this.headimgurl = headimgurl;
    }
    
    public long getSubscribeTime() {
        return this.subscribeTime;
    }
    
    public void setSubscribeTime(final long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
    
    public String getUnionid() {
        return this.unionid;
    }
    
    public void setUnionid(final String unionid) {
        this.unionid = unionid;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public int getGroupid() {
        return this.groupid;
    }
    
    public void setGroupid(final int groupid) {
        this.groupid = groupid;
    }
    
    @Override
    public String toString() {
        return "Follower [subscribe=" + this.subscribe + ", openid=" + this.openid + ", nickname=" + this.nickname + ", sex=" + this.sex + ", city=" + this.city + ", country=" + this.country + ", province=" + this.province + ", language=" + this.language + ", headimgurl=" + this.headimgurl + ", subscribeTime=" + this.subscribeTime + ", unionid=" + this.unionid + ", remark=" + this.remark + ", groupid=" + this.groupid + "]";
    }
}
