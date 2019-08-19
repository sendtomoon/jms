package com.jy.common.utils.verifyCode;

import java.util.*;
import java.awt.image.*;
import java.awt.*;

public class VerifyCodeUtil
{
    public static final int TYPE_NUM_ONLY = 0;
    public static final int TYPE_LETTER_ONLY = 1;
    public static final int TYPE_ALL_MIXED = 2;
    public static final int TYPE_NUM_UPPER = 3;
    public static final int TYPE_NUM_LOWER = 4;
    public static final int TYPE_UPPER_ONLY = 5;
    public static final int TYPE_LOWER_ONLY = 6;
    
    private static Color generateRandomColor() {
        final Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
    
    public static BufferedImage generateImageCode(final int type, final int length, final String excludeString, final int width, final int height, final int interLine, final boolean randomLocation, final Color backColor, final Color foreColor, final Color lineColor, final String fontType) {
        final String textCode = generateTextCode(type, length, excludeString);
        return generateImageCode(textCode, width, height, interLine, randomLocation, backColor, foreColor, lineColor, fontType);
    }
    
    public static String generateTextCode(final int type, final int length, final String excludeString) {
        if (length <= 0) {
            return "";
        }
        final StringBuffer verifyCode = new StringBuffer();
        int i = 0;
        final Random random = new Random();
        switch (type) {
            case 0: {
                while (i < length) {
                    final int t = random.nextInt(10);
                    if (excludeString == null || excludeString.indexOf(new StringBuilder(String.valueOf(t)).toString()) < 0) {
                        verifyCode.append(t);
                        ++i;
                    }
                }
                break;
            }
            case 1: {
                while (i < length) {
                    final int t = random.nextInt(123);
                    if ((t >= 97 || (t >= 65 && t <= 90)) && (excludeString == null || excludeString.indexOf((char)t) < 0)) {
                        verifyCode.append((char)t);
                        ++i;
                    }
                }
                break;
            }
            case 2: {
                while (i < length) {
                    final int t = random.nextInt(123);
                    if ((t >= 97 || (t >= 65 && t <= 90) || (t >= 48 && t <= 57)) && (excludeString == null || excludeString.indexOf((char)t) < 0)) {
                        verifyCode.append((char)t);
                        ++i;
                    }
                }
                break;
            }
            case 3: {
                while (i < length) {
                    final int t = random.nextInt(91);
                    if ((t >= 65 || (t >= 48 && t <= 57)) && (excludeString == null || excludeString.indexOf((char)t) < 0)) {
                        verifyCode.append((char)t);
                        ++i;
                    }
                }
                break;
            }
            case 4: {
                while (i < length) {
                    final int t = random.nextInt(123);
                    if ((t >= 97 || (t >= 48 && t <= 57)) && (excludeString == null || excludeString.indexOf((char)t) < 0)) {
                        verifyCode.append((char)t);
                        ++i;
                    }
                }
                break;
            }
            case 5: {
                while (i < length) {
                    final int t = random.nextInt(91);
                    if (t >= 65 && (excludeString == null || excludeString.indexOf((char)t) < 0)) {
                        verifyCode.append((char)t);
                        ++i;
                    }
                }
                break;
            }
            case 6: {
                while (i < length) {
                    final int t = random.nextInt(123);
                    if (t >= 97 && (excludeString == null || excludeString.indexOf((char)t) < 0)) {
                        verifyCode.append((char)t);
                        ++i;
                    }
                }
                break;
            }
        }
        return verifyCode.toString();
    }
    
    public static BufferedImage generateImageCode(final String textCode, final int width, final int height, final int interLine, final boolean randomLocation, final Color backColor, final Color foreColor, final Color lineColor, final String FontType) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        final Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor((backColor == null) ? generateRandomColor() : backColor);
        graphics.fillRect(0, 0, width, height);
        final Random random = new Random();
        if (interLine > 0) {
            final int x = 0;
            int y = 0;
            int y2 = 0;
            for (int i = 0; i < interLine; ++i) {
                graphics.setColor((lineColor == null) ? generateRandomColor() : lineColor);
                y = random.nextInt(height);
                y2 = random.nextInt(height);
                graphics.drawLine(x, y, width, y2);
            }
        }
        final int fsize = (int)(height * 0.8);
        int fx = height - fsize;
        int fy = fsize;
        graphics.setFont(new Font("".equals(FontType) ? "Default" : FontType, 0, fsize));
        for (int j = 0; j < textCode.length(); ++j) {
            fy = (randomLocation ? ((int)((Math.random() * 0.3 + 0.6) * height)) : fy);
            graphics.setColor((foreColor == null) ? generateRandomColor() : foreColor);
            graphics.drawString(new StringBuilder(String.valueOf(textCode.charAt(j))).toString(), fx, fy);
            fx += (int)(fsize * 0.9);
        }
        graphics.dispose();
        return bufferedImage;
    }
}
