package com.gandalf.framework.velocity.tool;

import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import com.gandalf.framework.web.tool.CookieUtil;

/**
 * 类CookieTool.java的实现描述：这个用于velocity tool中，普通类中请使用<a href="com.gandalf.framework.web.tool.CookieUtil">CookieUtil</a>
 * 
 * @author gandalf 2014-3-10 下午1:57:50
 */
@DefaultKey("cookieTool")
@ValidScope(Scope.REQUEST)
public class CookieTool extends AbstractTool {

    public void addCookie(String key, String value) {
        CookieUtil.addCookie(response, key, value);
    }

    public void addCookies(Map<String, String> cookieParams) {
        CookieUtil.addCookies(response, cookieParams);
    }

    public Cookie getCookie(String cookieName) {
        return CookieUtil.getCookie(request, cookieName);
    }

    public String getValue(String cookieName) {
        return CookieUtil.getValue(request, cookieName);
    }

    public void remove(String cookieName) {
        CookieUtil.remove(request, response, cookieName);
    }

    public void removeAll() {
        CookieUtil.removeAll(request, response);
    }

}
