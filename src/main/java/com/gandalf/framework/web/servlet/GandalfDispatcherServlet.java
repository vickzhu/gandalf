package com.gandalf.framework.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.requestcontext.impl.BufferedRequestContext;
import com.gandalf.framework.requestcontext.impl.LazyCommitRequestContext;
import com.gandalf.framework.requestcontext.impl.SessionRequestContext;
import com.gandalf.framework.requestcontext.impl.SimpleRequestContext;
import com.gandalf.framework.web.context.ServletContextHolder;
import com.gandalf.framework.web.session.SessionConfig;

/**
 * 类GandalfDispatcherServlet.java的实现描述：实现spring DispatcherServlet功能
 * 
 * @author gandalf 2014-4-22 下午4:04:46
 */
public class GandalfDispatcherServlet extends DispatcherServlet {

    private static final String GANDALF_REQUEST_CONTEXT = "_gandalf_request_context_";

    /**
     * 
     */
    private static final long   serialVersionUID        = -8865972286921159509L;

    /*
     * (non-Javadoc)
     * @see org.springframework.web.servlet.DispatcherServlet#doService(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getAttribute(GANDALF_REQUEST_CONTEXT) == null) {
            request.setAttribute(GANDALF_REQUEST_CONTEXT, Boolean.TRUE);
            RequestContext requestContext = buildRequestContext(request, response);
            request = requestContext.getRequest();
            response = requestContext.getResponse();
            try {
                super.doService(request, response);
            } finally {
                request.removeAttribute(GANDALF_REQUEST_CONTEXT);
                commitRequestContext(requestContext);
            }
        } else {
            super.doService(request, response);
        }
    }

    private RequestContext buildRequestContext(HttpServletRequest request, HttpServletResponse response) {
        ServletContext servletContext = ServletContextHolder.getServletContext();
        RequestContext requestContext = new SimpleRequestContext(request, response, servletContext);
        if (SessionConfig.isUseNativeSession()) {
            return requestContext;
        }
        requestContext = new BufferedRequestContext(requestContext);
        requestContext = new LazyCommitRequestContext(requestContext);
        requestContext = new SessionRequestContext(requestContext);
        return requestContext;
    }

    private void commitRequestContext(RequestContext requestContext) {
        for (RequestContext rc = requestContext; rc != null; rc = rc.getWrappedRequestContext()) {
            rc.commit();
        }
    }
}
