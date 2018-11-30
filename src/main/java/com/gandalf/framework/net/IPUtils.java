package com.gandalf.framework.net;

import com.gandalf.framework.constant.SymbolConstant;

/**
 * 判断国内IP
 * 源数据地址：http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest
 * 源数据格式：登记机构|获得该IP段的国家/组织|资源类型|起始IP|IP段长度|分配日期|分配状态
 * start_ip={Long类型起始IP}
 * end_ip = start_ip+{IP段长度}-1
 */
public class IPUtils {
    
    private static final String DOT_REGEX = "\\.";
	
	public static long ip2Long(String ip){
		String[] ipArr = ip.split(DOT_REGEX);
		long l1 = Long.parseLong(ipArr[0]);
		long l2 = Long.parseLong(ipArr[1]);
		long l3 = Long.parseLong(ipArr[2]);
		long l4 = Long.parseLong(ipArr[3]);
		return (l1 << 24) + (l2 << 16) + (l3 << 8) + l4;
	}
	
	public static String long2ip(long ipL){
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf((ipL >>> 24)));
		sb.append(SymbolConstant.DOT);
		sb.append(String.valueOf((ipL & 0x00FFFFFF) >>> 16)); 
		sb.append(SymbolConstant.DOT);
		sb.append(String.valueOf((ipL & 0x0000FFFF) >>> 8));
		sb.append(SymbolConstant.DOT);
		sb.append(String.valueOf((ipL & 0x000000FF)));  
		return sb.toString();
	}
	
}
