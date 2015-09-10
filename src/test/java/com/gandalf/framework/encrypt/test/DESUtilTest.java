package com.gandalf.framework.encrypt.test;

import com.gandalf.framework.encrypt.DESUtil;
import com.gandalf.framework.test.BaseTest;

/**
 * 类DESUtil.java的实现描述：DES加密
 * 
 * @author gandalf 2014-3-17 上午11:18:00
 */
public class DESUtilTest extends BaseTest {

    public static void main(String[] args) throws Exception {
        String key = "E60047BDB94A46C6900BC3EB";
        String data = "hello world!";
        String result = DESUtil.encryptBase64(key, data);
        System.out.println(result);
        String source = DESUtil.decryptBase64(key, result);
        System.out.println(source);
        String result2 = DESUtil.encryptHex(key, data);
        System.out.println(result2);
        String source2 = DESUtil.decryptHex(key, result2);
        System.out.println(source2);
    }
}
