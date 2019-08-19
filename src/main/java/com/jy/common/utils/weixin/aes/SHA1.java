package com.jy.common.utils.weixin.aes;

import java.security.*;
import java.util.*;

public class SHA1
{
    public static String calculate(final String... params) throws AesException {
        try {
            final StringBuffer sb = new StringBuffer();
            Arrays.sort(params);
            for (int len = params.length, i = 0; i < len; ++i) {
                sb.append(params[i]);
            }
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(new String(sb).getBytes("UTF-8"));
            final byte[] hash = md.digest();
            final Formatter formatter = new Formatter();
            byte[] array;
            for (int length = (array = hash).length, j = 0; j < length; ++j) {
                final byte b = array[j];
                formatter.format("%02x", b);
            }
            final String hex = formatter.toString();
            formatter.close();
            return hex;
        }
        catch (Exception e) {
            throw new AesException(-40003);
        }
    }
}
