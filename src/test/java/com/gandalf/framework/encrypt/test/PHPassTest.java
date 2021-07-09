package com.gandalf.framework.encrypt.test;

import com.gandalf.framework.encrypt.PHPass;

public class PHPassTest {

	public static void main(String[] args) {
		String password = PHPass.createHash("admin");
		System.out.println(PHPass.isMatch("admin", "$S$DPA77EJ8.5ixi.B6lLitwCeOksSNuQrvsTmE368T1KYkQ0A919d0"));
	}

}
