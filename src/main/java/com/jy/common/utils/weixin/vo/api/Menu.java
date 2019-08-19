package com.jy.common.utils.weixin.vo.api;

import java.util.*;
import org.nutz.json.*;
import com.jy.common.utils.weixin.enums.*;

public class Menu
{
    private String name;
    private String type;
    private String key;
    private String url;
    @JsonField("sub_button")
    private List<Menu> subButtons;
    
    public Menu() {
    }
    
    public Menu(final String name) {
        this.setName(name);
    }
    
    public Menu(final String name, final String type, final String val) {
        this.setName(name);
        this.setType(type);
        if (EventType.VIEW.name().equals(type)) {
            this.setUrl(val);
        }
        else {
            this.setKey(val);
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(final String type) {
        this.type = type.toLowerCase();
    }
    
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public List<Menu> getSubButtons() {
        return this.subButtons;
    }
    
    public void setSubButtons(final List<Menu> subButtons) {
        this.subButtons = subButtons;
    }
    
    @Override
    public String toString() {
        return "Menu [name=" + this.name + ", type=" + this.type + ", key=" + this.key + ", url=" + this.url + ", subButtons=" + this.subButtons + "]";
    }
}
