/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.exception;

/**
 * 类EncryptException.java的实现描述：加密异常
 * 
 * @author gandalf 2014-7-29 下午05:01:52
 */
public class EncryptException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 7123769343149111062L;

    /**
     * 
     */
    public EncryptException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public EncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public EncryptException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public EncryptException(Throwable cause) {
        super(cause);
    }

}
