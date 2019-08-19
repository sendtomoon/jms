package com.jy.common.utils.weixin.api;

public interface WechatAPI extends CredentialAPI, MenuAPI, MediaAPI, GroupsAPI, QRCodeAPI, UserAPI, TemplateAPI, MessageAPI
{
    public static final String wechatapi = "https://api.weixin.qq.com/cgi-bin";
}
