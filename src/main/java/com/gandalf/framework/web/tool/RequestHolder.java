/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.tool;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 类RequestHolder.java的实现描述：Http请求Holder
 * 
 * @author gandalf 2014-2-20 下午6:20:21
 */
public class RequestHolder extends SpringHolder {

    /**
     * 设置Request属性值
     * 
     * @param name
     * @param value
     */
    public static void setAttribute(String name, Object value) {
        setAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 获得request属性值
     * 
     * @param name
     * @return
     */
    public static Object getAttributeObj(String name) {
        return getAttributeObj(name, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 获得request属性值
     * 
     * @param name
     * @return
     */
    public static String getAttributeStr(String name) {
        return getAttributeStr(name, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 从request中删除属性
     * 
     * @param name
     */
    public static void removeAttribute(String name) {
        removeAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
