package com.gandalf.framework.tool;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.CharsetConstant;
import com.sun.mail.util.MailSSLSocketFactory;

public class MailSender {

	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

	private static final String PROTOCAL = "mail.transport.protocol";
	private static final String HOST = "mail.smtp.host";
	private static final String PORT = "mail.smtp.port";
	private static final String AUTH = "mail.smtp.auth";
	private static final String STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String SSL_SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static final String SOCKET_FACOTY_CLASS = "mail.smtp.socketFactory.class";
	private static final String SOCKET_FACOTY_FALLBACK = "mail.smtp.socketFactory.fallback";
	private static final String SOCKET_FACOTY_PORT = "mail.smtp.socketFactory.port";
	private static final String SOCKET_FACTORY = "mail.smtp.ssl.socketFactory";
	private static final String SMTP = "smpt";
	private static final String CONTENT_TYPE = "text/html;charset=utf-8";
	
	private static Map<String, Session> sessionPool = new HashMap<String, Session>();

	public static boolean send(MailConfig config, String[] recipients, String title, String content) {
		try {
			Session session = getSession(config);
			Message mailMessage = new MimeMessage(session);
			Address from = new InternetAddress(config.getSender(), config.getNickName(), CharsetConstant.UTF_8);
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to[] = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				to[i] = new InternetAddress(recipients[i]);
			}
			if (recipients.length == 1) {
				mailMessage.setRecipients(Message.RecipientType.TO, to);
			} else {
				mailMessage.setRecipients(Message.RecipientType.BCC, to);
			}
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主题
			mailMessage.setSubject(title);
			// 设置邮件消息的主要内容
			mailMessage.setContent(content, CONTENT_TYPE);
			// 发送邮件
			Transport.send(mailMessage);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error("Send email failed!", e);
			return false;
		}
		return true;
	}
	
	private static Session getSession(MailConfig config) throws GeneralSecurityException {
		Session session = sessionPool.get(config.getHost());
		if(session != null) {
			return session;
		}
		Properties pro = new Properties();
		pro.setProperty(PROTOCAL, SMTP);
		pro.put(HOST, config.getHost());
		pro.put(PORT, config.getPort());
		pro.put(AUTH, config.isAuth());
		pro.put(STARTTLS_ENABLE, config.isStarttls());
		if (config.isStarttls()) {
			pro.put(SOCKET_FACOTY_CLASS, SSL_SOCKET_FACTORY);
			pro.put(SOCKET_FACOTY_FALLBACK, Boolean.FALSE);
			pro.put(SOCKET_FACOTY_PORT, config.getPort());
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			pro.put(SOCKET_FACTORY, sf);
		}
		if(config.isAuth()) {
			session = Session.getInstance(pro, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(config.getUserName(), config.getPassword());
				}
			});
		} else {
			session = Session.getInstance(pro);				
		}
		sessionPool.put(config.getHost(), session);
		return session;
	}

	public static void main(String[] args) {
		MailConfig config = new MailConfig();
		config.setAuth(true);
		config.setHost("smtp.exmail.qq.com");
		config.setNickName("Honey");
		config.setUserName("xxx@gmail.com");
		config.setPassword("Your password");
		config.setPort("465");
		config.setSender("xxx@gmail.com");
		config.setStarttls(true);
		String title = "This is title";
		String content = "This is content!";
		String[] recipients = new String[] {"xxx_xxx@gmail.com"};
		send(config, recipients, title, content);
		System.out.println("The end。。。。。。");
	}

}
