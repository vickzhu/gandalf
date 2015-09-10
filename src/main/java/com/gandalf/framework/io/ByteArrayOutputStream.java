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
 * 字节数组输出流
 * 
 * @author gandalf 2014-7-24 下午05:25:47
 */
public class ByteArrayOutputStream extends OutputStream {

    private static final int DEFAULT_INITIAL_BUFFER_SIZE = 8192;

    // internal buffer
    private byte[]           buffer;
    private int              index;
    private int              capacity;

    // is the stream closed?
    private boolean          closed;

    // is the buffer shared?
    private boolean          shared;

    public ByteArrayOutputStream() {
        this(DEFAULT_INITIAL_BUFFER_SIZE);
    }

    public ByteArrayOutputStream(int initialBufferSize) {
        capacity = initialBufferSize;
        buffer = new byte[capacity];
    }

    @Override
    public void write(int datum) throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        } else {
            if (index >= capacity) {
                // expand the internal buffer
                capacity = capacity * 2 + 1;

                byte[] tmp = new byte[capacity];

                System.arraycopy(buffer, 0, tmp, 0, index);
                buffer = tmp;

                // the new buffer is not shared
                shared = false;
            }

            // store the byte
            buffer[index++] = (byte) datum;
        }
    }

    @Override
    public void write(byte[] data, int offset, int length) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        } else if (offset < 0 || offset + length > data.length || length < 0) {
            throw new IndexOutOfBoundsException();
        } else if (closed) {
            throw new IOException("Stream closed");
        } else {
            if (index + length > capacity) {
                // expand the internal buffer
                capacity = capacity * 2 + length;

                byte[] tmp = new byte[capacity];

                System.arraycopy(buffer, 0, tmp, 0, index);
                buffer = tmp;

                // the new buffer is not shared
                shared = false;
            }

            // copy in the subarray
            System.arraycopy(data, offset, buffer, index, length);
            index += length;
        }
    }

    @Override
    public void close() {
        closed = true;
    }

    public void writeTo(OutputStream out) throws IOException {
        // write the internal buffer directly
        out.write(buffer, 0, index);
    }

    public ByteArray toByteArray() {
        shared = true;
        return new ByteArray(buffer, 0, index);
    }

    public InputStream toInputStream() {
        // return a stream reading from the shared internal buffer
        shared = true;
        return new ByteArrayInputStream(buffer, 0, index);
    }

    public void reset() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        } else {
            if (shared) {
                // create a new buffer if it is shared
                buffer = new byte[capacity];
                shared = false;
            }

            // reset index
            index = 0;
        }
    }

}
