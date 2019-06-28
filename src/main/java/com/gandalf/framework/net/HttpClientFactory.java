package com.gandalf.framework.net;

import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
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
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/**
 * 类HttpClientFactory.java的实现描述：HttpClient工厂
 * 
 * @author gandalf 2013-6-5 上午11:11:48
 */
public class HttpClientFactory {

	private HttpClientFactory() {
	}

	public static HttpClient getHttpClient() {
		return HttpClientHolder.httpClient;
	}

	public static CloseableHttpClient getDefaultHttpClient() {
		return HttpClientHolder.httpClient;
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
			HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
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

		private static CloseableHttpClient httpClient;
		private static PoolingHttpClientConnectionManager pcm;
		static {
			RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder
					.<ConnectionSocketFactory> create();
			registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
			registryBuilder.register("https", SSLConnectionSocketFactory.getSocketFactory());
			
			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();//接收数据的等待超时时间
			RequestConfig.Builder  requestConfigBuilder = RequestConfig.custom();
			requestConfigBuilder.setConnectTimeout(CONNECTION_TIMEOUT);//连接超时时间
			requestConfigBuilder.setConnectionRequestTimeout(CONN_MANAGER_TIMEOUT);////从池中获取连接超时时间
			RequestConfig requestConfig = requestConfigBuilder.build();
			
			Registry<ConnectionSocketFactory> registry = registryBuilder.build();
			pcm = new PoolingHttpClientConnectionManager(registry);
			pcm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
			pcm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
			pcm.setDefaultSocketConfig(socketConfig);
			
			HttpClientBuilder httpClientBuilder = HttpClients.custom();
			httpClientBuilder.setConnectionManager(pcm);
			httpClientBuilder.setRetryHandler(new RetryHandler());
			httpClientBuilder.setDefaultRequestConfig(requestConfig);
			httpClient = httpClientBuilder.build();
		}
	}

	// private static class HttpClientHolder {
	//
	// /**
	// * 连接超时时间:实例化链接超时时间
	// */
	// private static final int CONNECTION_TIMEOUT = 30 * 1000;
	//
	// /**
	// * 套接字超时时间:获取response的返回超时时间
	// */
	// private static final int SOCKET_TIMEOUT = 30 * 1000;
	//
	// /**
	// * 连接池里的最大连接数
	// */
	// private static final int MAX_TOTAL_CONNECTIONS = 20;
	//
	// /**
	// * 每个路由的默认最大连接数
	// */
	// private static final int MAX_ROUTE_CONNECTIONS = 50;
	//
	// /**
	// * 连接池中 连接请求执行被阻塞的超时时间
	// */
	// private static final long CONN_MANAGER_TIMEOUT = 60000;
	//
	// private static PoolingClientConnectionManager pcm;
	//
	// private static HttpParams httpParams;
	//
	// private static DefaultHttpClient httpClient;
	//
	// static {
	// SchemeRegistry schemeRegistry = new SchemeRegistry();
	// schemeRegistry.register(new Scheme("http", 80,
	// PlainSocketFactory.getSocketFactory()));
	// try {
	// SSLContext ctx = SSLContext.getInstance("TLS");
	// X509TrustManager tm = new X509TrustManager() {
	// public X509Certificate[] getAcceptedIssuers() {
	// return null;
	// }
	// public void checkClientTrusted(X509Certificate[] arg0, String arg1)
	// throws CertificateException {}
	// public void checkServerTrusted(X509Certificate[] arg0, String arg1)
	// throws CertificateException {}
	// };
	// ctx.init(null, new TrustManager[] { tm }, null);
	// SSLSocketFactory ssf = new SSLSocketFactory(ctx,
	// SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	// schemeRegistry.register(new Scheme("https", 443, ssf));
	// } catch (Exception e) {
	// logger.error("Error when init SSLContext!", e);
	// }
	//
	//// schemeRegistry.register(new Scheme("https", 443,
	// SSLSocketFactory.getSocketFactory()));
	// pcm = new PoolingClientConnectionManager(schemeRegistry);
	// pcm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
	// pcm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
	// // pcm.setMaxPerRoute(new HttpRoute(DEFAULT_TARGETHOST), 20); //
	// 设置对目标主机的最大连接数
	//
	// httpParams = new BasicHttpParams();
	// HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
	// HttpConnectionParams.setConnectionTimeout(httpParams,
	// CONNECTION_TIMEOUT);
	// HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
	// HttpClientParams.setConnectionManagerTimeout(httpParams,
	// CONN_MANAGER_TIMEOUT);
	// // httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT,
	// CONN_MANAGER_TIMEOUT);
	//
	// httpClient = new DefaultHttpClient(pcm, httpParams);
	// httpClient.setHttpRequestRetryHandler(new RetryHandler());
	//
	// }
	// }

}
