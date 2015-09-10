/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gandalf.framework.requestcontext.RequestContext;

/**
 * 类AbstractRequestContext.java的实现描述：RequestContext抽象实现
 * 
 * @author gandalf 2014-8-4 下午04:22:39
 */
public abstract class AbstractRequestContext implements RequestContext {

    private final RequestContext wrappedRequestContext;
    private final ServletContext servletContext;
    private HttpServletRequest   request;
    private HttpServletResponse  response;

    public AbstractRequestContext(RequestContext wrappedContext) {
        this.wrappedRequestContext = wrappedContext;
        this.servletContext = wrappedContext.getServletContext();
        this.request = wrappedContext.getRequest();
        this.response = wrappedContext.getResponse();
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.RequestContext#getWrappedRequestContext()
     */
    public RequestContext getWrappedRequestContext() {
        return wrappedRequestContext;
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.RequestContext#getServletContext()
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.RequestContext#getRequest()
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.RequestContext#getResponse()
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

}
