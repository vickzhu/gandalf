/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类RequestContext.java的实现描述：请求上下文
 * 
 * @author gandalf 2014-8-4 下午04:04:09
 */
public interface RequestContext {

    /**
     * 取得被包装的context。
     * 
     * @return 被包装的<code>RequestContext</code>对象
     */
    RequestContext getWrappedRequestContext();

    /**
     * 取得servletContext对象。
     * 
     * @return <code>ServletContext</code>对象
     */
    ServletContext getServletContext();

    /**
     * 取得request对象。
     * 
     * @return <code>HttpServletRequest</code>对象
     */
    HttpServletRequest getRequest();

    /**
     * 取得response对象。
     * 
     * @return <code>HttpServletResponse</code>对象
     */
    HttpServletResponse getResponse();

    /** 开始一个请求。 */
    void prepare();

    /**
     * 结束一个请求。
     * 
     * @throws RequestContextException 如果失败
     */
    void commit() throws RequestContextException;
}
