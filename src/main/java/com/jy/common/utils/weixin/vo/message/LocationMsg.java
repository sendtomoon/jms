package com.jy.common.utils.weixin.vo.message;

import java.util.*;

public class LocationMsg extends BasicMsg
{
    private String x;
    private String y;
    private int scale;
    private String label;
    
    public LocationMsg() {
        this.msgType = "location";
    }
    
    public LocationMsg(final Map<String, String> values) {
        super(values);
        this.x = values.get("locationX");
        this.y = values.get("locationY");
        this.scale = Integer.parseInt(values.get("scale"));
        this.label = values.get("label");
    }
    
    public String getX() {
        return this.x;
    }
    
    public void setX(final String x) {
        this.x = x;
    }
    
    public String getY() {
        return this.y;
    }
    
    public void setY(final String y) {
        this.y = y;
    }
    
    public int getScale() {
        return this.scale;
    }
    
    public void setScale(final int scale) {
        this.scale = scale;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public void setLabel(final String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return "LocationMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", msgId=" + this.msgId + ", x=" + this.x + ", y=" + this.y + ", scale=" + this.scale + ", label=" + this.label + "]";
    }
}
