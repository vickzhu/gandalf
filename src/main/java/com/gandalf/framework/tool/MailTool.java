package com.gandalf.framework.tool;

import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * 邮件工具类，包括校验邮件是否合法，邮件MX是否存在
 * @author gandalf
 *
 */
public class MailTool {
	
	private static String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	
	private static Pattern pattern = Pattern.compile(regexPattern);

	public static boolean isValid(String email) {
		return pattern.matcher(email).matches();
	}
	
	public static boolean isExist(String email) {
		String domain = email.substring(email.lastIndexOf("@") + 1);
		try {
			return doLookup(domain) > 0;
		} catch (Exception e) {
			System.out.println(email + " : " + e.getMessage());
		}
		return false;
	}
	
	private static int doLookup(String hostName) throws NamingException {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(hostName, new String[] { "MX" });
		Attribute attr = attrs.get("MX");
		if (attr == null) {
			return (0);
		}
		return (attr.size());
	}
	
	public static void main(String[] args) {
		String email = "abc@jfjslfjsadl.com";
//		System.out.println(MailTool.isValid(email));
		System.out.println(MailTool.isExist(email));
		
	}
	
}
