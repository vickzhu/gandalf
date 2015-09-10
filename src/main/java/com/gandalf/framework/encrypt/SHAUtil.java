package com.gandalf.framework.encrypt;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

/**
 * 类SHAUtil.java的实现描述：SHA加密
 * 
 * @author gandalf 2014-2-20 下午5:28:54
 */
public class SHAUtil extends BaseCoder {

    /**
     * Returns an SHA-1 digest.
     * 
     * @return An SHA-1 digest instance.
     * @throws RuntimeException when a {@link java.security.NoSuchAlgorithmException} is caught.
     */
    private static MessageDigest getShaDigest() {
        return getDigest("SHA");
    }

    /**
     * Returns an SHA-256 digest.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @return An SHA-256 digest instance.
     * @throws RuntimeException when a {@link java.security.NoSuchAlgorithmException} is caught.
     */
    private static MessageDigest getSha256Digest() {
        return getDigest("SHA-256");
    }

    /**
     * Returns an SHA-384 digest.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @return An SHA-384 digest instance.
     * @throws RuntimeException when a {@link java.security.NoSuchAlgorithmException} is caught.
     */
    private static MessageDigest getSha384Digest() {
        return getDigest("SHA-384");
    }

    /**
     * Returns an SHA-512 digest.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @return An SHA-512 digest instance.
     * @throws RuntimeException when a {@link java.security.NoSuchAlgorithmException} is caught.
     */
    private static MessageDigest getSha512Digest() {
        return getDigest("SHA-512");
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
     * 
     * @param data Data to digest
     * @return SHA-1 digest
     */
    public static byte[] sha(byte[] data) {
        return getShaDigest().digest(data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
     * 
     * @param data Data to digest
     * @return SHA-1 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha(InputStream data) throws IOException {
        return digest(getShaDigest(), data);
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a <code>byte[]</code>.
     * 
     * @param data Data to digest
     * @return SHA-1 digest
     */
    public static byte[] sha(String data) {
        return sha(getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-256 digest
     * @since 1.4
     */
    public static byte[] sha256(byte[] data) {
        return getSha256Digest().digest(data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-256 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha256(InputStream data) throws IOException {
        return digest(getSha256Digest(), data);
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-256 digest
     * @since 1.4
     */
    public static byte[] sha256(String data) {
        return sha256(getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @since 1.4
     */
    public static String sha256Hex(byte[] data) {
        return Hex.encodeHexString(sha256(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha256Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha256(data));
    }

    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-256 digest as a hex string
     * @since 1.4
     */
    public static String sha256Hex(String data) {
        return Hex.encodeHexString(sha256(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-384 digest
     * @since 1.4
     */
    public static byte[] sha384(byte[] data) {
        return getSha384Digest().digest(data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-384 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha384(InputStream data) throws IOException {
        return digest(getSha384Digest(), data);
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-384 digest
     * @since 1.4
     */
    public static byte[] sha384(String data) {
        return sha384(getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @since 1.4
     */
    public static String sha384Hex(byte[] data) {
        return Hex.encodeHexString(sha384(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha384Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha384(data));
    }

    /**
     * Calculates the SHA-384 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-384 digest as a hex string
     * @since 1.4
     */
    public static String sha384Hex(String data) {
        return Hex.encodeHexString(sha384(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-512 digest
     * @since 1.4
     */
    public static byte[] sha512(byte[] data) {
        return getSha512Digest().digest(data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-512 digest
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static byte[] sha512(InputStream data) throws IOException {
        return digest(getSha512Digest(), data);
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a <code>byte[]</code>.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-512 digest
     * @since 1.4
     */
    public static byte[] sha512(String data) {
        return sha512(getBytesUtf8(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @since 1.4
     */
    public static String sha512Hex(byte[] data) {
        return Hex.encodeHexString(sha512(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String sha512Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha512(data));
    }

    /**
     * Calculates the SHA-512 digest and returns the value as a hex string.
     * <p>
     * Throws a <code>RuntimeException</code> on JRE versions prior to 1.4.0.
     * </p>
     * 
     * @param data Data to digest
     * @return SHA-512 digest as a hex string
     * @since 1.4
     */
    public static String sha512Hex(String data) {
        return Hex.encodeHexString(sha512(data));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     * 
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     */
    public static String shaHex(byte[] data) {
        return Hex.encodeHexString(sha(data));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     * 
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     * @throws IOException On error reading from the stream
     * @since 1.4
     */
    public static String shaHex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha(data));
    }

    /**
     * Calculates the SHA-1 digest and returns the value as a hex string.
     * 
     * @param data Data to digest
     * @return SHA-1 digest as a hex string
     */
    public static String shaHex(String data) {
        return Hex.encodeHexString(sha(data));
    }

}
