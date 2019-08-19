package com.jy.common.utils.weixin.aes;

public class AesException extends Exception
{
    private static final long serialVersionUID = 6602992230328883295L;
    public static final int OK = 0;
    public static final int ValidateSignatureError = -40001;
    public static final int ParseXmlError = -40002;
    public static final int ComputeSignatureError = -40003;
    public static final int IllegalAesKey = -40004;
    public static final int ValidateAppidError = -40005;
    public static final int EncryptAESError = -40006;
    public static final int DecryptAESError = -40007;
    public static final int IllegalBuffer = -40008;
    public static final int EncodeBase64Error = -40009;
    public static final int DecodeBase64Error = -40010;
    public static final int GenReturnXmlError = -40011;
    private int code;
    
    public AesException(final int code) {
        super(getMessage(code));
        this.code = code;
    }
    
    private static String getMessage(final int code) {
        switch (code) {
            case -40001: {
                return "\u7b7e\u540d\u9a8c\u8bc1\u9519\u8bef";
            }
            case -40002: {
                return "xml\u89e3\u6790\u5931\u8d25";
            }
            case -40003: {
                return "sha\u52a0\u5bc6\u751f\u6210\u7b7e\u540d\u5931\u8d25";
            }
            case -40004: {
                return "SymmetricKey\u975e\u6cd5";
            }
            case -40005: {
                return "appid\u6821\u9a8c\u5931\u8d25";
            }
            case -40006: {
                return "aes\u52a0\u5bc6\u5931\u8d25";
            }
            case -40007: {
                return "aes\u89e3\u5bc6\u5931\u8d25";
            }
            case -40008: {
                return "\u89e3\u5bc6\u540e\u5f97\u5230\u7684buffer\u975e\u6cd5";
            }
            case -40009: {
                return "base64\u52a0\u5bc6\u9519\u8bef";
            }
            case -40010: {
                return "base64\u89e3\u5bc6\u9519\u8bef";
            }
            case -40011: {
                return "xml\u751f\u6210\u5931\u8d25";
            }
            default: {
                return "\u672a\u77e5\u5f02\u5e38";
            }
        }
    }
    
    public int getCode() {
        return this.code;
    }
}
