package com.jy.common.utils.weixin.exception;

public class WechatApiException extends Exception
{
    private static final long serialVersionUID = -303278319021435258L;
    
    public WechatApiException() {
    }
    
    public WechatApiException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public WechatApiException(final String message) {
        super(message);
    }
    
    public WechatApiException(final Throwable cause) {
        super(cause);
    }
}
