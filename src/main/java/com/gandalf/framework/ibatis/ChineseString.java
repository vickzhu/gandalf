package com.gandalf.framework.ibatis;

import java.io.Serializable;

/**
 * 类ChineseString.java的实现描述：中文字符串
 * 
 * @author gandalf 2014-2-21 下午3:05:04
 */
public class ChineseString implements Serializable {

    private static final long serialVersionUID = -3984873216203045326L;

    /** 默认的构造函数,提供给bdb使用 */
    public ChineseString() {
    }

    /** 提供一构造函数，方便使用 */
    public ChineseString(String value) {

        this.value = value;
    }

    private String value;

    /** 取值 */
    public String getValue() {
        return value;
    }

    /** 设置值 */
    public void setValue(String value) {
        this.value = value;
    }

    /** 取值，与getValue方法一样，为了防止类似print或页面直接使用 */
    public String toString() {
        return value;
    }

    /**
     * 比较函数
     * 
     * @param cs
     * @return 是否相等
     */
    public boolean equals(ChineseString cs) {
        if (cs == null) {
            return false;
        } else {
            return value.equals(cs.getValue());
        }
    }
}
