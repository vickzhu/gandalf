/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类ThreeDESCoder.java的实现描述：3DES加密
 * 
 * @author gandalf 2014-5-21 下午6:30:09
 */
public class DESedeUtil extends BaseCoder {

    private static final Logger logger    = LoggerFactory.getLogger(DESedeUtil.class);

    private static final String ALGORITHM = "DESede";
    private static final String PATTERN   = "/ECB/PKCS5Padding";

    // 加密字符串
    public static byte[] encrypt(byte[] key, byte[] data) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESedeKeySpec对象
        DESedeKeySpec dks = new DESedeKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM + PATTERN);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    // 解密字符串
    public static byte[] decrypt(byte[] key, byte[] data) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESedeKeySpec dks = new DESedeKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM + PATTERN);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    public static String encryptHex(String key, String source) {
        try {
            return byte2Hex(encrypt(key.getBytes(), source.getBytes()));
        } catch (Exception e) {
            logger.error("Encrypt failed!", e);
        }
        return null;
    }

    public static String decryptHex(String key, String data) {
        try {
            return new String(decrypt(key.getBytes(), hex2Byte(data)));
        } catch (Exception e) {
            logger.error("Encrypt failed!", e);
        }
        return null;
    }

    public static String encryptBase64(String key, String source) {
        try {
            return byte2Base64(encrypt(key.getBytes(), source.getBytes()));
        } catch (Exception e) {
            logger.error("Encrypt failed!", e);
        }
        return null;
    }

    public static String decryptBase64(String key, String data) {
        try {
            return new String(decrypt(key.getBytes(), base642Byte(data)));
        } catch (Exception e) {
            logger.error("Encrypt failed!", e);
        }
        return null;
    }

}
