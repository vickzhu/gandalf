/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.impl;

import java.io.IOException;

import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.requestcontext.RequestContextException;
import com.gandalf.framework.requestcontext.wrapper.LazyCommitResponse;

/**
 * 类LazyCommitRequestContext.java的实现描述：延迟提交，在Cookie-based session情况下需要使用
 * 
 * @author gandalf 2014-8-4 下午04:32:37
 */
public class LazyCommitRequestContext extends AbstractRequestContext {

    public LazyCommitRequestContext(RequestContext requestContext) {
        super(requestContext);
        setResponse(new LazyCommitResponse(requestContext));
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
            ((LazyCommitResponse) getResponse()).commit();
        } catch (IOException e) {
            throw new RequestContextException(e);
        }
    }

}
