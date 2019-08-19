package com.jy.common.utils.weixin.enums;

public enum MessageType
{
    def("def", 0), 
    text("text", 1), 
    image("image", 2), 
    voice("voice", 3), 
    video("video", 4), 
    shortvideo("shortvideo", 5), 
    location("location", 6), 
    link("link", 7), 
    music("music", 8), 
    news("news", 9), 
    mpnews("mpnews", 10), 
    mpvideo("mpvideo", 11);
    
    private MessageType(final String s, final int n) {
    }
}
