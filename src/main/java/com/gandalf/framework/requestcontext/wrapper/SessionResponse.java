/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.wrapper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.web.session.SessionConfig;

/**
 * 支持自定义session的response<br/>
 * encodeUrl:是否支持把sessionID编码在URL中，应用必须调用response.encodeURL()
 * 
 * @author gandalf 2014-8-5 上午10:28:07
 */
public class SessionResponse extends AbstractResponse {

    private HttpServletRequest request;

    /**
     * @param response
     */
    public SessionResponse(RequestContext requestContext) {
        super(requestContext.getResponse());
        this.request = requestContext.getRequest();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#encodeUrl(java.lang.String)
     */
    @Override
    public String encodeUrl(String url) {
        return encodeURL(url);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#encodeURL(java.lang.String)
     */
    @Override
    public String encodeURL(String url) {
        if (!SessionConfig.isUseNativeSession() && SessionConfig.isUrlEnabled()) {
            return encodeSessionIDIntoURL(url);
        }
        return super.encodeURL(url);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#encodeRedirectUrl(java.lang.String)
     */
    @Override
    public String encodeRedirectUrl(String url) {
        return encodeRedirectURL(url);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#encodeRedirectURL(java.lang.String)
     */
    @Override
    public String encodeRedirectURL(String url) {
        if (!SessionConfig.isUseNativeSession() && SessionConfig.isUrlEnabled()) {
            return encodeSessionIDIntoURL(url);
        }
        return super.encodeRedirectUrl(url);
    }

    private String encodeSessionIDIntoURL(String url) {
        HttpSession session = request.getSession(false);
        if (session != null
            && (session.isNew() || request.isRequestedSessionIdFromURL() && !request.isRequestedSessionIdFromCookie())) {
            return toEncoded(url, session.getId());
        }
        return url;
    }

    /**
     * Return the specified URL with the specified session identifier suitably encoded.
     * 
     * @param url URL to be encoded with the session id
     * @param sessionId Session id to be included in the encoded URL
     */
    protected String toEncoded(String url, String sessionId) {

        if ((url == null) || (sessionId == null)) {
            return (url);
        }

        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf('?');
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        int pound = path.indexOf('#');
        if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }
        StringBuilder sb = new StringBuilder(path);
        if (sb.length() > 0) { // jsessionid can't be first.
            sb.append(";");
            sb.append(SessionConfig.getSessionIDKey());
            sb.append("=");
            sb.append(sessionId);
        }
        sb.append(anchor);
        sb.append(query);
        return (sb.toString());

    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.wrapper.GandalfResponse#commit()
     */
    public void commit() throws IOException {

    }

}
