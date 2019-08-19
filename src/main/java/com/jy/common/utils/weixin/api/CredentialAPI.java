package com.jy.common.utils.weixin.api;

import java.util.*;

public interface CredentialAPI
{
    public static final String get_at = "/token?grant_type=client_credential&appid=%s&secret=%s";
    public static final String cb_ips = "/getcallbackip?access_token=";
    public static final String short_url = "/shorturl?access_token=";
    public static final String js_ticket = "/ticket/getticket?type=jsapi&access_token=";
    
    String getAccessToken();
    
    List<String> getServerIps();
    
    String getShortUrl(final String p0);
    
    String getJSTicket();
}
