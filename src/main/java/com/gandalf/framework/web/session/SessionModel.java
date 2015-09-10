/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.session;

/**
 * 类SessionModel.java的实现描述：session模型,改模型不包含Attribute
 * 
 * @author gandalf 2014-3-28 下午1:32:57
 */
public class SessionModel {

    private String  sessionId;
    private Long    creationTime;
    private Long    lastAccessedTime;
    private Integer maxInactiveInterval;

    public SessionModel() {

    }

    public SessionModel(String sessionId) {
        this.sessionId = sessionId;
        reset();
    }

    public void reset() {
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;
        this.maxInactiveInterval = SessionConfig.getMaxInactiveInterval();
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the creationTime
     */
    public Long getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return the lastAccessedTime
     */
    public Long getLastAccessedTime() {
        return lastAccessedTime;
    }

    /**
     * @param lastAccessedTime the lastAccessedTime to set
     */
    public void setLastAccessedTime(Long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    /**
     * @return the maxInactiveInterval
     */
    public Integer getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    /**
     * @param maxInactiveInterval the maxInactiveInterval to set
     */
    public void setMaxInactiveInterval(Integer maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

}
