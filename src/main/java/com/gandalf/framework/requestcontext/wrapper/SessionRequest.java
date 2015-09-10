/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.wrapper;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.util.StringUtil;
import com.gandalf.framework.web.session.SessionConfig;
import com.gandalf.framework.web.session.impl.GandalfSessionImpl;
import com.gandalf.framework.web.tool.CookieUtil;

/**
 * 类SessionRequestWrapper.java的实现描述：基于session的Request实现
 * 
 * @author gandalf 2014-8-4 下午05:08:54
 */
public class SessionRequest extends AbstractRequest {

    /**
     * 临时保存sesion，一个request可能多个地方用到session
     */
    private HttpSession         session;
    /**
     * session是否已经返回过
     */
    private boolean             sessionReturned          = false;
    /**
     * 请求中的SessionID
     */
    private String              requestedSessionID;
    /**
     * sessionID是否已经被解析过了
     */
    private boolean             requestedSessionIDParsed = false;
    /**
     * 请求的SessionID是否从cookie取得
     */
    private boolean             requestedSessionIDFromCookie;
    /**
     * 请求的sessionID是否从URL中取得
     */
    private boolean             requestedSessionIDFromURL;

    private HttpServletResponse response;
    private RequestContext      requestContext;

    /**
     * @param request
     */
    public SessionRequest(RequestContext requestContext) {
        super(requestContext.getRequest());
        this.requestContext = requestContext;
        setResponse(requestContext.getResponse());
    }

    /**
     * @param response the response to set
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#getSession()
     */
    @Override
    public HttpSession getSession() {
        return getSession(true);

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#getSession(boolean)
     */
    @Override
    public HttpSession getSession(boolean create) {
        if (session != null && sessionReturned) {
            return session;
        }
        if (session == null) {
            if (SessionConfig.isUseNativeSession()) {
                session = super.getSession(create);
            } else {
                String sessionId = getRequestedSessionId();
                if (StringUtil.isBlank(sessionId) && create) {
                    sessionId = UUID.randomUUID().toString().replaceAll(SymbolConstant.H_LINE, StringUtil.EMPTY);
                    if (SessionConfig.isCookieEnabled()) {
                        Cookie cookie = new Cookie(SessionConfig.getSessionIDKey(), sessionId);
                        cookie.setPath(SymbolConstant.SLASH);
                        CookieUtil.addCookie(response, cookie);
                    }
                }
                if (StringUtil.isNotBlank(sessionId)) {
                    session = new GandalfSessionImpl(requestContext, sessionId);
                    if (!isAvailable()) {// session不可用
                        session.invalidate();
                        session = null;
                    }
                }
            }
        }
        sessionReturned = true;
        return session;
    }

    private boolean isAvailable() {
        int maxInactiveInterval = session.getMaxInactiveInterval();
        long lastAccessedTime = session.getLastAccessedTime();
        long current = System.currentTimeMillis();
        long inactiveInterval = current - lastAccessedTime;
        if (maxInactiveInterval * 1000 <= inactiveInterval) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdValid()
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        HttpSession session = getSession(false);
        return session != null && session.getId().equals(requestedSessionID);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromCookie()
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        ensureRequestedSessionID();
        return requestedSessionIDFromCookie;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#isRequestedSessionIdFromURL()
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        ensureRequestedSessionID();
        return requestedSessionIDFromURL;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#getRequestedSessionId()
     */
    @Override
    public String getRequestedSessionId() {
        ensureRequestedSessionID();
        return requestedSessionID;
    }

    /** 确保session ID已经从request中被解析出来了。 */
    private void ensureRequestedSessionID() {
        if (!requestedSessionIDParsed) {
            if (SessionConfig.isCookieEnabled()) {
                requestedSessionID = CookieUtil.getValue(this, SessionConfig.getSessionIDKey());
                requestedSessionIDFromCookie = requestedSessionID != null;
            }

            if (requestedSessionID == null && SessionConfig.isUrlEnabled()) {
                requestedSessionID = decodeSessionIDFromURL();
                requestedSessionIDFromURL = requestedSessionID != null;
            }
        }
    }

    /**
     * 从URL中取得session ID。
     * 
     * @return 如果存在，则返回session ID，否则返回<code>null</code>
     */
    public String decodeSessionIDFromURL() {
        String uri = this.getRequestURI();
        String keyName = SessionConfig.getSessionIDKey();
        int uriLength = uri.length();
        int keyNameLength = keyName.length();

        for (int keyBeginIndex = uri.indexOf(';'); keyBeginIndex >= 0; keyBeginIndex = uri.indexOf(';',
                                                                                                   keyBeginIndex + 1)) {
            keyBeginIndex++;

            if (uriLength - keyBeginIndex <= keyNameLength
                || !uri.regionMatches(keyBeginIndex, keyName, 0, keyNameLength)
                || uri.charAt(keyBeginIndex + keyNameLength) != '=') {
                continue;
            }

            int valueBeginIndex = keyBeginIndex + keyNameLength + 1;
            int valueEndIndex = uri.indexOf(';', valueBeginIndex);

            if (valueEndIndex < 0) {
                valueEndIndex = uriLength;
            }

            return uri.substring(valueBeginIndex, valueEndIndex);
        }

        return null;
    }
}
