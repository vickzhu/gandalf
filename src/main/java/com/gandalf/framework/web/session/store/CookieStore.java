/*
 * Copyright 2010-2013 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.web.session.store;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.CharsetConstant;
import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.encrypt.AESUtil;
import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.util.StringUtil;
import com.gandalf.framework.web.session.SessionAttribute;
import com.gandalf.framework.web.session.SessionModel;
import com.gandalf.framework.web.session.SessionStore;
import com.gandalf.framework.web.tool.CookieUtil;
import com.gandalf.framework.web.tool.RequestHolder;

/**
 * 类CookieStore.java的实现描述：将session保存到cookie中
 * 
 * @author gandalf 2014-4-18 下午5:01:36
 */
public class CookieStore implements SessionStore {

    private static final Logger logger              = LoggerFactory.getLogger(CookieStore.class);

    private static final String NAME_PATTERN_PREFIX = "_tmp";
    private static final String NAME_PATTERN_SUFFIX = "(\\d+)";
    private Pattern             namePattern         = Pattern.compile(NAME_PATTERN_PREFIX + NAME_PATTERN_SUFFIX);
    private String              secKey              = "nslekt23";
    private static final String tmpModelKey         = "_sm";
    private Integer             maxLength           = (int) (4096 - 200);
    private Integer             maxCount            = 5;

    public void setMaxLength(Integer maxLength) {
        if (maxLength != null) {
            this.maxLength = maxLength;
        }
    }

    public void setMaxCount(Integer maxCount) {
        if (maxCount != null) {
            this.maxCount = maxCount;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.web.session.SessionStore#findSessionModel(java.lang.String)
     */
    public SessionModel findSessionModel(RequestContext requestContext, String sessionId) {
        String model = CookieUtil.getValue(requestContext.getRequest(), tmpModelKey);
        if (StringUtil.isBlank(model)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            model = URLDecoder.decode(model, CharsetConstant.UTF_8);
            model = AESUtil.decryptBase64(secKey, model);
            return mapper.readValue(model, SessionModel.class);
        } catch (Exception e) {
            logger.error("Read session model from cookie failed", e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.web.session.SessionStore#getAttributes(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public SessionAttribute getAttributes(RequestContext requestContext, String sessionId) {
        HttpServletRequest request = RequestHolder.getRequest();
        Cookie[] cookies = request.getCookies();
        TreeMap<String, String> cookieMap = new TreeMap<String, String>();
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            Matcher matcher = namePattern.matcher(cookieName);
            if (matcher.matches()) {
                cookieMap.put(cookieName, cookie.getValue());
            }
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
            sb.append(entry.getValue());
        }
        String attr = sb.toString();
        if (StringUtil.isBlank(attr)) {
            return new SessionAttribute(sessionId, null);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            attr = URLDecoder.decode(attr, CharsetConstant.UTF_8);
            attr = AESUtil.decryptBase64(secKey, attr);
            Map<String, Object> attrMap = mapper.readValue(attr, Map.class);
            return new SessionAttribute(sessionId, attrMap);
        } catch (Exception e) {
            logger.error("Read session model from cookie failed", e);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.web.session.SessionStore#invalidate(java.lang.String)
     */
    public void invalidate(RequestContext requestContext, String sessionId) {
        // 清除所有cookie
        HttpServletRequest request = RequestHolder.getRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            Matcher matcher = namePattern.matcher(cookieName);
            if (matcher.matches() || cookieName.equals(tmpModelKey)) {
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath(SymbolConstant.SLASH);
                CookieUtil.addCookie(requestContext.getResponse(), cookie);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.gandalf.framework.web.session.SessionStore#commit(com.gandalf.framework.web.session.SessionModel)
     */
    public void commit(RequestContext requestContext, SessionModel sessionModel, SessionAttribute sessionAttribute) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String model = mapper.writeValueAsString(sessionModel);
            model = AESUtil.encryptBase64(secKey, model);
            model = URLEncoder.encode(model, CharsetConstant.UTF_8);
            CookieUtil.addCookie(requestContext.getResponse(), tmpModelKey, model);
            if (sessionAttribute.isModified()) {// 属性被更改过了
                String attr = mapper.writeValueAsString(sessionAttribute.getAttrMap());
                attr = AESUtil.encryptBase64(secKey, attr);
                attr = URLEncoder.encode(attr, CharsetConstant.UTF_8);
                if (attr.length() > maxLength * maxCount) {
                    logger.warn("Cookie store full!");
                } else {
                    for (int beginOffset = 0, i = 0; beginOffset < attr.length(); beginOffset += maxLength, i++) {
                        int endOffset = Math.min(beginOffset + maxLength, attr.length());
                        String cookieNameWithIndex = NAME_PATTERN_PREFIX + i;
                        String cookieValue = attr.substring(beginOffset, endOffset);
                        CookieUtil.addCookie(requestContext.getResponse(), cookieNameWithIndex, cookieValue);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Commit session to cookie failed", e);
        } catch (Exception e) {
            logger.error("Commit session to cookie failed", e);
            throw new RuntimeException();
        }

    }
}
