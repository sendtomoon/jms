package com.jy.common.utils.weixin.core;

import javax.xml.parsers.*;
import com.jy.common.utils.weixin.vo.*;
import org.nutz.log.*;
import com.jy.common.utils.weixin.exception.*;
import java.util.*;
import java.io.*;
import com.jy.common.utils.weixin.aes.*;
import com.jy.common.utils.weixin.util.*;
import org.xml.sax.helpers.*;
import com.jy.common.utils.robot.*;
import org.apache.commons.lang3.*;
import com.jy.common.utils.weixin.enums.*;
import com.jy.common.utils.weixin.vo.event.*;
import com.jy.common.utils.weixin.vo.push.*;
import org.nutz.lang.*;
import com.jy.common.utils.weixin.vo.message.*;

public class WechatKernel
{
    private static final Log log;
    private SAXParserFactory factory;
    private SAXParser xmlParser;
    private MessageHandler msgHandler;
    private WechatHandler handler;
    private Map<String, String[]> params;
    private MPAccount mpAct;
    
    static {
        log = Logs.get();
    }
    
    public WechatKernel() {
        this.factory = SAXParserFactory.newInstance();
        this.msgHandler = new MessageHandler();
        try {
            this.xmlParser = this.factory.newSAXParser();
        }
        catch (Exception e) {
            throw Lang.wrapThrow((Throwable)new WechatRunTimeException("\u521d\u59cb\u5316SAXParserFactory\u51fa\u73b0\u5f02\u5e38", e));
        }
    }
    
    public WechatKernel(final MPAccount mpAct, final WechatHandler handler, final Map<String, String[]> params) {
        this();
        this.mpAct = mpAct;
        this.handler = handler;
        this.params = params;
    }
    
    public void setParams(final Map<String, String[]> params) {
        this.params = params;
        if (WechatKernel.log.isDebugEnabled()) {
            final Set<Map.Entry<String, String[]>> es = params.entrySet();
            WechatKernel.log.debug((Object)"wechat server request params.");
            for (final Map.Entry<String, String[]> e : es) {
                WechatKernel.log.debugf("%s-%s", new Object[] { e.getKey(), e.getValue()[0] });
            }
        }
    }
    
    public void setMpAct(final MPAccount mpAct) {
        this.mpAct = mpAct;
    }
    
    public void setWechatHandler(final WechatHandler handler) {
        this.handler = handler;
    }
    
    protected String get(final String param) {
        final String[] vals = this.params.get(param);
        return (vals == null) ? null : vals[0];
    }
    
    public String check() {
        final String sign = this.get("signature");
        final String ts = this.get("timestamp");
        final String nonce = this.get("nonce");
        if (sign == null || sign.length() > 128 || ts == null || ts.length() > 128 || nonce == null || nonce.length() > 128) {
            WechatKernel.log.warnf("The sign params are null or too long. Please check them.", new Object[0]);
            return "error";
        }
        try {
            final String validsign = SHA1.calculate(this.mpAct.getToken(), ts, nonce);
            if (WechatKernel.log.isDebugEnabled()) {
                WechatKernel.log.debugf("Valid wechat server sign %b. sign: %s", new Object[] { Lang.equals((Object)validsign, (Object)sign), validsign });
            }
            if (sign.equals(validsign)) {
                return this.get("echostr");
            }
            return "error";
        }
        catch (AesException e) {
            throw Lang.wrapThrow((Throwable)new WechatRunTimeException("\u6821\u9a8c\u670d\u52a1\u5668\u8ba4\u8bc1\u51fa\u73b0\u5f02\u5e38", e));
        }
    }
    
    public String handle(final InputStream is) {
        final String encrypt = this.get("encrypt_type");
        WXBizMsgCrypt pc = null;
        BasicMsg msg = null;
        String respmsg = "success";
        if (encrypt != null && "aes".equals(encrypt) && this.mpAct.getAESKey() != null) {
            try {
                pc = new WXBizMsgCrypt(this.mpAct.getToken(), this.mpAct.getAESKey(), this.mpAct.getAppId());
                final String ts = this.get("timestamp");
                final String nonce = this.get("nonce");
                final String msgsign = this.get("msg_signature");
                final String decmsg = pc.decryptMsg(msgsign, ts, nonce, is);
                this.xmlParser.parse(StreamTool.toStream(decmsg), this.msgHandler);
                msg = this.handleMsg();
                respmsg = pc.encryptMsg(this.responseXML(msg), ts, nonce);
                return respmsg;
            }
            catch (Exception e) {
                throw Lang.wrapThrow((Throwable)new WechatRunTimeException("\u4f7f\u7528\u5bc6\u6587\u6a21\u5f0f\u51fa\u73b0\u5f02\u5e38", e));
            }
        }
        try {
            this.xmlParser.parse(is, this.msgHandler);
        }
        catch (Exception e) {
            throw Lang.wrapThrow((Throwable)new WechatRunTimeException("\u660e\u6587\u6a21\u5f0f\u4e0b\u89e3\u6790\u6d88\u606f\u51fa\u73b0\u5f02\u5e38", e));
        }
        msg = this.handleMsg();
        respmsg = this.responseXML(msg);
        return respmsg;
    }
    
    protected BasicMsg handleMsg() {
        final String msgtype = this.msgHandler.getValues().get("msgType");
        if ("event".equals(msgtype)) {
            return this.handleEventMsg();
        }
        return this.handleNormalMsg();
    }
    
    protected BasicMsg handleNormalMsg() {
        BasicMsg msg = null;
        MessageType mt;
        try {
            mt = MessageType.valueOf(this.msgHandler.getValues().get("msgType"));
        }
        catch (Exception e) {
            WechatKernel.log.error((Object)"There are have found new meessage type in wechat.");
            mt = MessageType.def;
        }
        switch (mt) {
            case text: {
                final Map<String, String> values = this.msgHandler.getValues();
                final TextMsg tm = new TextMsg(values);
                if (this.mpAct.isTuring()) {
                    final String turing_content = TuringUtil.get(values.get("content"), tm.getToUserName());
                    if (StringUtils.isNotBlank((CharSequence)turing_content)) {
                        tm.setContent(turing_content);
                    }
                }
                msg = this.handler.text(tm);
                break;
            }
            case image: {
                final ImageMsg im = new ImageMsg(this.msgHandler.getValues());
                msg = this.handler.image(im);
                break;
            }
            case voice: {
                final VoiceMsg vom = new VoiceMsg(this.msgHandler.getValues());
                msg = this.handler.voice(vom);
                break;
            }
            case video: {
                final VideoMsg vim = new VideoMsg(this.msgHandler.getValues());
                msg = this.handler.video(vim);
                break;
            }
            case shortvideo: {
                final VideoMsg shortvim = new VideoMsg(this.msgHandler.getValues());
                msg = this.handler.shortVideo(shortvim);
                break;
            }
            case location: {
                final LocationMsg locm = new LocationMsg(this.msgHandler.getValues());
                msg = this.handler.location(locm);
                break;
            }
            case link: {
                final LinkMsg lm = new LinkMsg(this.msgHandler.getValues());
                msg = this.handler.link(lm);
                break;
            }
            default: {
                final BasicMsg bm = new BasicMsg(this.msgHandler.getValues());
                msg = this.handler.defMsg(bm);
                break;
            }
        }
        return msg;
    }
    
    protected BasicMsg handleEventMsg() {
        BasicMsg msg = null;
        EventType et;
        try {
            et = EventType.valueOf(this.msgHandler.getValues().get("event"));
        }
        catch (Exception e) {
            WechatKernel.log.error((Object)"There are have found new event type from wechat.");
            et = EventType.def;
        }
        switch (et) {
            case subscribe: {
                final BasicEvent sube = new BasicEvent(this.msgHandler.getValues());
                msg = this.handler.eSub(sube);
                break;
            }
            case unsubscribe: {
                final BasicEvent unsube = new BasicEvent(this.msgHandler.getValues());
                this.handler.eUnSub(unsube);
                break;
            }
            case SCAN: {
                final ScanEvent se = new ScanEvent(this.msgHandler.getValues());
                msg = this.handler.eScan(se);
                break;
            }
            case LOCATION: {
                final LocationEvent le = new LocationEvent(this.msgHandler.getValues());
                this.handler.eLocation(le);
                break;
            }
            case CLICK: {
                final MenuEvent cme = new MenuEvent(this.msgHandler.getValues());
                msg = this.handler.eClick(cme);
                break;
            }
            case VIEW: {
                final MenuEvent vme = new MenuEvent(this.msgHandler.getValues());
                this.handler.eView(vme);
                break;
            }
            case scancode_push: {
                final ScanCodeEvent sce = new ScanCodeEvent(this.msgHandler.getValues());
                msg = this.handler.eScanCodePush(sce);
                break;
            }
            case scancode_waitmsg: {
                final ScanCodeEvent scemsg = new ScanCodeEvent(this.msgHandler.getValues());
                msg = this.handler.eScanCodeWait(scemsg);
                break;
            }
            case pic_sysphoto: {
                final SendPhotosEvent spesys = new SendPhotosEvent(this.msgHandler.getValues());
                msg = this.handler.ePicSysPhoto(spesys);
                break;
            }
            case pic_photo_or_album: {
                final SendPhotosEvent spealb = new SendPhotosEvent(this.msgHandler.getValues());
                msg = this.handler.ePicPhotoOrAlbum(spealb);
                break;
            }
            case pic_weixin: {
                final SendPhotosEvent spewx = new SendPhotosEvent(this.msgHandler.getValues());
                msg = this.handler.ePicWeixin(spewx);
                break;
            }
            case location_select: {
                final SendLocationInfoEvent lse = new SendLocationInfoEvent(this.msgHandler.getValues());
                msg = this.handler.eLocationSelect(lse);
                break;
            }
            case TEMPLATESENDJOBFINISH: {
                final SentTmlJobEvent stje = new SentTmlJobEvent(this.msgHandler.getValues());
                this.handler.eSentTmplJobFinish(stje);
                break;
            }
            case MASSSENDJOBFINISH: {
                final SentAllJobEvent saje = new SentAllJobEvent(this.msgHandler.getValues());
                this.handler.eSentAllJobFinish(saje);
                break;
            }
            default: {
                final BasicEvent be = new BasicEvent(this.msgHandler.getValues());
                msg = this.handler.defEvent(be);
                break;
            }
        }
        return msg;
    }
    
    protected String responseXML(final BasicMsg msg) {
        String respmsg = "success";
        if (msg == null || Strings.isBlank((CharSequence)msg.getMsgType())) {
            return respmsg;
        }
        final String fromUser = msg.getFromUserName();
        final String toUser = msg.getToUserName();
        msg.setFromUserName(toUser);
        msg.setToUserName(fromUser);
        final MessageType mt = MessageType.valueOf(msg.getMsgType());
        switch (mt) {
            case text: {
                respmsg = XmlMsgBuilder.create().text((TextMsg)msg).build();
                break;
            }
            case image: {
                respmsg = XmlMsgBuilder.create().image((ImageMsg)msg).build();
                break;
            }
            case voice: {
                respmsg = XmlMsgBuilder.create().voice((VoiceMsg)msg).build();
                break;
            }
            case music: {
                respmsg = XmlMsgBuilder.create().music((MusicMsg)msg).build();
                break;
            }
            case video: {
                respmsg = XmlMsgBuilder.create().video((VideoMsg)msg).build();
                break;
            }
            case news: {
                respmsg = XmlMsgBuilder.create().news((NewsMsg)msg).build();
                break;
            }
        }
        return respmsg;
    }
}
