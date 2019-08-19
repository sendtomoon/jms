package com.jy.common.utils.weixin.vo.message;

public class Article
{
    private String title;
    private String description;
    private String picUrl;
    private String url;
    
    public Article() {
    }
    
    public Article(final String title, final String description, final String picUrl, final String url) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getPicUrl() {
        return this.picUrl;
    }
    
    public void setPicUrl(final String picUrl) {
        this.picUrl = picUrl;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
}
