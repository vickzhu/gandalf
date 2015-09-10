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
import com.gandalf.framework.requestcontext.RequestContextException;

/**
 * 类SimpleRequestContext.java的实现描述：简单的RequestContext实现
 * 
 * @author gandalf 2014-8-4 下午04:03:43
 */
public class SimpleRequestContext implements RequestContext {

    private HttpServletRequest  request;
    private HttpServletResponse response;
    private ServletContext      servletContext;
    private RequestContext      wrappedRequestContext;

    public SimpleRequestContext(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
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

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.RequestContext#prepare()
     */
    public void prepare() {

    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.RequestContext#commit()
     */
    public void commit() throws RequestContextException {

    }

}
