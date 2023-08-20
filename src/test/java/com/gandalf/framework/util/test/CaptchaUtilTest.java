/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.util.test;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import com.gandalf.framework.util.CaptchaUtil2;

/**
 * 类CaptchaUtilTest.java的实现描述：验证码测试类
 * 
 * @author gandalf 2015-2-11 上午01:28:17
 */
public class CaptchaUtilTest {

    public static void main(String[] args) throws IOException {
        File dir = new File("D:/verifies");
        int w = 100, h = 40;
        for (int i = 0; i < 50; i++) {
            File file = new File(dir, i + ".jpg");
            String code = CaptchaUtil2.outputVerifyImage(w, h, file, 4);
            System.out.println(code);
        }
//        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//        for (Font font : fonts) {
//			System.out.println(font.getName());
//		}
        System.out.println("完成");

    }
}
