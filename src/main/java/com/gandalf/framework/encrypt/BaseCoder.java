package com.gandalf.framework.encrypt;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

/**
 * 类BaseCoder.java的实现描述：基础加密组件
 * 
 * @author gandalf 2014-2-21 下午2:21:41
 */
class BaseCoder {

    /**
     * MAC算法可选以下多种算法
     * 
     * <pre>
     * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC              = "HmacMD5";

    private static final int   STREAM_BUFFER_LENGTH = 1024;

    /**
     * Returns a <code>MessageDigest</code> for the given <code>algorithm</code>.
     * 
     * @param algorithm the name of the algorithm requested. See <a
     * href="http://java.sun.com/j2se/1.3/docs/guide/security/CryptoSpec.html#AppA">Appendix A in the Java Cryptography
     * Architecture API Specification & Reference</a> for information about standard algorithm names.
     * @return An MD5 digest instance.
     * @see MessageDigest#getInstance(String)
     * @throws RuntimeException when a {@link java.security.NoSuchAlgorithmException} is caught.
     */
    protected static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Read through an InputStream and returns the digest for the data
     * 
     * @param digest The MessageDigest to use (e.g. MD5)
     * @param data Data to digest
     * @return MD5 digest
     * @throws IOException On error reading from the stream
     */
    protected static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
        byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }
        return digest.digest();
    }

    /**
     * Calls {@link StringUtils#getBytesUtf8(String)}
     * 
     * @param data the String to encode
     * @return encoded bytes
     */
    protected static byte[] getBytesUtf8(String data) {
        return StringUtils.getBytesUtf8(data);
    }

    /**
     * 字节数组转换为16进制
     * 
     * @param data
     * @return
     */
    protected static String byte2Hex(byte[] data) {
        return Hex.encodeHexString(data);
    }

    /**
     * 十六进制转换为字节数组
     * 
     * @param data
     * @return
     * @throws DecoderException
     */
    protected static byte[] hex2Byte(String data) throws DecoderException {
        return new Hex().decode(data.getBytes());
    }

    /**
     * 字节数组转换为Base64
     * 
     * @param b
     * @return
     */
    protected static String byte2Base64(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * Base64转换为字节数组
     * 
     * @param data
     * @return
     */
    protected static byte[] base642Byte(String data) {
        return Base64.decodeBase64(data);
    }

    /**
     * 初始化HMAC密钥
     * 
     * @return
     * @throws Exception
     */
    protected static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return byte2Base64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     * 
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    protected static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(base642Byte(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }
}
