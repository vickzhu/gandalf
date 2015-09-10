/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.session;

import java.util.HashMap;
import java.util.Map;

/**
 * 类SessionAttribute.java的实现描述：session属性
 * 
 * @author gandalf 2014-4-22 下午1:39:52
 */
public class SessionAttribute {

    private boolean             modified = false;
    private String              sessionId;
    private Map<String, Object> attrMap;

    public SessionAttribute(String sessionId, Map<String, Object> attrMap) {
        this.sessionId = sessionId;
        if (attrMap == null) {
            attrMap = new HashMap<String, Object>();
        }
        this.attrMap = attrMap;
    }

    /**
     * @return the modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(boolean modified) {
        this.modified = modified;
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
     * @return the attrMap
     */
    public Map<String, Object> getAttrMap() {
        if (attrMap == null) {
            attrMap = new HashMap<String, Object>();
        }
        return attrMap;
    }

    /**
     * @param attrMap the attrMap to set
     */
    public void setAttrMap(Map<String, Object> attrMap) {
        this.attrMap = attrMap;
    }

}
