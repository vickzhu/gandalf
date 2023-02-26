package com.gandalf.framework.net;

/**
 * <pre>
 * type：类型，包括JCEKS、JKS、DKS、PKCS11、PKCS12等等
 * certPath：证书存放位置
 * password：证书密码
 * </pre>
 * 
 * @author gandalf
 *
 */
public class KeyStoreProp {

	private String type = "JKS";// keystore 类型，默认JKS
	private String certPath;// 证书存放位置
	private String password;// 证书密码

	public KeyStoreProp() {
		super();
	}

	public KeyStoreProp(String type, String certPath, String password) {
		super();
		this.type = type;
		this.certPath = certPath;
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
