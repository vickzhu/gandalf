/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.impl;

import javax.servlet.http.HttpSession;

import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.requestcontext.RequestContextException;
import com.gandalf.framework.requestcontext.wrapper.SessionRequest;
import com.gandalf.framework.requestcontext.wrapper.SessionResponse;
import com.gandalf.framework.web.session.impl.GandalfSessionImpl;

/**
 * 类SessionRequestContext.java的实现描述：实现自定义session
 * 
 * @author gandalf 2014-8-4 下午04:18:26
 */
public class SessionRequestContext extends AbstractRequestContext {

    public SessionRequestContext(RequestContext requestContext) {
        super(requestContext);
        setRequest(new SessionRequest(requestContext));
        setResponse(new SessionResponse(requestContext));
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
        HttpSession session = getRequest().getSession(false);
        if (session instanceof GandalfSessionImpl) {
            ((GandalfSessionImpl) session).commit();
        }
    }
}
