/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.tool;

import org.springframework.web.context.request.RequestAttributes;

/**
 * 类SessionHolder.java的实现描述：http Session Holder
 * 
 * @author gandalf 2014-2-20 下午6:24:18
 */
public class SessionHolder extends SpringHolder {

    /**
     * 设置Session属性值
     * 
     * @param name
     * @param value
     */
    public static void setAttribute(String name, Object value) {
        setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 获得Session属性值
     * 
     * @param name
     * @return
     */
    public static Object getAttributeObj(String name) {
        return getAttributeObj(name, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 获得Session属性值
     * 
     * @param name
     * @return
     */
    public static String getAttributeStr(String name) {
        Object obj = getAttributeObj(name, RequestAttributes.SCOPE_SESSION);
        return obj == null ? null : (String) obj;
    }

    /**
     * 从session中删除属性
     * 
     * @param name
     */
    public static void removeAttribute(String name) {
        removeAttribute(name, RequestAttributes.SCOPE_SESSION);
    }
}
