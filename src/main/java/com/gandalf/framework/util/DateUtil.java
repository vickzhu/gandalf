package com.gandalf.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 类DateUtil.java的实现描述：日期工具类
 * 
 * @author gandalf 2014-2-20 下午7:17:21
 */
public class DateUtil {
	
	private static SimpleDateFormat simpleSdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat fullSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat fullSdfIso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private static SimpleDateFormat fullSdfIso8601Fractional = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	static {
		fullSdfIso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
		fullSdfIso8601Fractional.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

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
     * 格式：yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date simpleParse(String date) {
    	try {
            return simpleSdf.parse(date);
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
    
    public static String simpleFormat(Date date) {
        if (date == null) {
            return StringUtil.EMPTY;
        }
        return simpleSdf.format(date);
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
     * <pre>
     * 将UTC时间转换为当前系统时区时间
     * Current date and time expressed according to ISO 8601:
     * Date	2023-03-12
     * Date and time in UTC	
     * 	2023-03-12T08:18:49+00:00
     * 	2023-03-12T08:18:49Z
     * 	20230312T081849Z
     * Week	2023-W10
     * Week with weekday	2023-W10-7
     * Ordinal date	2023-071
     * </pre>
     * @param date
     * @return
     */
    public static Date parseIso8601(String date) {
    	if(date.endsWith("Z")) {
    		date = date.replace("Z", "+0000");
    	}
    	try {
			return fullSdfIso8601.parse(date);
		} catch (ParseException e) {
			return null;
		}
    }
    
    /**
     * 将时间转换为yyyy-MM-ddTHH:mm:ssZ格式
     * @param date
     * @return
     */
    public static String formatIso8601(Date date) {
    	return fullSdfIso8601.format(date);
    }
    
    /**
     * <pre>
     * 将UTC时间转换为当前系统时区时间
     * 带小数秒的ISO 8601格式
     * 2023-03-12T08:18:49.011Z
     * </pre>
     * @param date
     * @return
     */
    public static Date parseIso8601Fractional(String date) {
    	if(date.endsWith("Z")) {
    		date = date.replace("Z", "+0000");
    	}
    	try {
			return fullSdfIso8601Fractional.parse(date);
		} catch (ParseException e) {
			return null;
		}
    }
    
    /**
     * 将时间转换为yyyy-MM-ddTHH:mm:ssSSSZ格式
     * @param date
     * @return
     */
    public static String formatIso8601Fractional(Date date) {
    	return fullSdfIso8601Fractional.format(date);
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
