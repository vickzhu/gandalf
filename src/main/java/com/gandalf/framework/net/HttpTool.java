package com.gandalf.framework.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.brotli.dec.BrotliInputStream;
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
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
    	HttpGet get = new HttpGet(url);
    	if(cookieMap != null){
    		String cookieStr = toCookieStr(cookieMap);
    		get.setHeader(new BasicHeader("Cookie", cookieStr));
    	}
    	if(headerMap == null){
    		headerMap = new HashMap<String, String>();
    	}
    	headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");
		headerMap.put("Accept-Encoding", "gzip, deflate, sdch");
    	for (Map.Entry<String, String> entry : headerMap.entrySet()) {
    		get.addHeader(entry.getKey(), entry.getValue());
    	}
        try {
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
            	boolean gzip = isGzip(response);
            	return EntityUtils.toString(response.getEntity(), charset, gzip);
            } else {
            	logger.error("Access [" + url + "] failure!,status code [" + statusCode + "]");
            }
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Access [" + url + "] failure!", e);
            e.printStackTrace();
        } catch (IOException e) {// 网络异常
            logger.error("Access [" + url + "] failure!", e);
            e.printStackTrace();
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
    	headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");
		headerMap.put("Accept-Encoding", "gzip, deflate, sdch");
    	for (Map.Entry<String, String> entry : headerMap.entrySet()) {
    		get.addHeader(entry.getKey(), entry.getValue());
    	}
        try {
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
            	boolean gzip = isGzip(response);
            	return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET, gzip);
            } else {
            	logger.error("Access [" + url + "] failure!,status code [" + statusCode + "]");
            }
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Access [" + url + "] failure!", e);
            e.printStackTrace();
        } catch (IOException e) {// 网络异常
            logger.error("Access [" + url + "] failure!", e);
            e.printStackTrace();
        } finally {
            get.releaseConnection();
        }
        return null;
    }
    
    private static final String CONTENTENCODING = "Content-Encoding";
    private static final String GZIP = "gzip";
    
    private static boolean isGzip(HttpResponse response){
    	Header[] headerArr = response.getHeaders(CONTENTENCODING);
    	for (Header header : headerArr) {
    		if(header.getName().equals(CONTENTENCODING) && header.getValue().toLowerCase().indexOf(GZIP) > -1){
    			return true;
    		}
	   }
	   return false;
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
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	if(cookieMap != null){
    		String cookieStr = toCookieStr(cookieMap);
    		post.setHeader(new BasicHeader("Cookie", cookieStr));
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
            post.setEntity(new UrlEncodedFormEntity(params, charset));
        }
        try {
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), charset);
            } else {
                logger.error("Access [" + url + "] failure!,status code [" + statusCode + "]");
            }
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
        MultipartEntity entity = new MultipartEntity();
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
                    entity.addPart(name, new StringBody(value, charset));
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("Encode params failure!", e);
                return null;
            }
        }
        post.setEntity(entity);
        try {
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), charset);
            } else {
                logger.error("Access [" + url + "] failure!,status code [" + statusCode + "]");
            }
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
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	if(cookieMap != null){
    		String cookieStr = toCookieStr(cookieMap);
    		post.setHeader(new BasicHeader("Cookie", cookieStr));
    	}
    	if(headerMap == null) {
    		headerMap = new HashMap<String, String>();
    	}
    	headerMap.put("Content-Type", "text/plain;charset=UTF-8");
		headerMap.put("Accept-Encoding", "gzip, deflate, br");
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
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
            	logger.error("Get error status:" + statusCode + ", Url:" + url);
            	System.out.println("Get error status:" + statusCode + ", Url:" + url);
            	return null;
            }
            Header header = response.getLastHeader("Content-Encoding");
            if(header != null && "br".equals(header.getValue())) {
            	return extractBrotliContent(response.getEntity().getContent());
            } else {
            	return EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Post [" + url + "] failure!", e);
            e.printStackTrace();
        } catch (IOException e) {// 网络异常
            logger.error("Post [" + url + "] failure!", e);
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return null;
    }
    
    public static String postJsonWithProxy(String url, Map<String, String> headerMap, String bodyJson, HttpHost proxy) {
    	return postJsonWithProxy(url, headerMap, bodyJson, null, DEFAULT_CHARSET, proxy);
    }
    
    public static String postJsonWithProxy(String url, Map<String, String> headerMap, String bodyJson, Map<String,String> cookieMap, Charset charset, HttpHost proxy) {
    	HttpClient httpClient = HttpClientFactory.getDefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	if(proxy != null) {
	    	RequestConfig requestConfig = RequestConfig.custom()
	                .setProxy(proxy)
	                .setConnectTimeout(5000)
	                .setSocketTimeout(5000)
	                .setConnectionRequestTimeout(3000)
	                .build();
	    	post.setConfig(requestConfig);
    	}
    	if(cookieMap != null){
    		String cookieStr = toCookieStr(cookieMap);
    		post.setHeader(new BasicHeader("Cookie", cookieStr));
    	}
    	if(headerMap == null) {
    		headerMap = new HashMap<String, String>();
    	}
    	headerMap.put("Content-Type", "text/plain;charset=UTF-8");
		headerMap.put("Accept-Encoding", "gzip, deflate, br");
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
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
            	logger.error("Get error status:" + statusCode + ", Url:" + url);
            	System.out.println("Get error status:" + statusCode + ", Url:" + url);
            	return null;
            }
            Header header = response.getLastHeader("Content-Encoding");
            if(header != null && "br".equals(header.getValue())) {
            	return extractBrotliContent(response.getEntity().getContent());
            } else {
            	return EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (ClientProtocolException e) {// 协议错误
            logger.error("Post [" + url + "] failure!", e);
            e.printStackTrace();
        } catch (IOException e) {// 网络异常
            logger.error("Post [" + url + "] failure!", e);
            e.printStackTrace();
        } finally {
            post.releaseConnection();
        }
        return null;
    }
    
    private static String extractBrotliContent(InputStream is) {
    	StringBuilder result = new StringBuilder();
    	BufferedReader reader = null;
    	InputStreamReader isr = null;
    	try {
    		BrotliInputStream stream = new BrotliInputStream(is);
    		isr = new InputStreamReader(stream);
            reader = new BufferedReader(isr);
            String str = null;
            while ((str = reader.readLine()) != null) {
                result.append(str);
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(reader != null) {
    			try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		if(isr != null) {
    			try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
        return result.toString();
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
