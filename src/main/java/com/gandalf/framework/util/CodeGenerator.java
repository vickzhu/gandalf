package com.gandalf.framework.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 各种码生成器：邀请码，优惠码等等
 * <pre>
 * 将任意数字转换为6位长度字符串，最大支持10亿
 * </pre>
 * @author gandalf
 *
 */
public class CodeGenerator {
	
	private static final String d = "0123456789abcdefghijklmnopqrstuvwxyz";
	private static final long max = ~(-1L << 30);
	
	public static String gen(long num) {
		long digit= d.length();
		StringBuilder sb = new StringBuilder();
		num = max ^ num;
		while(num > 0) {
			int idx = (int)(num % digit);
			char c = d.charAt(idx);
			sb.append(c);
			num = num / digit;
		}
		return sb.toString().toUpperCase();
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Set<String> s = new HashSet<String>();
		for (long i = 0; i < 30000000; i++) {
			String code = gen(i);
			s.add(code);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		System.out.println(s.size());
	}
	
}
