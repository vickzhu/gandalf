package com.gandalf.framework.web.session.impl;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.web.context.ServletContextHolder;
import com.gandalf.framework.web.session.SessionAttribute;
import com.gandalf.framework.web.session.SessionConfig;
import com.gandalf.framework.web.session.SessionModel;
import com.gandalf.framework.web.session.SessionStore;

/**
 * 类DatabaseSession.java的实现描述：session的实现
 * 
 * @author gandalf 2014-3-28 上午10:47:36
 */
public class GandalfSessionImpl implements HttpSession {

    private static final Logger logger      = LoggerFactory.getLogger(GandalfSessionImpl.class);

    private SessionStore        sessionStore;
    private SessionModel        sessionModel;
    private SessionAttribute    sessionAttribute;
    private boolean             isNew       = false;                                            // 是否为新建session
    private boolean             invalidated = false;                                            // 是否失效
    private RequestContext      requestContext;

    public GandalfSessionImpl(RequestContext requestContext, String sessionId) {
        this.requestContext = requestContext;
        this.sessionStore = SessionConfig.getSessionStore();
        this.sessionModel = sessionStore.findSessionModel(requestContext, sessionId);
        this.sessionAttribute = sessionStore.getAttributes(requestContext, sessionId);
        if (sessionModel == null) {
            sessionStore.invalidate(requestContext, sessionId);// 确保SessionStore中不会存在SessionAttribute
            isNew = true;
            sessionModel = new SessionModel(sessionId);
        } else {
            sessionModel.setLastAccessedTime(System.currentTimeMillis());
        }
        if (sessionAttribute == null) {
            sessionAttribute = new SessionAttribute(sessionId, null);
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getCreationTime()
     */
    public long getCreationTime() {
        logger.error("getCreationTime:" + sessionModel.getCreationTime());
        assertValid("getCreationTime");
        return sessionModel.getCreationTime();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getId()
     */
    public String getId() {
        logger.error("getId:" + sessionModel.getSessionId());
        return sessionModel.getSessionId();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getLastAccessedTime()
     */
    public long getLastAccessedTime() {
        logger.error("getLastAccessedTime:" + sessionModel.getLastAccessedTime());
        assertValid("getLastAccessedTime");
        return sessionModel.getLastAccessedTime();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getServletContext()
     */
    public ServletContext getServletContext() {
        logger.error("getServletContext");
        return ServletContextHolder.getServletContext();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
     */
    public void setMaxInactiveInterval(int interval) {
        logger.error("setMaxInactiveInterval:" + interval);
        assertValid("setMaxInactiveInterval");
        sessionModel.setMaxInactiveInterval(interval);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
     */
    public int getMaxInactiveInterval() {
        logger.error("getMaxInactiveInterval");
        assertValid("getMaxInactiveInterval");
        return sessionModel.getMaxInactiveInterval();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getSessionContext()
     */
    public HttpSessionContext getSessionContext() {
        logger.error("getSessionContext");
        return null;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {
        logger.error("getAttribute:" + name);
        assertValid("getAttribute");
        return sessionAttribute.getAttrMap().get(name);
        // return CookieUtil.getValue(request, name);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
     */
    public Object getValue(String name) {
        logger.error("getValue:" + name);
        return null;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getAttributeNames()
     */
    public Enumeration getAttributeNames() {
        logger.error("getAttributeNames");
        assertValid("getAttributeNames");

        Set<String> nameSet = new HashSet<String>();
        final Iterator<String> i = nameSet.iterator();
        return new Enumeration<String>() {

            public boolean hasMoreElements() {
                return i.hasNext();
            }

            public String nextElement() {
                return i.next();
            }
        };
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#getValueNames()
     */
    public String[] getValueNames() {
        logger.error("getValueNames");
        return null;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String name, Object value) {
        logger.error("setAttribute:name[" + name + "],value[" + value + "]");
        assertValid("setAttribute");
        sessionAttribute.setModified(true);
        sessionAttribute.getAttrMap().put(name, value);
        // CookieUtil.addCookie(response, name, value.toString());
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
     */
    public void putValue(String name, Object value) {
        logger.error("putValue:name[" + name + "],value[" + value + "]");

    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) {
        logger.error("removeAttribute:" + name);
        assertValid("removeAttribute");
        sessionAttribute.setModified(true);
        sessionAttribute.getAttrMap().remove(name);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     */
    public void removeValue(String name) {
        logger.error("removeValue:" + name);

    }

    /**
     * 让session不可用,首先重置本地缓存，包括SessionModel和SessionAttribute
     * 
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    public void invalidate() {
        if (invalidated) {
            return;
        }
        sessionModel.reset();
        sessionAttribute.getAttrMap().clear();
        sessionStore.invalidate(requestContext, sessionModel.getSessionId());
        invalidated = true;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpSession#isNew()
     */
    public boolean isNew() {
        return isNew;
    }

    public void commit() {
        if (invalidated) {// 已经宣布无效了
            return;
        }
        sessionStore.commit(requestContext, sessionModel, sessionAttribute);
    }

    /**
     * 确保session处于valid状态。
     * 
     * @param methodName 当前正要执行的方法
     */
    protected void assertValid(String methodName) {
        if (invalidated) {
            throw new IllegalStateException("Cannot call method " + methodName
                                            + ": the session has already invalidated");
        }
    }
}
