package com.gandalf.framework.velocity.tool;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.apache.velocity.runtime.Renderable;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.velocity.render.SystemInfoRender;

/**
 * 类HostTool.java的实现描述：主机工具类，用于获取主机相关参数，如域名、MAC
 * 
 * @author gandalf 2013-11-21 上午11:35:51
 */
@DefaultKey("hostTool")
@ValidScope(Scope.REQUEST)
public class HostTool extends AbstractTool {

    /**
     * 上下文
     * 
     * @param request
     * @return
     */
    public Renderable getBasePath() {
        StringBuffer sb = new StringBuffer();
//        sb.append(request.getScheme());
//        sb.append(SymbolConstant.COLON);
        sb.append(SymbolConstant.DOUBLE_SLASH);
        sb.append(request.getServerName());
        if (request.getServerPort() != 80) {
            sb.append(SymbolConstant.COLON + request.getServerPort());
        }
        sb.append(request.getContextPath());
        sb.append(SymbolConstant.SLASH);
        return new SystemInfoRender(sb.toString());
    }

    /**
     * 域名
     * 
     * @param request
     * @return
     */
    public Renderable getDomain() {
        StringBuffer sb = new StringBuffer();
        sb.append(request.getScheme());
        sb.append(SymbolConstant.COLON).append(SymbolConstant.DOUBLE_SLASH);
        sb.append(request.getServerName());
        if (request.getServerPort() != 80) {
            sb.append(SymbolConstant.COLON + request.getServerPort());
        }
        sb.append(SymbolConstant.SLASH);
        return new SystemInfoRender(sb.toString());
    }

    /**
     * 获取MAC地址的方法
     * 
     * @return
     * @throws Exception
     */
    public Renderable getMACAddress() throws Exception {
        InetAddress ia = InetAddress.getLocalHost();// 获取本地IP对象
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append(SymbolConstant.H_LINE);
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return new SystemInfoRender(sb.toString().toUpperCase());
    }
}
