/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.wrapper;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 类AbstractResponse.java的实现描述：response抽象实现
 * 
 * @author gandalf 2014-8-4 下午05:10:14
 */
public abstract class AbstractResponse extends HttpServletResponseWrapper implements GandalfResponse {

    protected boolean buffering = true;

    /**
     * @param response
     */
    public AbstractResponse(HttpServletResponse response) {
        super(response);
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.wrapper.WrappedResponse#setBuffering(boolean)
     */
    public void setBuffering(boolean buffering) {
        this.buffering = buffering;
    }

    /**
     * @return the buffering
     */
    public boolean isBuffering() {
        return buffering;
    }

}
