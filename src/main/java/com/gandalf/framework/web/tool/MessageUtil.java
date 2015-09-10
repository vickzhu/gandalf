/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.tool;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 类MessageUtil.java的实现描述：消息
 * 
 * @author gandalf 2014-7-28 下午04:43:36
 */
public class MessageUtil {

    public static String getLocaleMessage(HttpServletRequest request, Object[] args, String code) {
        WebApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
        return ac.getMessage(code, args, RequestContextUtils.getLocale(request));
    }

}
