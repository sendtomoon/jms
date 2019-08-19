package com.jy.common.utils.weixin.core;

import org.xml.sax.ext.*;
import java.util.*;
import org.nutz.log.*;
import java.util.concurrent.*;
import org.xml.sax.*;
import org.nutz.lang.*;

public class MessageHandler extends DefaultHandler2
{
    private static final Log log;
    private String attrVal;
    static Map<String, String> _vals;
    static StringBuffer _sb;
    
    static {
        log = Logs.get();
        MessageHandler._vals = new ConcurrentHashMap<String, String>();
        MessageHandler._sb = new StringBuffer();
    }
    
    public Map<String, String> getValues() {
        return MessageHandler._vals;
    }
    
    @Override
    public void startDocument() throws SAXException {
        MessageHandler._vals.clear();
        MessageHandler._sb.setLength(0);
    }
    
    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if ("PicList".equals(qName)) {
            MessageHandler._sb.append("[");
        }
    }
    
    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {
        if (MessageHandler.log.isDebugEnabled() && !Strings.equals("xml", qName) && !Strings.equals("ScanCodeInfo", qName) && !Strings.equals("SendLocationInfo", qName) && !Strings.equals("SendPicsInfo", qName) && !Strings.equals("PicList", qName) && !Strings.equals("item", qName)) {
            MessageHandler.log.debugf("Current node vaule: [%s-%s]", new Object[] { qName, this.attrVal });
        }
        if ("ToUserName".equals(qName)) {
            MessageHandler._vals.put("toUserName", this.attrVal);
            return;
        }
        if ("FromUserName".equals(qName)) {
            MessageHandler._vals.put("fromUserName", this.attrVal);
            return;
        }
        if ("CreateTime".equals(qName)) {
            MessageHandler._vals.put("createTime", this.attrVal);
            return;
        }
        if ("MsgType".equals(qName)) {
            MessageHandler._vals.put("msgType", this.attrVal);
            return;
        }
        if ("Content".equals(qName)) {
            MessageHandler._vals.put("content", this.attrVal);
            return;
        }
        if ("PicUrl".equals(qName)) {
            MessageHandler._vals.put("picUrl", this.attrVal);
            return;
        }
        if ("MediaId".equals(qName)) {
            MessageHandler._vals.put("mediaId", this.attrVal);
            return;
        }
        if ("Format".equals(qName)) {
            MessageHandler._vals.put("format", this.attrVal);
            return;
        }
        if ("Recognition".equals(qName)) {
            MessageHandler._vals.put("recognition", this.attrVal);
            return;
        }
        if ("ThumbMediaId".equals(qName)) {
            MessageHandler._vals.put("thumbMediaId", this.attrVal);
            return;
        }
        if ("Location_X".equals(qName)) {
            MessageHandler._vals.put("locationX", this.attrVal);
            return;
        }
        if ("Location_Y".equals(qName)) {
            MessageHandler._vals.put("locationY", this.attrVal);
            return;
        }
        if ("Scale".equals(qName)) {
            MessageHandler._vals.put("scale", this.attrVal);
            return;
        }
        if ("Label".equals(qName)) {
            MessageHandler._vals.put("label", this.attrVal);
            return;
        }
        if ("Title".equals(qName)) {
            MessageHandler._vals.put("title", this.attrVal);
            return;
        }
        if ("Description".equals(qName)) {
            MessageHandler._vals.put("description", this.attrVal);
            return;
        }
        if ("Url".equals(qName)) {
            MessageHandler._vals.put("url", this.attrVal);
            return;
        }
        if ("MsgId".equals(qName) || "MsgID".equals(qName)) {
            MessageHandler._vals.put("msgId", this.attrVal);
            return;
        }
        if ("Event".equals(qName)) {
            MessageHandler._vals.put("event", this.attrVal);
            return;
        }
        if ("EventKey".equals(qName)) {
            MessageHandler._vals.put("eventKey", this.attrVal);
            return;
        }
        if ("ScanType".equals(qName)) {
            MessageHandler._vals.put("scanType", this.attrVal);
            return;
        }
        if ("ScanResult".equals(qName)) {
            MessageHandler._vals.put("scanResult", this.attrVal);
            return;
        }
        if ("Poiname".equals(qName)) {
            MessageHandler._vals.put("poiname", this.attrVal);
            return;
        }
        if ("Count".equals(qName)) {
            MessageHandler._vals.put("count", this.attrVal);
            return;
        }
        if ("PicMd5Sum".equals(qName)) {
            MessageHandler._sb.append("{\"picMd5Sum\":\"").append(this.attrVal).append("\"},");
            return;
        }
        if ("PicList".equals(qName)) {
            MessageHandler._sb.deleteCharAt(MessageHandler._sb.lastIndexOf(","));
            MessageHandler._sb.append("]");
            MessageHandler._vals.put("picList", MessageHandler._sb.toString());
            return;
        }
        if ("Status".equals(qName)) {
            MessageHandler._vals.put("status", this.attrVal);
            return;
        }
        if ("TotalCount".equals(qName)) {
            MessageHandler._vals.put("totalCount", this.attrVal);
            return;
        }
        if ("FilterCount".equals(qName)) {
            MessageHandler._vals.put("filterCount", this.attrVal);
            return;
        }
        if ("SentCount".equals(qName)) {
            MessageHandler._vals.put("sentCount", this.attrVal);
            return;
        }
        if ("ErrorCount".equals(qName)) {
            MessageHandler._vals.put("errorCount", this.attrVal);
        }
    }
    
    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        this.attrVal = new String(ch, start, length);
    }
}
