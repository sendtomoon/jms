package com.jy.common.utils.weixin.vo.event;

public class PicItem
{
    private String picMd5Sum;
    
    public PicItem() {
    }
    
    public PicItem(final String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
    }
    
    public String getPicMd5Sum() {
        return this.picMd5Sum;
    }
    
    public void setPicMd5Sum(final String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
    }
    
    @Override
    public String toString() {
        return "PicItem [picMd5Sum=" + this.picMd5Sum + "]";
    }
}
