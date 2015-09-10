package com.gandalf.framework.encrypt.test;

import com.gandalf.framework.encrypt.AESUtil;
import com.gandalf.framework.test.BaseTest;

/**
 * 类AESUtil.java的实现描述：aes算法
 * 
 * @author gandalf 2014-4-21 下午2:50:31
 */
public class AESUtilTest extends BaseTest {

    public static void main(String[] args) throws Exception {
        String key = "11111111";
        String data = "hello world!";
        String result = AESUtil.encryptBase64(key, data);
        System.out.println(result);
        String source = AESUtil.decryptBase64(key, result);
        System.out.println(source);
        String result2 = AESUtil.encryptHex(key, data);
        System.out.println(result2);
        String source2 = AESUtil.decryptHex(key, result2);
        System.out.println(source2);
    }
}
