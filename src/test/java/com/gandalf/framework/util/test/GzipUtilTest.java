/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.util.test;

import java.nio.charset.Charset;

import com.gandalf.framework.constant.CharsetConstant;
import com.gandalf.framework.util.GzipUtil;

/**
 * 类GzipTest.java的实现描述：测试Gzip
 * 
 * @author gandalf 2014-7-9 下午05:58:07
 */
public class GzipUtilTest {

    public static void main(String[] args) {
        String say = "这是一段完全没有重复文字的语句，不信的话你可以仔细进行阅读，加入我说谎了，那么没什么，干嘛这样子，只不过开个玩笑";
        byte[] b = say.getBytes(Charset.forName(CharsetConstant.UTF_8));
        System.out.println(b.length);
        byte[] bz = GzipUtil.gzip(b);
        System.out.println(bz.length);
        byte[] uz = GzipUtil.unGzip(bz);
        System.out.println(new String(uz, Charset.forName(CharsetConstant.UTF_8)));
    }
}
