package com.gandalf.framework.encrypt.test;

import com.gandalf.framework.encrypt.PHPass;

public class PHPassTest {

	public static void main(String[] args) {
		String source = "HELLO WORLD";
		String password = PHPass.createHash(source);
		System.out.println(password);
		System.out.println(PHPass.isMatch(source, password));
	}

}
