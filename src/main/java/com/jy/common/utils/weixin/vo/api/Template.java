package com.jy.common.utils.weixin.vo.api;

public class Template
{
    private String name;
    private String color;
    private String value;
    
    public Template() {
    }
    
    public Template(final String name, final String value) {
        this.name = name;
        this.color = "#119EF3";
        this.value = value;
    }
    
    public Template(final String name, final String color, final String value) {
        this.name = name;
        this.color = color;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public void setColor(final String color) {
        this.color = color;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
    
    public String templateData() {
        final StringBuffer data = new StringBuffer("\"" + this.name + "\":{");
        data.append("\"value\":\"").append(this.value).append("\",");
        data.append("\"color\":\"").append(this.color).append("\"}");
        return data.toString();
    }
}
