package com.gandalf.framework.encrypt;

import java.nio.charset.Charset;

public class Base64 extends BaseCoder {

	public static String encode(String text) {
		return Base64.byte2Base64(text.getBytes(Charset.forName("UTF-8")));
	}
	
	public static String encode(byte[] b) {
		return Base64.byte2Base64(b);
	}

	public static String decode(String text) {
		return new String(Base64.base642Byte(text), Charset.forName("UTF-8"));
	}
	
	public static byte[] decode2Byte(String text) {
		return Base64.base642Byte(text);
	}

	public static void main(String[] args) {
		String text = "你好";
		String result = Base64.encode(text);
		System.out.println(result);
		String s = Base64.decode(result);
		System.out.println(s);
	}

}
