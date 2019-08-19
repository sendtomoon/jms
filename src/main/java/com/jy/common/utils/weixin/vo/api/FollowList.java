package com.jy.common.utils.weixin.vo.api;

import org.nutz.json.*;
import java.util.*;

public class FollowList
{
    private int total;
    private int count;
    private List<String> openIds;
    @JsonField("next_openid")
    private String nextOpenId;
    
    public FollowList() {
        this.openIds = new ArrayList<String>();
    }
    
    public int getTotal() {
        return this.total;
    }
    
    public void setTotal(final int total) {
        this.total = total;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    public List<String> getOpenIds() {
        return this.openIds;
    }
    
    public void setOpenIds(final List<String> openIds) {
        this.openIds = openIds;
    }
    
    public String getNextOpenId() {
        return this.nextOpenId;
    }
    
    public void setNextOpenId(final String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }
    
    @Override
    public String toString() {
        return "FollowList [total=" + this.total + ", count=" + this.count + ", openIds=" + this.openIds + ", nextOpenId=" + this.nextOpenId + "]";
    }
}
