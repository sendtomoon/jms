package com.jy.common.utils.weixin.vo.event;

import java.util.*;

public class LocationEvent extends BasicEvent
{
    private String latitude;
    private String longitude;
    private String precision;
    
    public LocationEvent() {
        this.event = "LOCATION";
    }
    
    public LocationEvent(final Map<String, String> values) {
        super(values);
        this.latitude = values.get("latitude");
        this.longitude = values.get("longitude");
        this.precision = values.get("precision");
        this.event = "LOCATION";
    }
    
    public String getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }
    
    public String getPrecision() {
        return this.precision;
    }
    
    public void setPrecision(final String precision) {
        this.precision = precision;
    }
    
    @Override
    public String toString() {
        return "LocationEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", precision=" + this.precision + "]";
    }
}
