package com.gandalf.framework.net;

import java.math.BigInteger;

import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.util.StringUtil;

/**
 * 判断国内IP 源数据地址：http://ftp.apnic.net/apnic/stats/apnic/delegated-apnic-latest
 * 源数据格式：登记机构|获得该IP段的国家/组织|资源类型|起始IP|IP段长度|分配日期|分配状态 start_ip={Long类型起始IP}
 * end_ip = start_ip+{IP段长度}-1
 */
public class IPUtils {

	private static final String DOT_REGEX = "\\.";

	public static long ip2Long(String ip) {
		String[] ipArr = ip.split(DOT_REGEX);
		long l1 = Long.parseLong(ipArr[0]);
		long l2 = Long.parseLong(ipArr[1]);
		long l3 = Long.parseLong(ipArr[2]);
		long l4 = Long.parseLong(ipArr[3]);
		return (l1 << 24) + (l2 << 16) + (l3 << 8) + l4;
	}

	public static String long2ip(long ipL) {
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

	public static BigInteger ipv6toBigInt(String ipv6) {

		int compressIndex = ipv6.indexOf("::");
		if (compressIndex != -1) {
			String part1s = ipv6.substring(0, compressIndex);
			String part2s = ipv6.substring(compressIndex + 1);
			BigInteger part1 = ipv6toBigInt(part1s);
			BigInteger part2 = ipv6toBigInt(part2s);
			int part1hasDot = 0;
			char ch[] = part1s.toCharArray();
			for (char c : ch) {
				if (c == ':') {
					part1hasDot++;
				}
			}
			// ipv6 has most 7 dot
			return part1.shiftLeft(16 * (7 - part1hasDot)).add(part2);
		}
		String[] str = ipv6.split(SymbolConstant.COLON);
		BigInteger big = BigInteger.ZERO;
		for (int i = 0; i < str.length; i++) {
			// ::1
			if (str[i].isEmpty()) {
				str[i] = "0";
			}
			big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16)).shiftLeft(16 * (str.length - i - 1)));
		}
		return big;
	}

	public static String bigInt2ipv6(BigInteger big) {
		String str = StringUtil.EMPTY;
		BigInteger ff = BigInteger.valueOf(0xffff);
		for (int i = 0; i < 8; i++) {
			str = big.and(ff).toString(16) + SymbolConstant.COLON + str;
			big = big.shiftRight(16);
		}
		str = str.substring(0, str.length() - 1);

		return str.replaceFirst("(^|:)(0+(:|$)){2,8}", "::");
	}

	private static final long ip127_0_0_1 = 2130706433;
	private static final long ip10_0_0_0 = 167772160;
	private static final long ip10_255_255_255 = 184549375;
	private static final long ip172_16_0_0 = 2886729728l;
	private static final long ip172_31_255_255 = 2887778303l;
	private static final long ip192_168_0_0 = 3232235520l;
	private static final long ip192_168_255_255 = 3232301055l;

	/**
	 * 是否为私有IP/内网IP
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isPrivate(String ip) {
		long ipl = ip2Long(ip);
		if (ipl == ip127_0_0_1) {
			return true;
		}
		if (ipl >= ip192_168_0_0 && ipl <= ip192_168_255_255) {
			return true;
		}
		if (ipl >= ip10_0_0_0 && ipl <= ip10_255_255_255) {
			return true;
		}
		if (ipl >= ip172_16_0_0 && ipl <= ip172_31_255_255) {
			return true;
		}
		return false;
	}

	static public void main(String[] args) {
		String ipString = "2607:f0d0:1002:0051:0000:0000:0000:0004";
//	    long[] asd = IPToLong(ipString);
//	    System.out.println(longToIP(asd));
		BigInteger ip = ipv6toBigInt(ipString);
		System.out.println(ip);
		ip = ip.add(BigInteger.valueOf(204800));
		System.out.println(ip);
		System.out.println(bigInt2ipv6(ip));
		String result = HttpTool.get("http://ftp.ripe.net/ripe/stats/delegated-ripencc-latest");
		System.out.println(result);
	}
}
