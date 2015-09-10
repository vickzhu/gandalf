/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.session.impl;

import java.io.Serializable;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类GandalfSession.java的实现描述：自定义session
 * 
 * @author gandalf 2014-3-25 上午11:12:44
 */
public class NativeSessionImpl implements HttpSession, Serializable {

    private static final Logger logger           = LoggerFactory.getLogger(NativeSessionImpl.class);

    private static final long   serialVersionUID = -8606364114712869205L;
    private HttpSession         session;

    public NativeSessionImpl(HttpSession session) {
        logger.error("create session");
        this.session = session;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getCreationTime()
     */
    public long getCreationTime() {
        logger.error("getCreationTime()");
        return session.getCreationTime();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getId()
     */
    public String getId() {
        logger.error("getId()");
        return session.getId();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getLastAccessedTime()
     */
    public long getLastAccessedTime() {
        logger.error("getLastAccessedTime()");
        return session.getLastAccessedTime();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getServletContext()
     */
    public ServletContext getServletContext() {
        logger.error("getServletContext()");
        return session.getServletContext();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
     */
    public void setMaxInactiveInterval(int interval) {
        logger.error("setMaxInactiveInterval()");
        session.setMaxInactiveInterval(interval);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
     */
    public int getMaxInactiveInterval() {
        logger.error("getMaxInactiveInterval()");
        return session.getMaxInactiveInterval();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getSessionContext()
     */
    public HttpSessionContext getSessionContext() {
        logger.error("getSessionContext()");
        return session.getSessionContext();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {
        logger.error("getAttribute()");
        if (session == null) {
            return null;
        }
        return session.getAttribute(name);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
     */
    public Object getValue(String name) {
        logger.error("getValue()");
        return session.getValue(name);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getAttributeNames()
     */
    public Enumeration getAttributeNames() {
        logger.error("getAttributeNames()");
        if (session == null) {
            return null;
        }
        return session.getAttributeNames();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getValueNames()
     */
    public String[] getValueNames() {
        logger.error("getValueNames()");
        return session.getValueNames();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String name, Object value) {
        logger.error("setAttribute()");
        session.setAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
     */
    public void putValue(String name, Object value) {
        logger.error("putValue()");
        session.putValue(name, value);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) {
        logger.error("removeAttribute()");
        session.removeAttribute(name);

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     */
    public void removeValue(String name) {
        logger.error("removeValue()");
        session.removeValue(name);

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    public void invalidate() {
        logger.error("invalidate()");
        session.invalidate();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#isNew()
     */
    public boolean isNew() {
        logger.error("isNew()");
        return session.isNew();
    }

}
