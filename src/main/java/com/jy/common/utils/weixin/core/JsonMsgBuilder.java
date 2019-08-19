package com.jy.common.utils.weixin.core;

import org.nutz.log.*;
import com.jy.common.utils.weixin.vo.message.*;
import java.util.*;
import com.jy.common.utils.weixin.vo.api.*;

public class JsonMsgBuilder
{
    private static final Log log;
    private final StringBuffer msgBuf;
    
    static {
        log = Logs.get();
    }
    
    public JsonMsgBuilder() {
        this.msgBuf = new StringBuffer("{");
    }
    
    public static JsonMsgBuilder create() {
        return new JsonMsgBuilder();
    }
    
    void msgPrefix(final BasicMsg msg) {
        this.msgBuf.append("\"touser\":\"").append(msg.getToUserName()).append("\",");
        this.msgBuf.append("\"msgtype\":\"").append(msg.getMsgType()).append("\",");
    }
    
    public JsonMsgBuilder text(final TextMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("\"text\": {");
        this.msgBuf.append(" \"content\":\"").append(msg.getContent()).append("\"");
        this.msgBuf.append("}");
        return this;
    }
    
    public JsonMsgBuilder image(final ImageMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("\"image\": {");
        this.msgBuf.append(" \"media_id\":\"").append(msg.getMediaId()).append("\"");
        this.msgBuf.append("}");
        return this;
    }
    
    public JsonMsgBuilder voice(final VoiceMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("\"voice\": {");
        this.msgBuf.append(" \"media_id\":\"").append(msg.getMediaId()).append("\"");
        this.msgBuf.append("}");
        return this;
    }
    
    public JsonMsgBuilder video(final VideoMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("\"video\": {");
        this.msgBuf.append(" \"media_id\":\"").append(msg.getMediaId()).append("\",");
        this.msgBuf.append(" \"thumb_media_id\":\"").append(msg.getThumbMediaId()).append("\",");
        this.msgBuf.append(" \"title\":\"").append(msg.getTitle()).append("\",");
        this.msgBuf.append(" \"description\":\"").append(msg.getDescription()).append("\"");
        this.msgBuf.append("}");
        return this;
    }
    
    public JsonMsgBuilder music(final MusicMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("\"music\": {");
        this.msgBuf.append(" \"title\":\"").append(msg.getTitle()).append("\",");
        this.msgBuf.append(" \"description\":\"").append(msg.getDescription()).append("\",");
        this.msgBuf.append(" \"musicurl\":\"").append(msg.getMusicUrl()).append("\",");
        this.msgBuf.append(" \"hqmusicurl\":\"").append(msg.getHQMusicUrl()).append("\",");
        this.msgBuf.append(" \"thumb_media_id\":\"").append(msg.getThumbMediaId()).append("\"");
        this.msgBuf.append("}");
        return this;
    }
    
    public JsonMsgBuilder news(final NewsMsg msg) {
        this.msgPrefix(msg);
        final StringBuffer arts_buf = new StringBuffer("\"articles\": [");
        final StringBuffer art_buf = new StringBuffer();
        for (final Article art : msg.getArticles()) {
            art_buf.setLength(0);
            art_buf.append("{");
            art_buf.append(" \"title\":\"").append(art.getTitle()).append("\",");
            art_buf.append(" \"description\":\"").append(art.getDescription()).append("\",");
            art_buf.append(" \"picurl\":\"").append(art.getPicUrl()).append("\",");
            art_buf.append(" \"url\":\"").append(art.getUrl());
            art_buf.append("\"},");
        }
        art_buf.deleteCharAt(art_buf.lastIndexOf(","));
        arts_buf.append(art_buf);
        arts_buf.append("]");
        this.msgBuf.append("\"news\": {");
        this.msgBuf.append(arts_buf);
        this.msgBuf.append("}");
        return this;
    }
    
    public JsonMsgBuilder template(final String openId, final String tmlId, final String topColor, final String url, final Template... tmls) {
        this.msgBuf.append("\"touser\":\"").append(openId).append("\",");
        this.msgBuf.append("\"template_id\":\"").append(tmlId).append("\",");
        this.msgBuf.append("\"url\":\"").append(url).append("\",");
        this.msgBuf.append("\"topcolor\":\"").append(topColor).append("\",");
        this.msgBuf.append("\"data\":{");
        final StringBuffer data = new StringBuffer("");
        for (final Template t : tmls) {
            data.append(t.templateData()).append(",");
        }
        data.deleteCharAt(data.lastIndexOf(","));
        this.msgBuf.append(data);
        this.msgBuf.append("}");
        return this;
    }
    
    public String build() {
        this.msgBuf.append("}");
        if (JsonMsgBuilder.log.isDebugEnabled()) {
            JsonMsgBuilder.log.debugf("Json message content: %s", new Object[] { this.msgBuf });
        }
        return new String(this.msgBuf);
    }
}
