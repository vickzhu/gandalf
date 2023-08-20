/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;


/**
 * 对流进行转换
 * 
 * @author gandalf 2014-7-24 下午05:58:01
 */
public class StreamUtil {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /** 从输入流读取内容，写入到输出流中。 */
    /**
     * 从输入流中获取内容，并写入到输出流中
     * @param in
     * @param out
     * @param closeIn	是否关闭输入流
     * @param closeOut	是否关闭输出流
     * @throws IOException
     */
    public static void io(InputStream in, OutputStream out, boolean closeIn, boolean closeOut) throws IOException {
        int bufferSize = DEFAULT_BUFFER_SIZE;
        byte[] buffer = new byte[bufferSize];
        int amount;

        try {
            while ((amount = in.read(buffer)) >= 0) {
                out.write(buffer, 0, amount);
            }

            out.flush();
        } finally {
            if (closeIn) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }

            if (closeOut) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 从reader中获取内容并输出到out中
     * @param in
     * @param out
     * @param closeIn	是否关闭reader
     * @param closeOut	是否关闭out
     * @throws IOException
     */
    public static void io(Reader in, Writer out, boolean closeIn, boolean closeOut) throws IOException {
        int bufferSize = DEFAULT_BUFFER_SIZE >> 1;
        char[] buffer = new char[bufferSize];
        int amount;

        try {
            while ((amount = in.read(buffer)) >= 0) {
                out.write(buffer, 0, amount);
            }

            out.flush();
        } finally {
            if (closeIn) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }

            if (closeOut) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 读取输入流转换为字符串
     * @param in	输入流
     * @param charset	编码方式
     * @param closeIn	是否关闭输入流
     * @return	转换后的字符串
     * @throws IOException
     */
    public static String readText(InputStream in, String charset, boolean closeIn) throws IOException {
        Reader reader = charset == null ? new InputStreamReader(in) : new InputStreamReader(in, charset);

        return readText(reader, closeIn);
    }

    /**
     * 读取Reader中的字符串
     * @param reader	
     * @param closeReader	是否关闭Reader
     * @return
     * @throws IOException
     */
    public static String readText(Reader reader, boolean closeReader) throws IOException {
        StringWriter out = new StringWriter();

        io(reader, out, closeReader, true);

        return out.toString();
    }

    /** 将指定<code>InputStream</code>的所有内容全部读出到一个byte数组中。 */
    public static ByteArray readBytes(InputStream in, boolean closeIn) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        io(in, out, closeIn, true);

        return out.toByteArray();
    }

    /** 将字符串写入到指定输出流中。 */
    public static void writeText(CharSequence chars, OutputStream out, String charset, boolean closeOut)
                                                                                                        throws IOException {
        Writer writer = charset == null ? new OutputStreamWriter(out) : new OutputStreamWriter(out, charset);

        writeText(chars, writer, closeOut);
    }

    /** 将字符串写入到指定<code>Writer</code>中。 */
    public static void writeText(CharSequence chars, Writer out, boolean closeOut) throws IOException {
        try {
            out.write(chars.toString());
            out.flush();
        } finally {
            if (closeOut) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /** 将byte数组写入到指定<code>OutputStream</code>中。 */
    public static void writeBytes(byte[] bytes, OutputStream out, boolean closeOut) throws IOException {
        writeBytes(new ByteArray(bytes), out, closeOut);
    }

    /** 将byte数组写入到指定<code>OutputStream</code>中。 */
    public static void writeBytes(ByteArray bytes, OutputStream out, boolean closeOut) throws IOException {
        try {
            out.write(bytes.getRawBytes(), bytes.getOffset(), bytes.getLength());
            out.flush();
        } finally {
            if (closeOut) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
