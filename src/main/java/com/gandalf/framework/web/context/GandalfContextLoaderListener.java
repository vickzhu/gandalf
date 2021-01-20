/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.context;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

/**
 * 类GandalfContextLoaderListener.java的实现描述：重写<@link ContextLoaderListener>的contextInitialized方法
 * 
 * @author gandalf 2014-4-3 下午4:59:45
 */
public class GandalfContextLoaderListener extends ContextLoaderListener {

    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ServletContextHolder.setServletContext(event.getServletContext());
    }

}
