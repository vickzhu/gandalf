/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.gandalf.framework.util.StringUtil;

/**
 * 类AbstractRequest.java的实现描述：抽象实现
 * 
 * @author gandalf 2014-8-4 下午05:09:50
 */
public abstract class AbstractRequest extends HttpServletRequestWrapper implements GandalfRequest {

    public AbstractRequest(HttpServletRequest request) {
        super(request);
    }

    public Long getLong(String name) {
        String value = getRequest().getParameter(name);
        if (StringUtil.isBlank(value)) {
            return null;
        }
        return Long.valueOf(value);
    }

    public Integer getInt(String name) {
        String value = getRequest().getParameter(name);
        if (StringUtil.isBlank(value)) {
            return null;
        }
        return Integer.valueOf(value);
    }

    public Float getFloat(String name) {
        String value = getRequest().getParameter(name);
        if (StringUtil.isBlank(value)) {
            return null;
        }
        return Float.valueOf(value);
    }
}
