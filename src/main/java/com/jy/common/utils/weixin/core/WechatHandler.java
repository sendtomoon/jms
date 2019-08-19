package com.jy.common.utils.weixin.core;

import com.jy.common.utils.weixin.vo.message.*;
import com.jy.common.utils.weixin.vo.event.*;
import com.jy.common.utils.weixin.vo.push.*;

public interface WechatHandler
{
    BasicMsg defMsg(final BasicMsg p0);
    
    BasicMsg defEvent(final BasicEvent p0);
    
    BasicMsg text(final TextMsg p0);
    
    BasicMsg image(final ImageMsg p0);
    
    BasicMsg voice(final VoiceMsg p0);
    
    BasicMsg video(final VideoMsg p0);
    
    BasicMsg shortVideo(final VideoMsg p0);
    
    BasicMsg location(final LocationMsg p0);
    
    BasicMsg link(final LinkMsg p0);
    
    BasicMsg eClick(final MenuEvent p0);
    
    void eView(final MenuEvent p0);
    
    BasicMsg eSub(final BasicEvent p0);
    
    void eUnSub(final BasicEvent p0);
    
    BasicMsg eScan(final ScanEvent p0);
    
    void eLocation(final LocationEvent p0);
    
    BasicMsg eScanCodePush(final ScanCodeEvent p0);
    
    BasicMsg eScanCodeWait(final ScanCodeEvent p0);
    
    BasicMsg ePicSysPhoto(final SendPhotosEvent p0);
    
    BasicMsg ePicPhotoOrAlbum(final SendPhotosEvent p0);
    
    BasicMsg ePicWeixin(final SendPhotosEvent p0);
    
    BasicMsg eLocationSelect(final SendLocationInfoEvent p0);
    
    void eSentTmplJobFinish(final SentTmlJobEvent p0);
    
    void eSentAllJobFinish(final SentAllJobEvent p0);
}
