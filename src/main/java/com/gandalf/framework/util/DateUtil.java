package com.gandalf.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类DateUtil.java的实现描述：日期工具类
 * 
 * @author gandalf 2014-2-20 下午7:17:21
 */
public class DateUtil {

    /**
     * 字符串转换成日期
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期格式化为字符串
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return StringUtil.EMPTY;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
