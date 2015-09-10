/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.util.test;

import java.io.File;
import java.io.IOException;

import com.gandalf.framework.util.CaptchaUtil;

/**
 * 类CaptchaUtilTest.java的实现描述：验证码测试类
 * 
 * @author gandalf 2015-2-11 上午01:28:17
 */
public class CaptchaUtilTest {

    public static void main(String[] args) throws IOException {
        File dir = new File("E:/verifies");
        int w = 110, h = 50;
        for (int i = 0; i < 100; i++) {
            File file = new File(dir, i + ".jpg");
            String code = CaptchaUtil.outputVerifyImage(w, h, file, 4);
            System.out.println(code);
        }
        System.out.println("完成");

    }
}
