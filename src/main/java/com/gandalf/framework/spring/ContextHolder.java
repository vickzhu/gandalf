package com.gandalf.framework.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类ContextUtil.java的实现描述：获得spring context
 * 
 * @author gandalf 2014-2-20 下午4:48:45
 */
public class ContextHolder implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static ApplicationContext getContext() {
        return CONTEXT;
    }
}
