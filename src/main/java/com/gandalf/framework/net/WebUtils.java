package com.gandalf.framework.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.util.StringUtil;

public class WebUtils {

private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);
    
    private static final String UNKNOWN = "unknown";

    /**
     * 获得客户端Ip.
     * 
     * @param request
     * @return ip
     */
    public static String getIp(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
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
     * ping IP地址，打印相关信息
     */
    public static void ping(String ip) {
        if (!checkIP(ip)) {
            return;
        }
        ip = "ping " + ip;
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec(ip);
            String encode = System.getProperty("sun.jnu.encoding");
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(),encode));
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            logger.error("通信异常", ex);
        }
    }

    /**
     * 校验IP地址是否合法
     * 
     * @param ip
     * @return true=合法<br>
     * false=不合法
     */
    public static boolean checkIP(String ip) {
        String[] ipNum = ip.split("[.]");
        for (int i = 0; i < ipNum.length; i++) {
            try {
                int num = Integer.parseInt(ipNum[i]);
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (Exception e) {
                logger.error("IP地址格式不正确", e);
                return false;
            }
        }
        return true;
    }
}
