package com.jy.common.utils.weixin.vo.message;

import java.util.*;
import com.jy.common.utils.weixin.vo.event.*;
import org.nutz.lang.*;

public class NewsMsg extends BasicMsg
{
    private int count;
    private List<Article> articles;
    
    public NewsMsg() {
        this.msgType = "news";
    }
    
    public NewsMsg(final BasicEvent event) {
        super(event);
        this.msgType = "news";
    }
    
    public NewsMsg(final BasicMsg msg) {
        super(msg);
        this.msgType = "news";
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    public List<Article> getArticles() {
        if (!Lang.isEmpty((Object)this.articles) && this.articles.size() > 10) {
            this.articles = this.articles.subList(0, 10);
            this.setCount(10);
        }
        else {
            this.setCount(Lang.length((Object)this.articles));
        }
        return this.articles;
    }
    
    public void setArticles(final List<Article> articles) {
        this.articles = articles;
    }
    
    @Override
    public String toString() {
        return "NewsMsg [toUserName=" + this.toUserName + ", fromUserName=" + this.fromUserName + ", createTime=" + this.createTime + ", msgType=" + this.msgType + ", msgId=" + this.msgId + ", count=" + this.count + ", articles=" + this.articles + "]";
    }
}
