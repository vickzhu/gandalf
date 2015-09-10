package com.gandalf.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.gandalf.framework.constant.CharsetConstant;

public class StringUtil extends StringUtils {

    /**
     * 替换
     * 
     * @param source
     * @param oldChar
     * @param newChar
     * @return
     */
    public static String replace(String source, String oldChar, String newChar) {
        return source.replaceAll(oldChar, newChar);
    }

    /**
     * <pre>
     * 对字符串进行html编码
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String htmlEncode(String value) {
        return StringEscapeUtils.escapeHtml(value);
    }

    /**
     * <pre>
     * 对已编码html进行解码
     * </pre>
     * 
     * @param value
     * @return
     */
    public static String htmlDecode(String value) {
        return StringEscapeUtils.unescapeHtml(value);
    }

    /**
     * 进行URL编码
     * 
     * @param content
     * @return
     */
    public static String urlEncode(String content) {
        try {
            return URLEncoder.encode(content, CharsetConstant.UTF_8);
        } catch (UnsupportedEncodingException e) {
        }
        return EMPTY;
    }

    /**
     * URL解码
     * 
     * @param content
     * @return
     */
    public static String urlDecode(String content) {
        try {
            return URLDecoder.decode(content, CharsetConstant.UTF_8);
        } catch (UnsupportedEncodingException e) {
        }
        return EMPTY;
    }

    private static final char[] DIGITS      = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int    DIGITS_SIZE = DIGITS.length;

    /**
     * 获得1个随机字符
     * 
     * @return
     */
    public static char getRandChar() {
        int rand = (int) Math.round(Math.random() * (DIGITS_SIZE - 1));
        return DIGITS[rand];
    }

    /**
     * 获得指定个数随机字符组成的字符串
     * 
     * @param count 字符数
     * @return
     */
    public static String getRand(int count) {
        char[] rands = new char[count];
        for (int i = 0; i < count; i++) {
            rands[i] = getRandChar();
        }
        return new String(rands);
    }

    /**
     * 获得指定个数字符组成的字符串数组
     * 
     * @param count
     * @return
     */
    public static String[] getRands(int count) {
        String[] rands = new String[count];
        for (int i = 0; i < count; i++) {
            rands[i] = String.valueOf(getRandChar());
        }
        return rands;
    }
}
