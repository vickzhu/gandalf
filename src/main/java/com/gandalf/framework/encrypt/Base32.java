package com.gandalf.framework.encrypt;

import java.nio.charset.Charset;

public class Base32 extends BaseCoder {

	public static String encode(String text) {
		return byte2Base32(text.getBytes(Charset.forName("UTF-8")));
	}

	public static String decode(String text) {
		return new String(base322Byte(text), Charset.forName("UTF-8"));
	}

	public static void main(String[] args) {
		String text = "你好+";
		String result = Base32.encode(text);
		System.out.println(result);
		String s = Base32.decode(result);
		System.out.println(s);
	}

}
