package com.gandalf.framework.encrypt.test;

import com.gandalf.framework.encrypt.PHPass;

public class PHPassTest {

	public static void main(String[] args) {
		String password = PHPass.createHash("I love you");
		System.out.println(password);
		System.out.println(PHPass.isMatch("I love you", "$S$DBYbNpplXNNLF/9CVBZXLI4dRr8e5G4o4IGEU8udDFD4Cez1cVFD"));
	}

}
