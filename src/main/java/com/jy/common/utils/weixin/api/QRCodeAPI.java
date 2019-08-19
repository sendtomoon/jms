package com.jy.common.utils.weixin.api;

import com.jy.common.utils.weixin.vo.api.*;
import java.io.*;

public interface QRCodeAPI
{
    public static final String create_qrcode = "/qrcode/create?access_token=";
    public static final String show_qrcode = "/showqrcode?ticket=";
    
    QRTicket createQR(final Object p0, final int p1);
    
    File getQR(final String p0);
}
