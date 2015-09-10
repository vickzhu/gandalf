/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类GzipUtil.java的实现描述：Gzip压缩解压
 * 
 * @author gandalf 2014-7-2 下午02:30:01
 */
public class GzipUtil {

    private static final Logger logger = LoggerFactory.getLogger(GzipUtil.class);

    /**
     * gzip压缩字符串
     * 
     * @param str
     * @return
     */
    public static byte[] gzip(byte[] in) {
        byte[] out = null;
        ByteArrayOutputStream bo = null;
        GZIPOutputStream gzipo = null;
        try {
            bo = new ByteArrayOutputStream();
            gzipo = new GZIPOutputStream(bo);
            gzipo.write(in);
            gzipo.finish();
            out = bo.toByteArray();
        } catch (Exception e) {
            logger.error("Compress string with Gzip failure!", e);
        } finally {
            if (gzipo != null) {
                try {
                    gzipo.close();
                } catch (Exception e) {
                    logger.warn("Compress string with Gzip failure!", e);
                }
            }
            if (bo != null) {
                try {
                    bo.close();
                } catch (Exception e) {
                    logger.warn("Compress string with Gzip failure!", e);
                }
            }
        }
        return out;
    }

    public static byte[] unGzip(byte[] in) {
        GZIPInputStream gzi = null;
        ByteArrayOutputStream bos = null;
        byte[] out = null;
        try {
            gzi = new GZIPInputStream(new ByteArrayInputStream(in));
            bos = new ByteArrayOutputStream(in.length);
            int count = 0;
            byte[] tmp = new byte[2048];
            while ((count = gzi.read(tmp)) != -1) {
                bos.write(tmp, 0, count);
            }
            out = bos.toByteArray();
        } catch (IOException e) {
            logger.error("Decompress string with Gzip failure!", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.warn("Decompress string with Gzip failure!", e);
                }
            }
            if (gzi != null) {
                try {
                    gzi.close();
                } catch (IOException e) {
                    logger.warn("Decompress string with Gzip failure!", e);
                }
            }
        }
        return out;
    }

}
