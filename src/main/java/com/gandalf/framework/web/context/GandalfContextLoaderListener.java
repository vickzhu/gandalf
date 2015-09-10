/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.context;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.gandalf.framework.web.session.SessionConfig;

/**
 * 类GandalfContextLoaderListener.java的实现描述：重写<@link ContextLoaderListener>的contextInitialized方法
 * 
 * @author gandalf 2014-4-3 下午4:59:45
 */
public class GandalfContextLoaderListener extends ContextLoaderListener {

    private static Logger logger = LoggerFactory.getLogger(GandalfContextLoaderListener.class);

    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ServletContextHolder.setServletContext(event.getServletContext());
        if (!checkSessionConfig()) {
            logger.error("The value of useNativeSession is false,but the sessionStore is null!");
            System.exit(-1);
        }
    }

    private boolean checkSessionConfig() {
        if (!SessionConfig.isUseNativeSession() && SessionConfig.getSessionStore() == null) {
            return false;
        }
        return true;
    }

}
