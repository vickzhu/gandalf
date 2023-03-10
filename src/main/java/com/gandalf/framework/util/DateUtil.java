package com.gandalf.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 类DateUtil.java的实现描述：日期工具类
 * 
 * @author gandalf 2014-2-20 下午7:17:21
 */
public class DateUtil {
	
	private static SimpleDateFormat fullSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
     * 格式:yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static Date fullParse(String date) {
        try {
            return fullSdf.parse(date);
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
    
    /**
     * 格式：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String fullFormat(Date date) {
    	if (date == null) {
            return StringUtil.EMPTY;
        }
    	return fullSdf.format(date);
    }
    
    /**
     * GMT时间转换为北京时间
     * @param date
     * @return
     */
    public static Date gmtToBj(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, 8);
		return c.getTime();
	}
	
    /**
     * 北京时间转换为GMT时间
     * @param date
     * @return
     */
	public static Date bjToGmt(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, -8);
		return c.getTime();
	}
	
}
