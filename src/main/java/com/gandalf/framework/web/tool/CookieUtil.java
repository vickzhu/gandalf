/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.tool;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.CharsetConstant;
import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.encrypt.AESUtil;
import com.gandalf.framework.util.Assert;
import com.gandalf.framework.util.StringUtil;

/**
 * 类CookieUtil.java的实现描述：操作Cookie相关
 * 
 * @author gandalf 2014-3-10 下午1:50:30
 */
public class CookieUtil {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * 写入Cookie
     * 
     * @param response
     * @param key
     * @param value
     */
    public static void addCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(SymbolConstant.SLASH);
        response.addCookie(cookie);
    }

    /**
     * 将cookie加密保存
     * 
     * @param response
     * @param key
     * @param value
     * @param secKey
     */
    public static void addCookieSec(HttpServletResponse response, String key, String value, String secKey) {
        Assert.assertNotNull(key);
        Assert.assertNotNull(secKey);
        try {
            if (StringUtil.isNotBlank(value)) {
                value = AESUtil.encryptBase64(secKey, value);
            }
            value = URLEncoder.encode(value, CharsetConstant.UTF_8);
            addCookie(response, key, value);
        } catch (Exception e) {
            logger.error("Save Cookie failure!", e);
        }
    }

    /**
     * 批量写入Cookie
     * 
     * @param response HttpServletResponse对象
     * @param cookieParams Map.Entry的key作为Cookie的name，value作为Cookie的Value
     */
    public static void addCookies(HttpServletResponse response, Map<String, String> cookieParams) {
        if (MapUtils.isNotEmpty(cookieParams)) {
            for (String key : cookieParams.keySet()) {
                addCookie(response, key, cookieParams.get(key));
            }
        }
    }

    /**
     * 批量加密保存cookie
     * 
     * @param response
     * @param cookieParams
     * @param secKey
     */
    public static void addCookiesSec(HttpServletResponse response, Map<String, String> cookieParams, String secKey) {
        if (MapUtils.isNotEmpty(cookieParams)) {
            for (String key : cookieParams.keySet()) {
                addCookieSec(response, key, cookieParams.get(key), secKey);
            }
        }
    }

    /**
     * 根据Cookie名称得到Cookie
     * 
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 根据Cookie名称得到cookie值
     * 
     * @param request
     * @param cookieName
     * @return
     */
    public static String getValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 根据密钥来解密cookie
     * 
     * @param request
     * @param cookieName
     * @param secKey
     * @return
     */
    public static String getValueSec(HttpServletRequest request, String cookieName, String secKey) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie == null) {
            return null;
        }
        try {
            String value = URLDecoder.decode(cookie.getValue(), CharsetConstant.UTF_8);
            return AESUtil.decryptBase64(secKey, value);
        } catch (Exception e) {
            logger.error("Read cookie failured!", e);
        }
        return null;
    }

    /**
     * 根据名称清除指定Cookie
     * 
     * @param request
     * @param response
     * @param cookieName
     */
    public static void remove(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie == null) {
            return;
        }
        cookie.setValue(null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 清除Cookie中所有的值
     * 
     * @param request
     * @param response
     */
    public static void removeAll(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}
