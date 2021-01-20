package com.gandalf.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * 类GandalfDispatcherServlet.java的实现描述：实现spring DispatcherServlet功能
 * 
 * @author gandalf 2014-4-22 下午4:04:46
 */
public class GandalfDispatcherServlet extends DispatcherServlet {
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
    	super.doService(request, response);
    }
}
