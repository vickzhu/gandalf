/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.context;

import javax.servlet.ServletContext;

/**
 * 类ServletContextUtil.java的实现描述：上下文
 * 
 * @author gandalf 2014-4-3 下午5:29:52
 */
public class ServletContextHolder {

    public static ServletContext servletContext;

    /**
     * @return the servletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @param servletContext the servletContext to set
     */
    public static void setServletContext(ServletContext servletContext) {
        ServletContextHolder.servletContext = servletContext;
    }

}
