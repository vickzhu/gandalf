package com.gandalf.framework.web.interceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gandalf.framework.util.StringUtil;
import com.gandalf.framework.web.tool.TokenUtil;

/**
 * 防止CSRF攻击，同时也是防止重复提交的有效手段
 * <ul>
 * <li>默认所有POST、DELETE、PUT方法都会执行CSRF检测，在发生CSRF攻击时返回403状态码 并跳转到403页面，如果有配置redirectURI，则会跳转到该值指定的页面</li>
 * <li>当部分URI不需要CSRF检测时，则将该URI配置为unCheckedUri</li>
 * </ul>
 * 
 * @author gandalf 2014-3-11 下午2:42:00
 */
public class CSRFInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = LoggerFactory.getLogger(CSRFInterceptor.class);

    private Pattern     pattern    = Pattern.compile("GET|HEAD|OPTIONS|TRACE");
    private PathMatcher uriMatcher = new AntPathMatcher();
    private String      expireTip  = "您访问的地址已过期!";

    private static final String ONCE_REQUEST_KEY = "_once_request";//一次请求
    
    /**
     * 在重复提交的情况下跳转的路径
     */
    private String      redirectURI;
    /**
     * 不需要执行CSRF检查的Uri
     */
    private String[]    unCheckedUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if(request.getAttribute(ONCE_REQUEST_KEY) != null){
        	return true;
        } else {
        	request.setAttribute(ONCE_REQUEST_KEY, true);        	
        }
        String method = request.getMethod();
        if (csrfSafeMethod(method)) {
            return true;
        }
        if (unCheckedUri != null) {
            String requestURI = request.getRequestURI();
            String contextPath = request.getContextPath();
            if (requestURI.equals(contextPath)) {
                return true;
            }
            requestURI = requestURI.replaceFirst(contextPath, StringUtil.EMPTY);
            for (String uri : unCheckedUri) {
                if (uriMatcher.match(uri, requestURI)) {
                    return true;
                }
            }
        }
 
    	if (!TokenUtil.checkToken(request, response)) {//Token不正确
    		logger.info("CsrfToken check failed:" + request.getRequestURI());
            if (StringUtil.isBlank(redirectURI)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, expireTip);
            } else {
                response.sendRedirect(redirectURI);
            }
            return false;
        }
        
        return true;
    }
    
    @Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		request.removeAttribute(ONCE_REQUEST_KEY);
		super.afterCompletion(request, response, handler, ex);
	}

    /**
     * 是否为CSRF安全的方法，包括GET,HEAD,OPTIONS,TRACE
     * 
     * @param method
     * @return
     */
    private boolean csrfSafeMethod(String method) {
        Matcher m = pattern.matcher(method);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * @param redirectURI the redirectURI to set
     */
    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    /**
     * @param unCheckedUri the unCheckedUri to set
     */
    public void setUnCheckedUri(String[] unCheckedUri) {
        this.unCheckedUri = unCheckedUri;
    }

}
