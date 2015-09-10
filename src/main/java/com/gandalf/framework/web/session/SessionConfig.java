/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.session;

import static com.gandalf.framework.util.Assert.assertNotNull;

/**
 * 类SessionConfig.java的实现描述：session的相关配置
 * 
 * @author gandalf 2014-4-4 下午4:04:15
 */
public class SessionConfig {

    /**
     * 是否使用原生的session
     */
    private static boolean      useNativeSession    = true;

    /**
     * 最大非活动时间，单位秒
     */
    private static int          maxInactiveInterval = 30 * 60;

    /**
     * 用于持久化的session
     */
    private static SessionStore sessionStore;
    /**
     * sessionID保存的键
     */
    private static String       sessionIDKey        = "_gs";
    /**
     * 是否支持sessionID保存到cookie中
     */
    private static boolean      cookieEnabled       = true;
    /**
     * 是否支持sessionID保存到URL中
     */
    private static boolean      urlEnabled          = false;

    /**
     * @return the useNativeSession
     */
    public static boolean isUseNativeSession() {
        return useNativeSession;
    }

    /**
     * @param useNativeSession the useNativeSession to set
     */
    public void setUseNativeSession(Boolean useNativeSession) {
        assertNotNull(useNativeSession);
        SessionConfig.useNativeSession = useNativeSession;
    }

    /**
     * @return the maxInactiveInterval
     */
    public static int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    /**
     * @param maxInactiveInterval the maxInactiveInterval to set
     */
    public void setMaxInactiveInterval(Integer maxInactiveInterval) {
        assertNotNull(maxInactiveInterval);
        SessionConfig.maxInactiveInterval = maxInactiveInterval;
    }

    /**
     * @return the sessionStore
     */
    public static SessionStore getSessionStore() {
        return sessionStore;
    }

    /**
     * @param sessionStore the sessionStore to set
     */
    public void setSessionStore(SessionStore sessionStore) {
        assertNotNull(sessionStore);
        SessionConfig.sessionStore = sessionStore;
    }

    /**
     * @return the sessionIDKey
     */
    public static String getSessionIDKey() {
        return sessionIDKey;
    }

    /**
     * @param sessionIDKey the sessionIDKey to set
     */
    public void setSessionIDKey(String sessionIDKey) {
        SessionConfig.sessionIDKey = sessionIDKey;
    }

    /**
     * @return the cookieEnabled
     */
    public static boolean isCookieEnabled() {
        return cookieEnabled;
    }

    /**
     * @param cookieEnabled the cookieEnabled to set
     */
    public void setCookieEnabled(Boolean cookieEnabled) {
        assertNotNull(cookieEnabled);
        SessionConfig.cookieEnabled = cookieEnabled;
    }

    /**
     * @return the urlEnabled
     */
    public static boolean isUrlEnabled() {
        return urlEnabled;
    }

    /**
     * @param urlEnabled the urlEnabled to set
     */
    public void setUrlEnabled(Boolean urlEnabled) {
        assertNotNull(urlEnabled);
        SessionConfig.urlEnabled = urlEnabled;
    }

}
