/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.wrapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.requestcontext.RequestContext;

/**
 * 类LazyCommitResponse.java的实现描述：延迟提交response
 * 
 * @author gandalf 2014-8-5 下午06:11:01
 */
public class LazyCommitResponse extends AbstractResponse {

    private static final Logger log = LoggerFactory.getLogger(LazyCommitResponse.class);

    private SendError           sendError;
    private String              sendRedirect;
    private boolean             setLocation;
    private boolean             bufferFlushed;
    private int                 status;
    private boolean             headersCommitted;

    /**
     * @param response
     */
    public LazyCommitResponse(RequestContext requestContext) {
        super(requestContext.getResponse());
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#flushBuffer()
     */
    @Override
    public void flushBuffer() throws IOException {
        bufferFlushed = true;

        if (headersCommitted) {
            super.flushBuffer();
        }
    }

    /** 保存sendError的信息。 */
    private class SendError {

        public final int    status;
        public final String message;

        public SendError(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int, java.lang.String)
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {
        if (sendError == null && sendRedirect == null) {
            sendError = new SendError(sc, msg);
            if (headersCommitted) {
                super.sendError(status, msg);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int)
     */
    @Override
    public void sendError(int sc) throws IOException {
        sendError(sc, null);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#sendRedirect(java.lang.String)
     */
    @Override
    public void sendRedirect(String location) throws IOException {
        if (sendError == null && sendRedirect == null) {
            sendRedirect = location;
            if (headersCommitted) {
                super.sendRedirect(location);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#setHeader(java.lang.String, java.lang.String)
     */
    @Override
    public void setHeader(String name, String value) {
        if ("location".equalsIgnoreCase(name)) {
            setLocation = true;
        }
        super.setHeader(name, value);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int, java.lang.String)
     */
    @Override
    public void setStatus(int sc, String sm) {
        status = sc;
        if (headersCommitted) {
            super.setStatus(sc);
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int)
     */
    @Override
    public void setStatus(int sc) {
        setStatus(sc, null);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletResponseWrapper#reset()
     */
    @Override
    public void reset() {
        super.reset();
        sendError = null;
        sendRedirect = null;
        setLocation = false;
        bufferFlushed = false;
        status = 0;
    }

    private void commitHeader() throws IOException {
        headersCommitted = true;

        if (status > 0) {
            log.debug("Set HTTP status to " + status);
            super.setStatus(status);
        }

        if (sendError != null) {
            if (sendError.message == null) {
                log.debug("Set error page: " + sendError.status);

                super.sendError(sendError.status);
            } else {
                log.debug("Set error page: " + sendError.status + " " + sendError.message);

                super.sendError(sendError.status, sendError.message);
            }
        } else if (sendRedirect != null) {
            log.debug("Set redirect location to " + sendRedirect);

            // 将location用输出编码转换一下，这样可以确保包含非US_ASCII字符的URL正确输出
            String charset = getCharacterEncoding();

            if (charset != null) {
                sendRedirect = new String(sendRedirect.getBytes(charset), "8859_1");
            }

            super.sendRedirect(sendRedirect);
        }
    }

    private void commitContent() throws IOException {
        if (bufferFlushed) {
            super.flushBuffer();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.requestcontext.wrapper.GandalfResponse#commit()
     */
    public void commit() throws IOException {
        super.flushBuffer();
        commitHeader();// 提交http头
        commitContent();// 提交http content
    }

}
