package com.gandalf.framework.tool;

public class MailConfig {

	private String host;
	private String port;
	private boolean auth;
	private boolean starttls;
	
	/**
	 * SMTP登录账号，例如XXX@gmail.com，但是如果使用了代理SMTP，则为代理SMTP的登录账号(AWS, sendinblue etc.)
	 */
	private String userName;
	/**
	 * SMTP登陆密码，如果使用了代理SMTP，则为代理SMPT的登录密码 
	 */
	private String password;
	/**
	 * 发件箱，例如XXX@qq.com，如果没有使用代理SMTP，则和username是一致的
	 */
	private String sender;
	private String nickName;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public boolean isStarttls() {
		return starttls;
	}

	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
