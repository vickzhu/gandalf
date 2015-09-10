package com.gandalf.framework.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    /**
     * 获得客户端Ip.
     * 
     * @param request
     * @return ip
     */
    public static String getIp(final HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多级反向代理
        if (null != ip && !"".equals(ip.trim())) {
            StringTokenizer st = new StringTokenizer(ip, ",");
            if (st.countTokens() > 1) {
                return st.nextToken();
            }
        }
        return ip;
    }

    /**
     * 从HttpServletRequest中得到请求的IP地址
     */
    public String getIP(HttpServletRequest request) {
        String realIP = request.getHeader("x-forwarded-for");// 代理服务器会在转发时将头信息放在头信息中
        System.out.println(realIP);
        if (realIP != null && realIP.length() != 0) {
            while ((realIP != null) && (realIP.equals("unknown"))) {// 如果有x-forwarded-for信息,并且等于unknown则继续读
                realIP = request.getHeader("x-forwarded-for");
            }
        }
        if (realIP == null || realIP.length() == 0) {
            realIP = request.getHeader("Proxy-Client-IP");
        }
        if (realIP == null || realIP.length() == 0) {
            realIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (realIP == null || realIP.length() == 0) {
            realIP = request.getRemoteAddr();
        }
        return realIP;
    }

    /**
     * ping IP地址，打印相关信息
     */
    public void ping(String ip) {
        if (!this.checkIP(ip)) {
            return;
        }
        ip = "ping " + ip;
        String line = null;
        try {
            Process pro = Runtime.getRuntime().exec(ip);
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
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
    public boolean checkIP(String ip) {
        String[] ipNum = ip.split("[.]");
        for (int i = 0; i < ipNum.length; i++) {
            try {
                int num = Integer.parseInt(ipNum[i]);
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (Exception e) {
                logger.error("IP地址中不能有非数字字符", e);
                return false;
            }
        }
        return true;
    }
}
