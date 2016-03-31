package com.gandalf.framework.velocity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EscapeHtmlHandlerTest {

	public static void main(String[] args) {
        Matcher matcher = Pattern.compile("\\$\\{?screen_content\\}?|\\$\\!?\\{?tokenUtil\\S*\\}?").matcher("$tokenUtil");
        System.out.println(matcher.matches());
    }
	
}
