package com.jy.common.utils.weixin.aes;

import java.nio.charset.*;
import java.util.*;

public class PKCS7Encoder
{
    private static Charset CHARSET;
    private static int BLOCK_SIZE;
    
    static {
        PKCS7Encoder.CHARSET = Charset.forName("utf-8");
        PKCS7Encoder.BLOCK_SIZE = 32;
    }
    
    public static byte[] encode(final int count) {
        int amountToPad = PKCS7Encoder.BLOCK_SIZE - count % PKCS7Encoder.BLOCK_SIZE;
        if (amountToPad == 0) {
            amountToPad = PKCS7Encoder.BLOCK_SIZE;
        }
        final char padChr = chr(amountToPad);
        String tmp = new String();
        for (int index = 0; index < amountToPad; ++index) {
            tmp = String.valueOf(tmp) + padChr;
        }
        return tmp.getBytes(PKCS7Encoder.CHARSET);
    }
    
    public static byte[] decode(final byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }
    
    public static char chr(final int a) {
        final byte target = (byte)(a & 0xFF);
        return (char)target;
    }
}
