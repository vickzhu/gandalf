package com.gandalf.framework.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.CharsetConstant;
import com.gandalf.framework.constant.SymbolConstant;
import com.gandalf.framework.util.StringUtil;

/**
 * 类HttpTool.java的实现描述：http连接类
 * 
 * @author gandalf 2013-6-5 上午10:01:23
 */
public class HttpTool {

    private static Charset      DEFAULT_CHARSET = Charset.forName(CharsetConstant.UTF_8);
    private static final Logger logger          = LoggerFactory.getLogger(HttpTool.class);
    
    private static final String USER_AGENT_KEY = "User-Agent";
    private static final String ACCEPT_ENCODING_KEY = "Accept-Encoding";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_ENCODING_KEY = "Content-Encoding";
    private static final String COOKIE_KEY = "cookie";
    private static final String APPLICATION_JSON = "application/json";
    
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36";
    private static final String ACCEPT_ENCODING = "gzip, deflate, sdch";
    
    public static void main(String[] args) {
    	String result = get("https://download.microsoft.com/download/7/1/D/71D86715-5596-4529-9B13-DA13A5DE5B63/ServiceTags_Public_20221121.json");
    	System.out.println(result);
    }

    /**
     * http get 方法,默认utf-8编码
     * 
     * @param url 链接url
     */
    public static String get(String url) {
        return get(url, DEFAULT_CHARSET);
    }

    /**
     * http get 方法
     * 
     * @param url
     * @param charset
     * @return 请求结果
     */
    public static String get(String url, Charset charset) {
        return get(url, null, charset);
    }
    
    /**
     * http get 方法
     * 
     * @param url
     * @param headerMap	请求头
     * @return 请求结果
     */
    public static String get(String url, Map<String, String> headerMap) {
        return get(url, headerMap, DEFAULT_CHARSET);
    }
    
    /**
     * http get 方法
     * @param url	请求连接
     * @param headerMap	请求头
     * @param charset	字符编码
     * @return
     */
    public static String get(String url, Map<String, String> headerMap, Charset charset) {
        return get(url, headerMap, null, charset);
    }
    
    public static String get(String url, Map<String, String> headerMap, Map<String, String> cookieMap, Charset charset) {
        return get(url, headerMap, cookieMap, null, null);
    }
    
    /**
     * Http Proxy
     * <div>
     * HttpHost proxy = new HttpHost("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param cookieMap
     * @param proxy
     * @return
     */
    public static String getWithProxy(String url, Map<String, String> headerMap, Map<String,String> cookieMap, HttpHost proxy) {
    	
	    RequestConfig requestConfig = RequestConfig.custom()
	    		.setProxy(proxy)
	    		.setConnectTimeout(5000)
	    		.setSocketTimeout(5000)
	    		.setConnectionRequestTimeout(3000)
	    		.build();   
    	
        return get(url, headerMap, cookieMap, requestConfig, null);
    }
    
    /**
     * Socks Proxy
     * <div>
     * InetSocketAddress proxy = new InetSocketAddress("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param cookieMap
     * @param proxy
     * @return
     */
    public static String getWithProxy(String url, Map<String, String> headerMap, Map<String,String> cookieMap, InetSocketAddress proxy) {
    	HttpClientContext context = null;
        if(proxy != null) {
            context = HttpClientContext.create();
            context.setAttribute("socks.address", proxy);
        }
    	return get(url, headerMap, cookieMap, null, context);
    }
    
    public static String get(String url, Map<String, String> headerMap, Map<String, String> cookieMap, RequestConfig requestConfig, HttpClientContext context) {
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
    	HttpGet get = new HttpGet(url);
    	if(requestConfig != null) {
    		get.setConfig(requestConfig);
    	}
    	if(cookieMap != null){
    		String cookieStr = toCookieStr(cookieMap);
    		get.setHeader(new BasicHeader(COOKIE_KEY, cookieStr));
    	}
    	if(headerMap == null){
    		headerMap = new HashMap<String, String>();
    	}
    	if(StringUtil.isBlank(headerMap.get(USER_AGENT_KEY))) {    		
    		headerMap.put(USER_AGENT_KEY, USER_AGENT);
    	}
    	if(StringUtil.isBlank(headerMap.get(ACCEPT_ENCODING_KEY))) {    		
    		headerMap.put(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING);
    	}
    	for (Map.Entry<String, String> entry : headerMap.entrySet()) {
    		get.addHeader(entry.getKey(), entry.getValue());
    	}
        try {
            HttpResponse response = httpClient.execute(get, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
            	logger.error("[" + url + "] return status code [" + statusCode + "]");
            	return null;
            }
            String contentEncoding = null;
        	Header[] header = response.getHeaders(CONTENT_ENCODING_KEY);
        	if(header.length > 0) {
        		contentEncoding = header[0].getValue();
        	}
        	return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET, contentEncoding);
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Access [" + url + "] failure!", e);
        } catch (IOException e) {// 网络异常
            logger.error("Access [" + url + "] failure!", e);
        } finally {
            get.releaseConnection();
        }
        return null;
    }
    
    public static String getWithSsl(String url, Map<String, String> headerMap, KeyStoreProp ksp) {
    	HttpClient httpClient = HttpClientFactory.getCustomHttpClient(ksp);
    	HttpGet get = new HttpGet(url);
    	if(headerMap == null){
    		headerMap = new HashMap<String, String>();
    	}
    	if(StringUtil.isBlank(headerMap.get(USER_AGENT_KEY))) {    		
    		headerMap.put(USER_AGENT_KEY, USER_AGENT);
    	}
    	if(StringUtil.isBlank(headerMap.get(ACCEPT_ENCODING_KEY))) {    		
    		headerMap.put(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING);
    	}
    	for (Map.Entry<String, String> entry : headerMap.entrySet()) {
    		get.addHeader(entry.getKey(), entry.getValue());
    	}
        try {
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
            	logger.error("[" + url + "] return status code [" + statusCode + "]");
            	return null;
            }
            String contentEncoding = null;
        	Header[] header = response.getHeaders(CONTENT_ENCODING_KEY);
        	if(header.length > 0) {
        		contentEncoding = header[0].getValue();
        	}
        	return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET, contentEncoding);
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Access [" + url + "] failure!", e);
        } catch (IOException e) {// 网络异常
            logger.error("Access [" + url + "] failure!", e);
        } finally {
            get.releaseConnection();
        }
        return null;
    }

    /**
     * http post 方法
     * 
     * @param url 请求url
     * @param paramMap 参数map
     */
    public static String post(String url, Map<String, String> paramMap) {
        return post(url, paramMap, DEFAULT_CHARSET);
    }

    /**
     * http post 方法
     * 
     * @param url 链接url
     * @param paramMap 参数map
     * @param charset 字符编码
     */
    public static String post(String url, Map<String, String> paramMap, Charset charset) {
        return post(url, null, paramMap, charset);
    }
    
    /**
     * http post 方法
     * @param url	请求url
     * @param headerMap	请求头
     * @param paramMap	请求参数
     * @param charset	字符编码
     * @return
     */
    public static String post(String url, Map<String, String> headerMap, Map<String, String> paramMap, Charset charset){
        return post(url, headerMap, paramMap, null, charset);
    }
    
    /**
     * http post 方法
     * @param url	请求url
     * @param headerMap	请求头
     * @param paramMap	请求参数
     * @param charset	字符编码
     * @return
     */
    public static String post(String url, Map<String, String> headerMap, Map<String, String> paramMap, Map<String,String> cookieMap, Charset charset){
    	return post(url, headerMap, paramMap, cookieMap, null, null);
    }
    
    /**
     * Http Proxy
     * <div>
     * HttpHost proxy = new HttpHost("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param paramMap
     * @param cookieMap
     * @param proxy
     * @return
     */
    public static String postWithProxy(String url, Map<String, String> headerMap, Map<String, String> paramMap, Map<String,String> cookieMap, HttpHost proxy) {
    	
	    RequestConfig requestConfig = RequestConfig.custom()
	    		.setProxy(proxy)
	    		.setConnectTimeout(5000)
	    		.setSocketTimeout(5000)
	    		.setConnectionRequestTimeout(3000)
	    		.build();   
    	
        return post(url, headerMap, paramMap, cookieMap, requestConfig, null);
    }
    
    /**
     * Socks Proxy
     * <div>
     * InetSocketAddress proxy = new InetSocketAddress("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param paramMap
     * @param cookieMap
     * @param proxy
     * @return
     */
    public static String postWithProxy(String url, Map<String, String> headerMap, Map<String, String> paramMap, Map<String,String> cookieMap, InetSocketAddress proxy) {
    	HttpClientContext context = null;
        if(proxy != null) {
            context = HttpClientContext.create();
            context.setAttribute("socks.address", proxy);
        }
    	return post(url, headerMap, paramMap, cookieMap, null, context);
    }
    
    private static String post(String url, Map<String, String> headerMap, Map<String, String> paramMap, Map<String,String> cookieMap, RequestConfig requestConfig, HttpClientContext context){
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	if(requestConfig != null) {
    		post.setConfig(requestConfig);
    	}
    	if(cookieMap != null){
    		String cookieStr = toCookieStr(cookieMap);
    		post.setHeader(new BasicHeader(COOKIE_KEY, cookieStr));
    	}
    	if(headerMap == null){
    		headerMap = new HashMap<String, String>();
    	}
    	if(StringUtil.isBlank(headerMap.get(USER_AGENT_KEY))) {    		
    		headerMap.put(USER_AGENT_KEY, USER_AGENT);
    	}
    	if(StringUtil.isBlank(headerMap.get(ACCEPT_ENCODING_KEY))) {    		
    		headerMap.put(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING);
    	}
        if(headerMap != null) {
        	for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        		post.addHeader(entry.getKey(), entry.getValue());
        	}
        }
        if (paramMap != null) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            post.setEntity(new UrlEncodedFormEntity(params, DEFAULT_CHARSET));
        }
        try {
            HttpResponse response = httpClient.execute(post, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
            	logger.error("[" + url + "] return status code [" + statusCode + "]");
                return null;
            }
            String contentEncoding = null;
        	Header[] header = response.getHeaders(CONTENT_ENCODING_KEY);
        	if(header.length > 0) {
        		contentEncoding = header[0].getValue();
        	}
        	return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET, contentEncoding);
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Access [" + url + "] failure!", e);
        } catch (IOException e) {// 网络异常
            logger.error("Access [" + url + "] failure!", e);
        } finally {
            post.releaseConnection();
        }
        return null;
    }

    /**
     * 上传文件
     * 
     * @param url
     * @param paramMap
     * @param fileMap
     * @param charset
     * @return
     */
    public static String postFile(String url, Map<String, String> paramMap, Map<String, FileBody> fileMap,
                                  Charset charset) {
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        if (fileMap != null) {
            for (Map.Entry<String, FileBody> entry : fileMap.entrySet()) {
                entity.addPart(entry.getKey(), entry.getValue());
            }
        }
        if (paramMap != null) {
            try {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    String name = URLEncoder.encode(entry.getKey(), charset.name());
                    String value = entry.getValue();
                    entity.addPart(name, new StringBody(value, ContentType.TEXT_PLAIN));
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("Encode params failure!", e);
                return null;
            }
        }
        post.setEntity(entity.build());
        try {
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
            	logger.error("Access [" + url + "] failure!,status code [" + statusCode + "]");
                return null;
            }
            String contentEncoding = null;
        	Header[] header = response.getHeaders(CONTENT_ENCODING_KEY);
        	if(header.length > 0) {
        		contentEncoding = header[0].getValue();
        	}
        	return EntityUtils.toString(response.getEntity(), charset, contentEncoding);
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Access [" + url + "] failure!", e);
        } catch (IOException e) {// 网络异常
            logger.error("Access [" + url + "] failure!", e);
        } finally {
            post.releaseConnection();
        }
        return null;
    }
    
    public static String postJson(String url, Map<String, String> headerMap, String bodyJson) {
    	return postJson(url, headerMap, bodyJson, null, DEFAULT_CHARSET);
    }
    
    public static String postJson(String url, Map<String, String> headerMap, String bodyJson, Map<String,String> cookieMap, Charset charset) {
        return postJson(url, headerMap, bodyJson, cookieMap, DEFAULT_CHARSET, null, null);
    }
    
    /**
     * Http Proxy
     * <div>
     * HttpHost proxy = new HttpHost("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param bodyJson
     * @param proxy
     * @return
     */
    public static String postJsonWithProxy(String url, Map<String, String> headerMap, String bodyJson, HttpHost proxy) {
    	return postJsonWithProxy(url, headerMap, bodyJson, null, proxy);
    }
    
    /**
     * Http Proxy
     * <div>
     * HttpHost proxy = new HttpHost("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param bodyJson
     * @param cookieMap
     * @param proxy
     * @return
     */
    public static String postJsonWithProxy(String url, Map<String, String> headerMap, String bodyJson, Map<String,String> cookieMap, HttpHost proxy) {
    	
	    RequestConfig requestConfig = RequestConfig.custom()
	    		.setProxy(proxy)
	    		.setConnectTimeout(5000)
	    		.setSocketTimeout(5000)
	    		.setConnectionRequestTimeout(3000)
	    		.build();   
    	
        return postJson(url, headerMap, bodyJson, cookieMap, DEFAULT_CHARSET, requestConfig, null);
    }
    
    /**
     * Socks Proxy
     * <div>
     * InetSocketAddress proxy = new InetSocketAddress("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param bodyJson
     * @param proxy
     * @return
     */
    public static String postJsonWithProxy(String url, Map<String, String> headerMap, String bodyJson, InetSocketAddress proxy) {
    	return postJsonWithProxy(url, headerMap, bodyJson, null, proxy);
    }
    
    /**
     * Socks Proxy
     * <div>
     * InetSocketAddress proxy = new InetSocketAddress("127.0.0.1", 1080);
     * </div>
     * @param url
     * @param headerMap
     * @param bodyJson
     * @param cookieMap
     * @param proxy
     * @return
     */
    public static String postJsonWithProxy(String url, Map<String, String> headerMap, String bodyJson, Map<String,String> cookieMap, InetSocketAddress proxy) {
        HttpClientContext context = null;
        if(proxy != null) {
            context = HttpClientContext.create();
            context.setAttribute("socks.address", proxy);
        }
        return postJson(url, headerMap, bodyJson, cookieMap, DEFAULT_CHARSET, null, context);
    }
    
    
    private static String postJson(String url, Map<String, String> headerMap, String bodyJson, Map<String,String> cookieMap, Charset charset, RequestConfig requestConfig, HttpClientContext context) {
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	if(requestConfig != null) {
    		post.setConfig(requestConfig);
    	}
    	if(cookieMap != null){
    		String cookieStr = toCookieStr(cookieMap);
    		post.setHeader(new BasicHeader(COOKIE_KEY, cookieStr));
    	}
    	if(headerMap == null){
    		headerMap = new HashMap<String, String>();
    	}
    	if(StringUtil.isBlank(headerMap.get(USER_AGENT_KEY))) {    		
    		headerMap.put(USER_AGENT_KEY, USER_AGENT);
    	}
    	if(StringUtil.isBlank(headerMap.get(ACCEPT_ENCODING_KEY))) {    		
    		headerMap.put(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING);
    	}
    	if(StringUtil.isBlank(headerMap.get(CONTENT_TYPE_KEY))) {
    		headerMap.put(CONTENT_TYPE_KEY, APPLICATION_JSON);
    	}
        if(headerMap != null) {
        	for (Map.Entry<String, String> entry : headerMap.entrySet()) {
        		post.addHeader(entry.getKey(), entry.getValue());
        	}
        }
        if (StringUtil.isNotBlank(bodyJson)) {
            StringEntity entity = new StringEntity(bodyJson, charset);
            post.setEntity(entity);
        }
        try {
            HttpResponse response = httpClient.execute(post, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
            	logger.error("[" + url + "] return status code [" + statusCode + "]");
            	return null;
            }
            String contentEncoding = null;
        	Header[] header = response.getHeaders(CONTENT_ENCODING_KEY);
        	if(header.length > 0) {
        		contentEncoding = header[0].getValue();
        	}
        	return EntityUtils.toString(response.getEntity(), charset, contentEncoding);
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Post [" + url + "] failure!", e);
        } catch (IOException e) {// 网络异常
            logger.error("Post [" + url + "] failure!", e);
        } finally {
            post.releaseConnection();
        }
        return null;
    }
    
    /**
     * 将Map形式的cookie转换为字符串
     * @param cookieMap
     * @return
     */
    private static String toCookieStr(Map<String,String> cookieMap){
    	StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(SymbolConstant.EQUALS);
			sb.append(entry.getValue());
			sb.append(SymbolConstant.COMMA);
		}
		return sb.toString();
    }

}
