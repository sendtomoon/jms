package com.jy.common.utils.weixin.util;

import org.nutz.log.*;
import java.io.*;

public class StreamTool
{
    private static final Log log;
    
    static {
        log = Logs.get();
    }
    
    public static InputStream toStream(final String str) {
        InputStream stream = null;
        try {
            stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e) {
            StreamTool.log.error((Object)"\u8f6c\u6362\u8f93\u51faStream\u5f02\u5e38,\u4e0d\u652f\u6301\u7684\u5b57\u7b26\u96c6!!!");
            StreamTool.log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return stream;
    }
    
    public static String toString(final InputStream is) {
        final StringBuffer str = new StringBuffer();
        final byte[] b = new byte[1024];
        try {
            int n;
            while ((n = is.read(b)) != -1) {
                str.append(new String(b, 0, n));
            }
        }
        catch (IOException e) {
            StreamTool.log.error((Object)"\u8bfb\u53d6\u8f93\u5165\u6d41\u51fa\u73b0\u5f02\u5e38!!!");
            StreamTool.log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return str.toString();
    }
}
