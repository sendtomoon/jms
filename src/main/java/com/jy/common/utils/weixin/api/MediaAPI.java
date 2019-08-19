package com.jy.common.utils.weixin.api;

import java.io.*;
import com.jy.common.utils.weixin.vo.api.*;

public interface MediaAPI
{
    public static final String upload_media = "/media/upload?access_token=%s&type=%s";
    public static final String get_media = "/media/get?access_token=%s&media_id=%s";
    
    Media upMedia(final String p0, final File p1);
    
    File dlMedia(final String p0);
}
