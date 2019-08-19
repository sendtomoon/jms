package com.jy.common.utils.weixin.vo.api;

public class Groups
{
    private int id;
    private String name;
    private int count;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    @Override
    public String toString() {
        return "Groups [id=" + this.id + ", name=" + this.name + ", count=" + this.count + "]";
    }
}
