package com.jy.common.utils.weixin.enums;

public enum MediaType
{
    image("image", 0), 
    voice("voice", 1), 
    video("video", 2), 
    thumb("thumb", 3);
    
    private MediaType(final String s, final int n) {
    }
}
