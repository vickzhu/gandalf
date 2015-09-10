package com.gandalf.framework.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gandalf.framework.constant.CharsetConstant;

/**
 * 类SocketClient.java的实现描述：socket客户端
 * 
 * @author gandalf 2014-4-28 下午5:26:23
 */
public class SocketClient {

    private static Logger logger = LoggerFactory.getLogger(SocketClient.class);

    /**
     * 发起socket连接，默认采用utf-8编码
     * 
     * @param host
     * @param port
     * @param content
     * @return
     */
    public static String send(String host, int port, String content) {
        return send(host, port, content, Charset.forName(CharsetConstant.UTF_8));
    }

    /**
     * 发起socket连接
     * 
     * @param host
     * @param port
     * @param content
     * @param charset
     * @return
     */
    public static String send(String host, int port, String content, Charset charset) {
        Socket s = null;
        OutputStream out = null;
        InputStream in = null;
        ByteArrayOutputStream byteout = null;
        try {
            s = new Socket(host, port);
            s.setSendBufferSize(4096);
            s.setTcpNoDelay(true);
            s.setSoTimeout(40000);
            s.setKeepAlive(true);
            out = s.getOutputStream();
            out.write(content.getBytes(charset));
            out.flush();

            in = s.getInputStream();
            byteout = new ByteArrayOutputStream(4096);
            byte[] buff = new byte[2048];
            while (true) {
                int ilen = in.read(buff);
                if (ilen == -1) {
                    break;
                }
                if (ilen > 0) {
                    byteout.write(buff, 0, ilen);
                }
            }
            return new String(byteout.toByteArray(), charset);
        } catch (Exception e) {
            logger.error("Send socket request failed!", e);
        } finally {
            if (byteout != null) {
                try {
                    byteout.close();
                } catch (IOException e) {
                    logger.error("Close ByteArrayOutputStream failed!", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("Close OutputStream failed!", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("Close InputStream failed!", e);
                }
            }
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    logger.error("Close socket failed!", e);
                }
            }
        }
        return null;
    }
}
