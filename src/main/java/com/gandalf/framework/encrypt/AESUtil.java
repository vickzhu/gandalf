package com.gandalf.framework.encrypt;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.gandalf.framework.constant.CharsetConstant;
import com.gandalf.framework.exception.EncryptException;

/**
 * 类AESUtil.java的实现描述：aes算法，默认采用UTF-8算法，提供Base64及Hex两种编码实现
 * 
 * @author gandalf 2014-4-21 下午2:50:31
 */
public class AESUtil extends BaseCoder {

    public static String encryptBase64(String key, String data) throws EncryptException {
        return byte2Base64(encrypt(key, data));
    }

    public static String encryptHex(String key, String data) throws EncryptException {
        return byte2Hex(encrypt(key, data));
    }

    public static byte[] encrypt(String key, String data) throws EncryptException {
        SecretKeySpec skeySpec = getKey(key);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(data.getBytes(Charset.forName(CharsetConstant.UTF_8)));
        } catch (Exception e) {
            throw new EncryptException(e);
        }

    }

    public static String decryptBase64(String key, String data) throws EncryptException {
        return new String(decrypt(key, base642Byte(data)));
    }

    public static String decryptHex(String key, String data) throws EncryptException {
        try {
            return new String(decrypt(key, hex2Byte(data)));
        } catch (Exception e) {
            throw new EncryptException(e);
        }
    }

    public static byte[] decrypt(String key, byte[] data) throws EncryptException {
        SecretKeySpec skeySpec = getKey(key);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new EncryptException(e);
        }
    }

    private static SecretKeySpec getKey(String key) throws EncryptException {
        try {
            byte[] arrBTmp = key.getBytes(CharsetConstant.UTF_8);
            byte[] arrB = new byte[16];
            for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
                arrB[i] = arrBTmp[i];
            }
            return new SecretKeySpec(arrB, "AES");
        } catch (Exception e) {
            throw new EncryptException(e);
        }

    }
}
