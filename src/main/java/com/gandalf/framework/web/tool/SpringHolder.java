/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.tool;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 类SpringHolder.java的实现描述：基础Holder
 * 
 * @author gandalf 2014-2-20 下午6:27:57
 */
class SpringHolder {

    protected static void setAttribute(String name, Object value, int scope) {
        getRequestAttributes().setAttribute(name, value, scope);
    }

    protected static Object getAttributeObj(String name, int scope) {
        return getRequestAttributes().getAttribute(name, scope);
    }

    protected static String getAttributeStr(String name, int scope) {
        Object obj = getRequestAttributes().getAttribute(name, scope);
        return obj == null ? null : (String) obj;
    }

    protected static void removeAttribute(String name, int scope) {
        getRequestAttributes().removeAttribute(name, scope);
    }

    protected static RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }

}
