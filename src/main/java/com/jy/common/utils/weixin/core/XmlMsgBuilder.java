package com.jy.common.utils.weixin.core;

import org.nutz.log.*;
import com.jy.common.utils.weixin.vo.message.*;
import java.util.*;

public class XmlMsgBuilder
{
    private static final Log log;
    private final StringBuffer msgBuf;
    
    static {
        log = Logs.get();
    }
    
    public XmlMsgBuilder() {
        this.msgBuf = new StringBuffer("<xml>\n");
    }
    
    public static XmlMsgBuilder create() {
        return new XmlMsgBuilder();
    }
    
    void msgPrefix(final BasicMsg msg) {
        this.msgBuf.append("<ToUserName><![CDATA[").append(msg.getToUserName()).append("]]></ToUserName>\n");
        this.msgBuf.append("<FromUserName><![CDATA[").append(msg.getFromUserName()).append("]]></FromUserName>\n");
        this.msgBuf.append("<CreateTime>").append(msg.getCreateTime()).append("</CreateTime>\n");
        this.msgBuf.append("<MsgType><![CDATA[").append(msg.getMsgType()).append("]]></MsgType>\n");
    }
    
    public XmlMsgBuilder text(final TextMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("<Content><![CDATA[").append(msg.getContent()).append("]]></Content>\n");
        return this;
    }
    
    public XmlMsgBuilder image(final ImageMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("<Image>");
        this.msgBuf.append("<MediaId><![CDATA[").append(msg.getMediaId()).append("]]></MediaId>\n");
        this.msgBuf.append("</Image>");
        return this;
    }
    
    public XmlMsgBuilder voice(final VoiceMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("<Voice>");
        this.msgBuf.append("<MediaId><![CDATA[").append(msg.getMediaId()).append("]]></MediaId>\n");
        this.msgBuf.append("</Voice>\n");
        return this;
    }
    
    public XmlMsgBuilder video(final VideoMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("<Video>");
        this.msgBuf.append("<MediaId><![CDATA[").append(msg.getMediaId()).append("]]></MediaId>\n");
        this.msgBuf.append("<Title><![CDATA[").append(msg.getTitle()).append("]]></Title>\n");
        this.msgBuf.append("<Description><![CDATA[").append(msg.getDescription()).append("]]></Description>\n");
        this.msgBuf.append("</Video>\n");
        return this;
    }
    
    public XmlMsgBuilder music(final MusicMsg msg) {
        this.msgPrefix(msg);
        this.msgBuf.append("<Music>");
        this.msgBuf.append("<Title><![CDATA[").append(msg.getTitle()).append("]]></Title>\n");
        this.msgBuf.append("<Description><![CDATA[").append(msg.getDescription()).append("]]></Description>\n");
        this.msgBuf.append("<MusicUrl><![CDATA[").append(msg.getMusicUrl()).append("]]></MusicUrl>\n");
        this.msgBuf.append("<HQMusicUrl><![CDATA[").append(msg.getHQMusicUrl()).append("]]></HQMusicUrl>\n");
        this.msgBuf.append("<ThumbMediaId><![CDATA[").append(msg.getThumbMediaId()).append("]]></ThumbMediaId>\n");
        this.msgBuf.append("</Music>\n");
        return this;
    }
    
    public XmlMsgBuilder news(final NewsMsg msg) {
        this.msgPrefix(msg);
        final StringBuffer arts_buf = new StringBuffer("<Articles>\n");
        final StringBuffer item_buf = new StringBuffer();
        for (final Article art : msg.getArticles()) {
            item_buf.setLength(0);
            item_buf.append("<item>\n");
            item_buf.append("<Title><![CDATA[").append(art.getTitle()).append("]]></Title>\n");
            item_buf.append("<Description><![CDATA[").append(art.getDescription()).append("]]></Description>\n");
            item_buf.append("<PicUrl><![CDATA[").append(art.getPicUrl()).append("]]></PicUrl>\n");
            item_buf.append("<Url><![CDATA[").append(art.getUrl()).append("]]></Url>\n");
            item_buf.append("</item>\n");
            arts_buf.append(item_buf);
        }
        arts_buf.append("</Articles>\n");
        this.msgBuf.append("<ArticleCount>").append(msg.getCount()).append("</ArticleCount>\n");
        this.msgBuf.append(arts_buf);
        return this;
    }
    
    public String encrypt(final String xml, final String msgSignature, final String timeStamp, final String nonce) {
        this.msgBuf.setLength(0);
        this.msgBuf.append("<xml>\n");
        this.msgBuf.append("<Encrypt><![CDATA[").append(xml).append("]]></Encrypt>\n");
        this.msgBuf.append("<MsgSignature><![CDATA[").append(msgSignature).append("]]></MsgSignature>\n");
        this.msgBuf.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>\n");
        this.msgBuf.append("<Nonce><![CDATA[").append(nonce).append("]]></Nonce>\n");
        this.msgBuf.append("</xml>");
        return this.msgBuf.toString();
    }
    
    public String build() {
        this.msgBuf.append("</xml>");
        if (XmlMsgBuilder.log.isDebugEnabled()) {
            XmlMsgBuilder.log.debugf("Xml message content: \n%s", new Object[] { this.msgBuf });
        }
        return new String(this.msgBuf);
    }
}
