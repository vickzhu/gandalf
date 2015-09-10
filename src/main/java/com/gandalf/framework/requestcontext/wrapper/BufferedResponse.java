/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.wrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.io.ByteArray;
import com.gandalf.framework.io.ByteArrayOutputStream;
import com.gandalf.framework.io.StreamUtil;
import com.gandalf.framework.requestcontext.RequestContext;
import com.gandalf.framework.util.StringUtil;

/**
 * 类BufferedResponse.java的实现描述：添加缓存机制的response
 * 
 * @author gandalf 2014-8-4 下午05:48:52
 */
public class BufferedResponse extends AbstractResponse {

    private static final Logger   log = LoggerFactory.getLogger(BufferedResponse.class);
    private ServletOutputStream   stream;
    private PrintWriter           streamAdapter;
    private PrintWriter           writer;
    private ServletOutputStream   writerAdapter;

    /**
     * 暂存response.getOutputStream()中写入的字节流
     */
    private ByteArrayOutputStream byteArrayOutputStream;
    /**
     * 暂存response.getWriter()中写入的字符流
     */
    private StringWriter          stringWriter;

    /**
     * @param response
     */
    public BufferedResponse(RequestContext requestContext) {
        super(requestContext.getResponse());
    }

    /**
     * 取得输出流。
     * 
     * @return response的输出流
     * @throws IOException 输入输出失败
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (stream != null) {
            return stream;
        }

        if (writer != null) {
            // 如果getWriter方法已经被调用，则将writer转换成OutputStream
            // 这样做会增加少量额外的内存开销，但标准的servlet engine不会遇到这种情形，
            // 只有少数servlet engine需要这种做法（resin）。
            if (writerAdapter != null) {
                return writerAdapter;
            } else {
                log.debug("Attampt to getOutputStream after calling getWriter.  This may cause unnecessary system cost.");
                writerAdapter = new WriterOutputStream(writer, getCharacterEncoding());
                return writerAdapter;
            }
        }

        if (buffering) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            stream = new BufferedServletOutputStream(byteArrayOutputStream);
            log.debug("Created new byte buffer");
        } else {
            stream = super.getOutputStream();
        }

        return stream;
    }

    /**
     * 取得输出字符流。
     * 
     * @return response的输出字符流
     * @throws IOException 输入输出失败
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        if (writer != null) {
            return writer;
        }

        if (stream != null) {
            // 如果getOutputStream方法已经被调用，则将stream转换成PrintWriter。
            // 这样做会增加少量额外的内存开销，但标准的servlet engine不会遇到这种情形，
            // 只有少数servlet engine需要这种做法（resin）。
            if (streamAdapter != null) {
                return streamAdapter;
            } else {
                log.debug("Attampt to getWriter after calling getOutputStream.  This may cause unnecessary system cost.");
                streamAdapter = new PrintWriter(new OutputStreamWriter(stream, getCharacterEncoding()), true);
                return streamAdapter;
            }
        }

        if (buffering) {
            // 注意，servletWriter一旦创建，就不改变，
            // 如果需要改变，只需要改变其下面的chars流即可。
            stringWriter = new StringWriter();
            writer = new BufferedServletWriter(stringWriter);
            log.debug("Created new character buffer");
        } else {
            writer = super.getWriter();
        }

        return writer;
    }

    /**
     * 设置content长度。该调用只在<code>setBuffering(false)</code>时有效。
     * 
     * @param length content长度
     */
    @Override
    public void setContentLength(int length) {
        if (!buffering) {
            super.setContentLength(length);
        }
    }

    /**
     * 冲洗buffer。
     * 
     * @throws IOException 如果失败
     */
    @Override
    public void flushBuffer() throws IOException {
        if (buffering) {
            flushBufferAdapter();

            if (writer != null) {
                writer.flush();
            } else if (stream != null) {
                stream.flush();
            }
        } else {
            super.flushBuffer();
        }
    }

    /**
     * 清除所有buffers，常用于显示出错信息。
     * 
     * @throws IllegalStateException 如果response已经commit
     */
    @Override
    public void resetBuffer() {
        if (buffering) {
            flushBufferAdapter();
            if (stream != null) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                ((BufferedServletOutputStream) stream).updateOutputStream(byteArrayOutputStream);
            }
            if (writer != null) {
                stringWriter = new StringWriter();
                ((BufferedServletWriter) writer).updateWriter(stringWriter);
            }
        }
        super.resetBuffer();
    }

    /**
     * 设置是否将所有信息保存在内存中。
     * 
     * @return 如果是，则返回<code>true</code>
     */
    public boolean isBuffering() {
        return buffering;
    }

    /**
     * 设置buffer模式，如果设置成<code>true</code>，表示将所有信息保存在内存中，否则直接输出到原始response中。
     * <p>
     * 此方法必须在<code>getOutputStream</code>和<code>getWriter</code>方法之前执行，否则将抛出 <code>IllegalStateException</code>。
     * </p>
     * 
     * @param buffering 是否buffer内容
     * @throws IllegalStateException <code>getOutputStream</code>或 <code>getWriter</code>方法已经被执行
     */
    public void setBuffering(boolean buffering) {
        if (stream == null && writer == null) {
            if (this.buffering != buffering) {
                this.buffering = buffering;
                log.debug("Set buffering " + (buffering ? "on" : "off"));
            }
        } else {
            if (this.buffering != buffering) {
                throw new IllegalStateException(
                                                "Unable to change the buffering mode since the getOutputStream() or getWriter() method has been called");
            }
        }
    }

    /**
     * 将buffer中的内容提交到真正的servlet输出流中。
     * <p>
     * 如果从来没有执行过<code>getOutputStream</code>或<code>getWriter</code> 方法，则该方法不做任何事情。
     * </p>
     * 
     * @throws IOException 如果输入输出失败
     * @throws IllegalStateException 如果不是在buffer模式，或buffer栈中不止一个buffer
     */
    public void commitBuffer() throws IOException {
        if (stream == null && writer == null) {
            return;
        }

        if (!buffering) {
            throw new IllegalStateException("Buffering mode is required for commitBuffer");
        }

        // 输出bytes
        if (stream != null) {
            flushBufferAdapter();

            OutputStream ostream = super.getOutputStream();
            ByteArray bytes = byteArrayOutputStream.toByteArray();
            bytes.writeTo(ostream);

            log.debug("Committed buffered bytes to the Servlet output stream");
        }

        // 输出chars
        if (writer != null) {

            flushBufferAdapter();
            PrintWriter writer = super.getWriter();
            String chars = stringWriter.toString();
            writer.write(chars);

            log.debug("Committed buffered characters to the Servlet writer");
        }
    }

    /** 冲洗buffer adapter，确保adapter中的信息被写入buffer中。 */
    private void flushBufferAdapter() {
        if (streamAdapter != null) {
            streamAdapter.flush();
        }
        if (writerAdapter != null) {
            try {
                writerAdapter.flush();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 代表一个将内容保存在内存中的<code>ServletOutputStream</code>。<br/>
     * XXX 这里没有对Servlet3做兼容处理
     */
    private static class BufferedServletOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream bytes;

        public BufferedServletOutputStream(ByteArrayOutputStream bytes) {
            this.bytes = bytes;
        }

        public void updateOutputStream(ByteArrayOutputStream bytes) {
            this.bytes = bytes;
        }

        @Override
        public void write(int b) throws IOException {
            bytes.write((byte) b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            bytes.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            bytes.flush();
        }

        @Override
        public void close() throws IOException {
            bytes.flush();
            bytes.close();
        }
    }

    /** 代表一个将内容保存在内存中的<code>PrintWriter</code>。 */
    private static class BufferedServletWriter extends PrintWriter {

        public BufferedServletWriter(StringWriter chars) {
            super(chars);
        }

        public void updateWriter(StringWriter chars) {
            this.out = chars;
        }
    }

    /**
     * 将<code>Writer</code>适配到<code>ServletOutputStream</code>。<br/>
     * XXX 这里没有对Servlet3做兼容处理
     */
    private static class WriterOutputStream extends ServletOutputStream {

        private ByteArrayOutputStream buffer  = new ByteArrayOutputStream();
        private Writer                writer;
        private String                charset = "ISO-8859-1";

        public WriterOutputStream(Writer writer, String charset) {
            this.writer = writer;
            if (StringUtil.isNotBlank(charset)) {
                this.charset = charset;
            }
        }

        @Override
        public void write(int b) throws IOException {
            buffer.write((byte) b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            buffer.write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            buffer.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            ByteArray bytes = buffer.toByteArray();

            if (bytes.getLength() > 0) {
                ByteArrayInputStream inputBytes = new ByteArrayInputStream(bytes.getRawBytes(), bytes.getOffset(),
                                                                           bytes.getLength());
                InputStreamReader reader = new InputStreamReader(inputBytes, charset);
                StreamUtil.io(reader, writer, true, false);
                writer.flush();
                buffer.reset();
            }
        }

        @Override
        public void close() throws IOException {
            this.flush();
        }
    }

    public void commit() throws IOException {
        if (isBuffering()) {
            try {
                commitBuffer();
            } catch (IOException e) {
                log.error("Commit buffered response failure!", e);
                throw e;
            }
        }
    }

}
