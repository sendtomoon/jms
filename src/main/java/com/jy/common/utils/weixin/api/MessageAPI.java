package com.jy.common.utils.weixin.api;

import com.jy.common.utils.weixin.vo.api.*;

public interface MessageAPI
{
    public static final String send_template = "/message/template/send?access_token=";
    
    long sendTemplateMsg(final String p0, final String p1, final String p2, final String p3, final Template... p4);
}
