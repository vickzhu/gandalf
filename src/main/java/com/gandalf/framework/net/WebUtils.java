package com.gandalf.framework.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

	private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);   

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
