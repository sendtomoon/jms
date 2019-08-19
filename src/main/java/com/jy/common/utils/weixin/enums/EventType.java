package com.jy.common.utils.weixin.enums;

public enum EventType
{
    def("def", 0), 
    subscribe("subscribe", 1), 
    unsubscribe("unsubscribe", 2), 
    SCAN("SCAN", 3), 
    LOCATION("LOCATION", 4), 
    CLICK("CLICK", 5), 
    VIEW("VIEW", 6), 
    TEMPLATESENDJOBFINISH("TEMPLATESENDJOBFINISH", 7), 
    MASSSENDJOBFINISH("MASSSENDJOBFINISH", 8), 
    scancode_push("scancode_push", 9), 
    scancode_waitmsg("scancode_waitmsg", 10), 
    pic_sysphoto("pic_sysphoto", 11), 
    pic_photo_or_album("pic_photo_or_album", 12), 
    pic_weixin("pic_weixin", 13), 
    location_select("location_select", 14), 
    media_id("media_id", 15), 
    view_limited("view_limited", 16);
    
    private EventType(final String s, final int n) {
    }
}
