/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.velocity.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类AbstractTool.java的实现描述：velocity抽象工具类
 * 
 * @author gandalf 2014-8-5 上午10:55:27
 */
public class AbstractTool {

    protected HttpServletRequest  request;
    protected HttpServletResponse response;

    // --------------------------------------- Setup Methods -------------

    /**
     * Sets the current {@link HttpServletRequest}. This is required for this tool to operate and will throw a
     * NullPointerException if this is not set or is set to {@code null}.
     */
    public void setRequest(HttpServletRequest request) {
        if (request == null) {
            throw new NullPointerException("request should not be null");
        }
        this.request = request;
    }

    /**
     * Sets the current {@link HttpServletResponse}. This is required for this tool to operate and will throw a
     * NullPointerException if this is not set or is set to {@code null}.
     */
    public void setResponse(HttpServletResponse response) {
        if (response == null) {
            throw new NullPointerException("response should not be null");
        }
        this.response = response;
    }
}
