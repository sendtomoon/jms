package com.jy.common.utils.weixin.exception;

public class WechatRunTimeException extends Exception
{
    private static final long serialVersionUID = 7300175978685072703L;
    
    public WechatRunTimeException() {
    }
    
    public WechatRunTimeException(final String msg, final Throwable e) {
        super(msg, e);
    }
    
    public WechatRunTimeException(final String e) {
        super(e);
    }
    
    public WechatRunTimeException(final Throwable e) {
        super(e);
    }
}
