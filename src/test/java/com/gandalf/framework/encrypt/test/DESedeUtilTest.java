/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.encrypt.test;

import com.gandalf.framework.encrypt.DESedeUtil;

/**
 * 类ThreeDESCoder.java的实现描述：3DES加密
 * 
 * @author gandalf 2014-5-21 下午6:30:09
 */
public class DESedeUtilTest {

    public static void main(String[] args) {
        String key = "E60047BDB94A46C6900BC3EB";
        String data = "hello world!";
        String hex = DESedeUtil.encryptHex(key, data);
        System.out.println(hex);
        String hexResult = DESedeUtil.decryptHex(key, hex);
        System.out.println(hexResult);
        String base64 = DESedeUtil.encryptBase64(key, data);
        System.out.println(base64);
        String base64Result = DESedeUtil.decryptBase64(key, base64);
        System.out.println(base64Result);
    }

}
