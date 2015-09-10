/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.gandalf.framework.spring.ContextHolder;

/**
 * 类BaseTest.java的实现描述：基础测试类
 * 
 * @author gandalf 2014-2-20 下午4:45:02
 */
@ContextConfiguration(locations = { "classpath*:/spring/**/application-*.xml" })
public class BaseTest extends AbstractJUnit4SpringContextTests {

    @Test
    public void testContext() {
        ApplicationContext context = ContextHolder.getContext();
        System.out.println(context);
    }
}
