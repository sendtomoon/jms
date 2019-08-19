package com.jy.common.utils.weixin.core;

import org.nutz.lang.*;
import com.jy.common.utils.weixin.vo.message.*;
import com.jy.common.utils.*;
import com.jy.service.weixin.menu.*;
import com.jy.entity.weixin.event.*;
import org.springframework.context.*;
import com.jy.entity.weixin.menu.*;
import java.util.*;
import com.jy.common.utils.weixin.vo.event.*;
import com.jy.common.utils.weixin.vo.push.*;

public class WechatDefHandler implements WechatHandler
{
    @Override
    public BasicMsg defMsg(final BasicMsg bm) {
        final TextMsg tm = new TextMsg(bm);
        tm.setContent(bm.getMsgType());
        return tm;
    }
    
    @Override
    public BasicMsg defEvent(final BasicEvent be) {
        final TextMsg tm = new TextMsg(be);
        tm.setContent(Strings.join("\n", (Object[])new String[] { be.getEvent(), be.getEventKey() }));
        return tm;
    }
    
    @Override
    public BasicMsg text(final TextMsg tm) {
        return tm;
    }
    
    @Override
    public BasicMsg image(final ImageMsg im) {
        return im;
    }
    
    @Override
    public BasicMsg voice(final VoiceMsg vm) {
        final TextMsg tm = new TextMsg(vm);
        tm.setContent(Strings.join("\n", (Object[])new String[] { vm.getMediaId(), vm.getFormat(), vm.getRecognition() }));
        return tm;
    }
    
    @Override
    public BasicMsg video(final VideoMsg vim) {
        final TextMsg tm = new TextMsg(vim);
        tm.setContent(Strings.join("\n", (Object[])new String[] { vim.getMsgType(), vim.getMediaId(), vim.getThumbMediaId() }));
        return tm;
    }
    
    @Override
    public BasicMsg shortVideo(final VideoMsg vim) {
        final TextMsg tm = new TextMsg(vim);
        tm.setContent(Strings.join("\n", (Object[])new String[] { vim.getMsgType(), vim.getMediaId(), vim.getThumbMediaId() }));
        return tm;
    }
    
    @Override
    public BasicMsg location(final LocationMsg lm) {
        final TextMsg tm = new TextMsg(lm);
        tm.setContent(Strings.join("\n", (Object[])new String[] { lm.getX(), lm.getY(), String.valueOf(lm.getScale()), lm.getLabel() }));
        return tm;
    }
    
    @Override
    public BasicMsg link(final LinkMsg lm) {
        final NewsMsg news = new NewsMsg(lm);
        final Article art = new Article();
        art.setTitle(lm.getTitle());
        art.setDescription(lm.getDescription());
        art.setUrl(lm.getUrl());
        news.setArticles(Arrays.asList(art));
        return news;
    }
    
    @Override
    public BasicMsg eClick(final MenuEvent me) {
        final ApplicationContext ac = SpringWebContextUtil.getApplicationContext();
        final WxMenuService wms = (WxMenuService)ac.getBean("WxMenuService");
        final WxMenu wxMenu = wms.getWxMenuByKeyId(me.getEventKey());
        if (wxMenu != null) {
            final String selectType = wxMenu.getSelectType();
            final List<WxEventClick> wxecs = wxMenu.getItems();
            if (wxecs != null && wxecs.size() > 0) {
                if ("text".equals(selectType)) {
                    final TextMsg tm = new TextMsg(me);
                    tm.setContent(wxecs.get(0).getContent());
                    return tm;
                }
                if ("imageText".equals(selectType)) {
                    final NewsMsg news = new NewsMsg(me);
                    final List<Article> articles = new ArrayList<Article>();
                    for (final WxEventClick wxec : wxecs) {
                        final Article art = new Article(wxec.getTitle(), wxec.getContent(), wxec.getPicUrl(), wxec.getUrl());
                        articles.add(art);
                    }
                    news.setArticles(articles);
                    return news;
                }
                if ("image".equals(selectType)) {
                    final NewsMsg news = new NewsMsg(me);
                    final Article art2 = new Article();
                    art2.setTitle("");
                    art2.setDescription("");
                    art2.setPicUrl(wxecs.get(0).getPicUrl());
                    news.setArticles(Arrays.asList(art2));
                    return news;
                }
            }
        }
        final TextMsg tm2 = new TextMsg(me);
        tm2.setContent("\u5bf9\u4e0d\u8d77,\u6682\u65f6\u4e0d\u80fd\u63d0\u4f9b\u670d\u52a1");
        return tm2;
    }
    
    @Override
    public void eView(final MenuEvent me) {
    }
    
    @Override
    public BasicMsg eSub(final BasicEvent be) {
        final TextMsg tm = new TextMsg(be);
        tm.setContent("\u6b22\u8fce\u5173\u6ce8\u91d1\u4e00\u6587\u5316");
        return tm;
    }
    
    @Override
    public void eUnSub(final BasicEvent be) {
    }
    
    @Override
    public BasicMsg eScan(final ScanEvent se) {
        final TextMsg tm = new TextMsg(se);
        tm.setContent(String.valueOf(se.getEventKey()) + se.getTicket());
        return tm;
    }
    
    @Override
    public void eLocation(final LocationEvent le) {
    }
    
    @Override
    public BasicMsg eScanCodePush(final ScanCodeEvent sce) {
        final TextMsg tm = new TextMsg(sce);
        tm.setContent(Strings.join("\n", (Object[])new String[] { sce.getEventKey(), sce.getScanType(), sce.getScanResult() }));
        return tm;
    }
    
    @Override
    public BasicMsg eScanCodeWait(final ScanCodeEvent sce) {
        return this.eScanCodePush(sce);
    }
    
    @Override
    public BasicMsg ePicSysPhoto(final SendPhotosEvent spe) {
        final TextMsg tm = new TextMsg(spe);
        tm.setContent(Strings.join("\n", (Object[])new String[] { spe.getEventKey(), String.valueOf(spe.getSendPicsInfo().getCount()), String.valueOf(spe.getSendPicsInfo().getPicList()), String.valueOf(spe.getSendPicsInfo().getPicList().get(0).getPicMd5Sum()) }));
        return tm;
    }
    
    @Override
    public BasicMsg ePicPhotoOrAlbum(final SendPhotosEvent spe) {
        return this.ePicSysPhoto(spe);
    }
    
    @Override
    public BasicMsg ePicWeixin(final SendPhotosEvent spe) {
        return this.ePicSysPhoto(spe);
    }
    
    @Override
    public BasicMsg eLocationSelect(final SendLocationInfoEvent slie) {
        final TextMsg tm = new TextMsg(slie);
        tm.setContent(Strings.join("\n", (Object[])new String[] { slie.getLocationX(), slie.getLocationY(), slie.getLabel(), String.valueOf(slie.getScale()), slie.getPoiname() }));
        return tm;
    }
    
    @Override
    public void eSentTmplJobFinish(final SentTmlJobEvent stje) {
    }
    
    @Override
    public void eSentAllJobFinish(final SentAllJobEvent saje) {
    }
}
