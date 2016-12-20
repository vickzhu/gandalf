/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.token.store;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * 类TokenStore.java的实现描述：token存储,不能保存在cookie中，因为请求并发时，cookie值还未发送到浏览器，导致cookie未更新
 * 
 * @author gandalf 2014-4-21 下午5:12:14
 */
public class TokenStore {

    public static void saveToken(HttpServletRequest request, HttpServletResponse response, String name, String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }
        request.getSession().setAttribute(name, token);
    }

    public static String getToken(HttpServletRequest request, HttpServletResponse response, String name) {
    	HttpSession session = request.getSession();
    	Object value = session.getAttribute(name);
    	return value == null ? null : String.valueOf(value);
    }

    public static void removeToken(HttpServletRequest request, HttpServletResponse response, String name) {
    	request.getSession().removeAttribute(name);
    }
}
