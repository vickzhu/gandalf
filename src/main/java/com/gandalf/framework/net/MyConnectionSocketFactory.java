package com.gandalf.framework.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

public class MyConnectionSocketFactory extends SSLConnectionSocketFactory {

	public MyConnectionSocketFactory(final SSLContext sslContext) {
		super(sslContext);
	}

	public MyConnectionSocketFactory(final SSLContext sslContext, final String[] supportedProtocols,
			final String[] supportedCipherSuites, final HostnameVerifier hostnameVerifier) {
		super(sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifier);
	}

	@Override
	public Socket createSocket(final HttpContext context) throws IOException {
		Object socks = context.getAttribute("socks.address");
		if (socks != null) {
			InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
			return new Socket(proxy);
		}
		return super.createSocket(context);
	}

}
