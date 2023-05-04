/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.net.test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.gandalf.framework.net.HttpTool;
import com.gandalf.framework.net.KeyStoreProp;

/**
 * 类HttpToolTest.java的实现描述：http 测试
 * 
 * @author gandalf 2014-6-18 下午05:34:21
 */
public class HttpToolTest {

    public static void main(String[] args) {
//        String url = "http://localhost:8080/aimipay-merchant-web/bat/upload";
//        Map<String, FileBody> fileMap = new HashMap<String, FileBody>();
//        File file = new File("D:\\bat_trans_template.xls");
//        FileBody body = new FileBody(file, "application/vnd.ms-excel", CharsetConstant.GBK);
//        fileMap.put("batFile", body);
//        String result = HttpTool.postFile(url, null, fileMap, Charset.forName(CharsetConstant.UTF_8));
//        System.out.println(result);
    	
//    	String result = HttpTool.get("https://www.giftcardcat.com", null, Charset.forName("UTF-8"));
//    	System.out.println(result);
    }

  
    /**
     * 异步POST
     */
    public static void asyncPost(){
    	String url = "http://localhost:8080/pentagon/login";
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("X-Requested-With", "XMLHttpRequest");
		Map<String, String> paramMap = new HashMap<String, String>();
		String result = HttpTool.post(url, headerMap, paramMap, Charset.forName("utf-8"));
		System.out.println(result);		
    }
}
