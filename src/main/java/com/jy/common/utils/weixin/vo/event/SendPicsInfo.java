package com.jy.common.utils.weixin.vo.event;

import java.util.*;

public class SendPicsInfo
{
    private int count;
    private List<PicItem> picList;
    
    public SendPicsInfo() {
    }
    
    public SendPicsInfo(final int count, final List<PicItem> picList) {
        this();
        this.count = count;
        this.picList = picList;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    public List<PicItem> getPicList() {
        return this.picList;
    }
    
    public void setPicList(final List<PicItem> picList) {
        this.picList = picList;
    }
    
    @Override
    public String toString() {
        return "SendPicsInfo [count=" + this.count + ", picList=" + this.picList + "]";
    }
}
