/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.impl;

import java.io.IOException;

import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.requestcontext.RequestContextException;
import com.gandalf.framework.requestcontext.wrapper.BufferedResponse;

/**
 * 类BufferedRequestContext.java的实现描述：提供缓冲功能
 * 
 * @author gandalf 2014-8-5 下午06:07:54
 */
public class BufferedRequestContext extends AbstractRequestContext {

    /**
     * @param wrappedContext
     */
    public BufferedRequestContext(RequestContext requestContext) {
        super(requestContext);
        setResponse(new BufferedResponse(requestContext));
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
        try {
            ((BufferedResponse) getResponse()).commit();
        } catch (IOException e) {
            throw new RequestContextException(e);
        }
    }

}
