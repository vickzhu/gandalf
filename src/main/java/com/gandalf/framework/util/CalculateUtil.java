package com.gandalf.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 类ArithUtil.java的实现描述：运算工具类
 * 
 * @author gandalf 2013-6-19 下午2:47:24
 */
public class CalculateUtil {

    private static final int DEFAULT_SCALE = 2;

    /**
     * 提供精确加法计算的add方法
     * 
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2) {
        return add(value1, value2, DEFAULT_SCALE);
    }

    /**
     * 提供精确加法计算的add方法
     * 
     * @param value1 被加数
     * @param value2 加数
     * @param scale 精度
     * @return 两个参数的和
     */
    public static double add(double value1, double value2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     * 
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2) {
        return sub(value1, value2, DEFAULT_SCALE);
    }

    /**
     * 提供精确减法运算的sub方法
     * 
     * @param value1 被减数
     * @param value2 减数
     * @param scale 精度
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     * 
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        return mul(value1, value2, DEFAULT_SCALE);
    }

    /**
     * 提供精确乘法运算的mul方法
     * 
     * @param value1 被乘数
     * @param value2 乘数
     * @param scale 精度
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     * 
     * @param value1 被除数
     * @param value2 除数
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2) {
        return div(value1, value2, DEFAULT_SCALE);
    }

    /**
     * 提供精确的除法运算方法div
     * 
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精度
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }
}
