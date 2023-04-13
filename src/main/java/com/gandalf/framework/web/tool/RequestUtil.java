package com.gandalf.framework.web.tool;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.util.StringUtil;

public class RequestUtil {
	
	private static final String AJAX_HEADER = "x-requested-with";
	private static final String UNKNOWN = "unknown";
	
	/**
	 * 判断是否为ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		if(request.getHeader(AJAX_HEADER) != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader(AJAX_HEADER))){
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	/**
     * 获得客户端Ip.
     * 
     * @param request
     * @return ip
     */
    public static String getIp(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {//cloudflare
        	ip = request.getHeader("cf-connecting-ip");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级反向代理
        if (null != ip && !StringUtil.EMPTY.equals(ip.trim())) {
            StringTokenizer st = new StringTokenizer(ip, SymbolConstant.COMMA);
            if (st.countTokens() > 1) {
                return st.nextToken();
            }
        }
        return ip;
    }
    
    /**
     * 获取最近路由IP
     * @param request
     * @return
     */
    public static String getTerminalIp(final HttpServletRequest request) {
        String ip = request.getHeader("cf-connecting-ip");//可能前面加了CloudFlare
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
}
