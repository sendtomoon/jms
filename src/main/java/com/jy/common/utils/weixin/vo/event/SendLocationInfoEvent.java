package com.jy.common.utils.weixin.vo.event;

import java.util.*;

public class SendLocationInfoEvent extends MenuEvent
{
    private String locationX;
    private String locationY;
    private int scale;
    private String label;
    private String poiname;
    
    public SendLocationInfoEvent() {
    }
    
    public SendLocationInfoEvent(final Map<String, String> values) {
        super(values);
        this.locationX = values.get("locationX");
        this.locationY = values.get("locationY");
        this.scale = Integer.parseInt(values.get("scale"));
        this.label = values.get("label");
        this.poiname = values.get("poiname");
    }
    
    public String getLocationX() {
        return this.locationX;
    }
    
    public void setLocationX(final String locationX) {
        this.locationX = locationX;
    }
    
    public String getLocationY() {
        return this.locationY;
    }
    
    public void setLocationY(final String locationY) {
        this.locationY = locationY;
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
    
    public String getPoiname() {
        return this.poiname;
    }
    
    public void setPoiname(final String poiname) {
        this.poiname = poiname;
    }
    
    @Override
    public String toString() {
        return "SendLocationInfoEvent [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", event=" + this.event + ", eventKey=" + this.eventKey + ", locationX=" + this.locationX + ", locationY=" + this.locationY + ", scale=" + this.scale + ", label=" + this.label + ", poiname=" + this.poiname + "]";
    }
}
