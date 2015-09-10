/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.token.store;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.CharsetConstant;
import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.encrypt.AESUtil;
import com.gandalf.framework.util.StringUtil;
import com.gandalf.framework.web.tool.CookieUtil;

/**
 * 类TokenStore.java的实现描述：token存储
 * 
 * @author gandalf 2014-4-21 下午5:12:14
 */
public class TokenStore {

    private static final Logger logger = LoggerFactory.getLogger(TokenStore.class);

    private static final String secKey = "i23cxo6^";

    public static void saveToken(HttpServletRequest request, HttpServletResponse response, String name, String token) {
        if (StringUtil.isBlank(token)) {
            return;
        }
        try {
            token = AESUtil.encryptBase64(secKey, token);
            token = URLEncoder.encode(token, CharsetConstant.UTF_8);
            Cookie secCookie = new Cookie(name, token);
            secCookie.setPath(SymbolConstant.SLASH);
            CookieUtil.addCookie(response, secCookie);
        } catch (Exception e) {
            logger.error("Save Cookie failure!", e);
        }
    }

    public static String getToken(HttpServletRequest request, HttpServletResponse response, String name) {
        String value = CookieUtil.getValue(request, name);
        try {
            if (StringUtil.isNotBlank(value)) {
                value = URLDecoder.decode(value, CharsetConstant.UTF_8);
                return AESUtil.decryptBase64(secKey, value);
            }
        } catch (Exception e) {
            logger.error("Read cookie failured!", e);
        }
        return null;
    }

    public static void removeToken(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = CookieUtil.getCookie(request, name);
        cookie.setMaxAge(0);
        cookie.setValue(null);
        CookieUtil.addCookie(response, cookie);
    }
}
