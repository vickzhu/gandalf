/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 字节数组
 * 
 * @author gandalf 2014-7-24 下午05:26:11
 */
public class ByteArray {

    private final byte[] bytes;
    private final int    offset;
    private final int    length;

    public ByteArray(byte[] bytes) {
        this(bytes, 0, Integer.MIN_VALUE);
    }

    // TODO
    public ByteArray(byte[] bytes, int offset, int length) {
        // assertNotNull(bytes, "bytes");
        // assertTrue(offset >= 0, "offset", offset);

        if (length == Integer.MIN_VALUE) {
            length = bytes.length - offset;
        }

        // assertTrue(length >= 0 && length <= bytes.length - offset, "length");

        this.bytes = bytes;
        this.offset = offset;
        this.length = length;
    }

    public byte[] getRawBytes() {
        return bytes;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public byte[] toByteArray() {
        byte[] copy = new byte[length];

        System.arraycopy(bytes, offset, copy, 0, length);

        return copy;
    }

    public InputStream toInputStream() {
        return new ByteArrayInputStream(bytes, offset, length);
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(bytes, offset, length);
    }

    @Override
    public String toString() {
        return "byte[" + length + "]";
    }
}
