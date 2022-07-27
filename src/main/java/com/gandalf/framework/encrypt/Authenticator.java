package com.gandalf.framework.encrypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.StringUtils;

/**
 * MFA 验证器
 * 
 * @author Gandalf
 *
 */
public class Authenticator extends BaseCoder {

	/**
	 * 用户密钥长度：80bit
	 */
	private static int secretKeyBits = 80;

	/**
	 * TOTP长度：6
	 */
	private static int codeDigits = 6;

	/**
	 * 有效视窗长度:(-3,3)
	 */
	private static int timeWindowSize = 3;

	/**
	 * TOTP更新周期
	 */
	private static int timeStep = 30;

	/**
	 * TOTP 二维码格式
	 */
	private static String QRCODE_URL = "otpauth://totp/%s:%s?secret=%s&issuer=%s";

	/**
	 * 创建密钥
	 * 
	 * @return
	 */
	public static String createSecretKey() {
		try {
			SecureRandom keyRandom = SecureRandom.getInstance("SHA1PRNG");
			byte[] keyBytes = keyRandom.generateSeed(secretKeyBits / 8);
			return byte2Base32(keyBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成一次性密码(One Time Password)
	 * 
	 * @param secretKey    密钥
	 * @param movingFactor 移动因子
	 * @return
	 */
	private static String generateOtp(String secretKey, long movingFactor) {

		String resultTotp = null;
		byte[] keyBytes = base322Byte(secretKey);

		byte[] movingFactorBytes = new byte[8];
		for (int i = 8; i-- > 0; movingFactor >>>= 8) {
			movingFactorBytes[i] = (byte) movingFactor;
		}
		try {
			byte[] hash = encryptHMAC(keyBytes, movingFactorBytes, "HmacSHA1");
			
			int offset = hash[hash.length - 1] & 0xF;

			int originTotp = ((hash[offset] & 0x7F) << 24) | ((hash[offset + 1] & 0xFF) << 16)
					| ((hash[offset + 2] & 0xFF) << 8) | (hash[offset + 3] & 0xFF);

			int totp = originTotp % (int) Math.pow(10, codeDigits);

			resultTotp = Integer.toString(totp);

			while (resultTotp.length() < 6) {
				resultTotp = "0" + resultTotp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultTotp;

	}

	/**
	 * 基于以下原因会去校验相邻的时间窗口 1、时钟偏移，客户端和服务器端的时间不一致 2、用户输入过程中，已经进入下一个时间窗口
	 * 
	 * @param secretKey 密钥
	 * @param userTotp  用户输入的TOTP
	 * @return
	 */
	public static boolean checkTotp(String secretKey, String userTotp) {
		long timeWindow = System.currentTimeMillis() / TimeUnit.SECONDS.toMillis(timeStep);
		for (int i = -timeWindowSize; ++i < timeWindowSize;) {
			String totp = generateOtp(secretKey, timeWindow + i);
			if (StringUtils.equals(userTotp, totp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 生成TOTP，以指定的时间步长作为时间窗口，比如每30s为一个时间窗口
	 * 
	 * @param secretKey 密钥
	 * @return
	 */
	public static String generateTotp(String secretKey) {
		long timeWindow = System.currentTimeMillis() / TimeUnit.SECONDS.toMillis(timeStep);
		return generateOtp(secretKey, timeWindow);
	}

	/**
	 * 生成HOTP
	 * 
	 * @param secretKey 密钥
	 * @param counter   计数器
	 * @return
	 */
	public static String generateHotp(String secretKey, long counter) {
		return generateOtp(secretKey, counter);
	}

	/**
	 * 生成二维码
	 * 
	 * @param secretKey
	 * @param username
	 * @param issuer
	 * @param prefix
	 * @return
	 */
	public static String generateQrCode(String secretKey, String username, String issuer, String prefix) {
		return String.format(QRCODE_URL, prefix, username, secretKey, issuer);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		SecureRandom keyRandom = SecureRandom.getInstance("SHA1PRNG");
//		byte[] keyBytes = keyRandom.generateSeed(40 / 8);

//		System.out.println(createSecretKey());
		String key = "11NJXXRj+SyPT3HA==";
		String totp = generateTotp(key);
		System.out.println(totp);
		System.out.println(checkTotp(key, totp));
	}
}
