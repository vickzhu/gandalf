package com.gandalf.framework.encrypt;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.gandalf.framework.constant.CharsetConstant;

/**
 * 类DESUtil.java的实现描述：DES加密，加密数据进行Base64编码
 * 
 * @author gandalf 2014-3-17 上午11:18:00
 */
public class DESUtil extends BaseCoder {

    private static final String DES = "DES";

    /**
     * Description 根据键值进行加密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     * @throws
     */
    public static String encryptBase64(String key, String data) throws Exception {
        byte[] bt = encrypt(key.getBytes(CharsetConstant.UTF_8), data.getBytes(CharsetConstant.UTF_8));
        return byte2Base64(bt);
    }

    /**
     * 默认utf-8编码
     * 
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptHex(String key, String data) throws Exception {
        byte[] keyByte = key.getBytes(Charset.forName(CharsetConstant.UTF_8));
        byte[] dataByte = data.getBytes(Charset.forName(CharsetConstant.UTF_8));
        byte[] result = encrypt(keyByte, dataByte);
        return byte2Hex(result);

    }

    /**
     * Description 根据键值进行解密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws IOException
     */
    public static String decryptBase64(String key, String data) throws Exception {
        byte[] buf = Base64.decodeBase64(data);
        byte[] bt = decrypt(key.getBytes(CharsetConstant.UTF_8), buf);
        return new String(bt);
    }

    public static String decryptHex(String key, String data) throws Exception {
        byte[] keyByte = key.getBytes(Charset.forName(CharsetConstant.UTF_8));
        byte[] dataByte = hex2Byte(data);
        return new String(decrypt(keyByte, dataByte));
    }

    /**
     * Description 根据键值进行加密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] key, byte[] data) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     * 
     * @param data
     * @param key 加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] key, byte[] data) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }

}
