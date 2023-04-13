package com.gandalf.framework.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.ssl.SSLContexts;

/**
 * 类HttpClientFactory.java的实现描述：HttpClient工厂
 * 
 * @author gandalf 2013-6-5 上午11:11:48
 */
public class HttpClientFactory {

	private HttpClientFactory() {
	}

	public static CloseableHttpClient getDefaultHttpClient() {
		return HttpClientHolder.getDefaultHttpClient();
	}
	
	public static CloseableHttpClient getCustomHttpClient(KeyStoreProp ksp) {
		return HttpClientHolder.getCustomHttpClient(ksp);
	}

	/**
	 * 类HttpClientFactory.java的实现描述：请求重试
	 * 
	 * @author gandalf 2013-6-5 下午4:50:35
	 */
	private static class RetryHandler implements HttpRequestRetryHandler {

		private static final int maxTryTime = 3;

		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
			if (executionCount >= maxTryTime) {
				// 如果超过最大重试次数，那么就不要继续了
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				// 如果服务器丢掉了连接，那么就重试
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// 不要重试SSL握手异常
				return false;
			}
			HttpRequest request = (HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);
			boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			if (idempotent) {
				// 如果请求被认为是幂等的，那么就重试
				return true;
			}
			return false;
		}
	}

	private static class HttpClientHolder {

		/**
		 * 连接超时时间:实例化链接超时时间
		 */
		private static final int CONNECTION_TIMEOUT = 30 * 1000;

		/**
		 * 套接字超时时间:获取response的返回超时时间
		 */
		private static final int SOCKET_TIMEOUT = 30 * 1000;

		/**
		 * 连接池里的最大连接数
		 */
		private static final int MAX_TOTAL_CONNECTIONS = 20;

		/**
		 * 每个路由的默认最大连接数
		 */
		private static final int MAX_ROUTE_CONNECTIONS = 50;

		/**
		 * 连接池中 连接请求执行被阻塞的超时时间
		 */
		private static final int CONN_MANAGER_TIMEOUT = 60000;
		
		private static Map<String, CloseableHttpClient> clientMap = new HashMap<String, CloseableHttpClient>();
		
		public synchronized static CloseableHttpClient getDefaultHttpClient() {
			CloseableHttpClient client = clientMap.get("default");
			if(client == null) {
				client = getHttpClient();
				clientMap.put("default", client);
			}
			return client;
		}
		
		public synchronized static CloseableHttpClient getCustomHttpClient(KeyStoreProp ksp) {
			CloseableHttpClient client = clientMap.get(ksp.getCertPath());
			if(client == null) {
				client = getHttpClient(ksp);
				clientMap.put(ksp.getCertPath(), client);
			}
			return client;
		}
		
		private static CloseableHttpClient getHttpClient() {
			return getHttpClient(null);
		}
		
		private static CloseableHttpClient getHttpClient(KeyStoreProp ksp) {
			Registry<ConnectionSocketFactory> registry = null;
			if(ksp != null) {
				registry = getCustomRegistry(ksp);
			} else {
				registry = getDefaultRegistry();
			}

			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();// 接收数据的等待超时时间
			RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
			requestConfigBuilder.setConnectTimeout(CONNECTION_TIMEOUT);// 连接超时时间
			requestConfigBuilder.setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT);//// 从池中获取连接超时时间
			RequestConfig requestConfig = requestConfigBuilder.build();

			PoolingHttpClientConnectionManager pcm = new PoolingHttpClientConnectionManager(registry);
			pcm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
			pcm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
			pcm.setDefaultSocketConfig(socketConfig);

			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			httpClientBuilder.setConnectionManager(pcm);
			httpClientBuilder.setRetryHandler(new RetryHandler());
			httpClientBuilder.setDefaultRequestConfig(requestConfig);
			return httpClientBuilder.build();
		}
		
		private static Registry<ConnectionSocketFactory> getDefaultRegistry(){
			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
					.<ConnectionSocketFactory>create();
			registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
			try {
				//忽略SSL证书验证，也就是接受所有证书，这种方式存在风险
				SSLContext sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, new TrustManager[] { new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {

					}
				} }, new SecureRandom());
				registryBuilder.register("https", new SSLConnectionSocketFactory(sslContext));
				return registryBuilder.build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private static Registry<ConnectionSocketFactory> getCustomRegistry(KeyStoreProp ksp){
			InputStream is = null;
			try {
		    	File certFile = new File(ksp.getCertPath());
		    	is = new FileInputStream(certFile);
		    	KeyStore keyStore = KeyStore.getInstance(ksp.getType());//JCEKS、JKS、DKS、PKCS11、PKCS12等等
				keyStore.load(is, ksp.getPassword().toCharArray());
				
				SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, ksp.getPassword().toCharArray()).build();
				String[] protocols = new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"};
				HostnameVerifier hv = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
				SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, protocols, null, hv);
				
				RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
				registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
				registryBuilder.register("https", sslConnectionSocketFactory);
				
				return registryBuilder.build();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		    return null;
		}
	}
}
