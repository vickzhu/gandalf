/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext;

/**
 * 类RequestContextException.java的实现描述：上下文异常
 * 
 * @author gandalf 2014-8-4 下午04:06:16
 */
public class RequestContextException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1585958626063985194L;

    /**
     * 
     */
    public RequestContextException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public RequestContextException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public RequestContextException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public RequestContextException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public RequestContextException(Throwable cause) {
        super(cause);
    }

}
