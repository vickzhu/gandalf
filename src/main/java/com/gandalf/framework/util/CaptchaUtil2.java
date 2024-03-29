/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 类CaptchaUtil.java的实现描述：验证码
 * 
 * @author gandalf 2015-2-9 下午11:05:33
 */
public class CaptchaUtil2 {

    public static final String VERIFY_CODES = "23456789abcdefghjkmnopqrstuvxyz";
    private static Random      random       = new Random();

    /**
     * 使用系统默认字符源生成验证码
     * 
     * @param verifySize 验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, VERIFY_CODES);
    }

    /**
     * 使用指定源生成验证码
     * 
     * @param verifySize 验证码长度
     * @param sources 验证码字符源
     * @return
     */
    private static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 生成随机验证码文件,并返回验证码值
     * 
     * @param w
     * @param h
     * @param outputFile
     * @param verifySize
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException {
        String verifyCode = generateVerifyCode(verifySize);
        outputImage(w, h, outputFile, verifyCode);
        return verifyCode;
    }

    /**
     * 生成指定验证码图像文件
     * 
     * @param w
     * @param h
     * @param outputFile
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
        if (outputFile == null) {
            return;
        }
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            outputImage(w, h, fos, code);
            fos.close();
        } catch (IOException e) {
            throw e;
        }
    }
    
    private static Color fontColor = Color.BLACK;

    /**
     * 输出指定验证码图片流
     * 
     * @param w
     * @param h
     * @param os
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int fontSize = h * 4 / 5;
        int fontSpace = fontSize / 3;
        BufferedImage image = new BufferedImage(w + 5, h + 5, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = image.createGraphics();
        // 消除锯齿边缘
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color c = getRandColor(150, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 2, w + 60, h + 50);
        g2.setColor(c);// 设置边框色
        g2.fillRect(0, 0, w + 60, h);

        int xOffSet = random.nextInt(w / 2);
        int yOffSet = random.nextInt(h - 30) + 30;
        
        drawline(g2, w, h, xOffSet, yOffSet);
        shear(g2, w, h, c);// 使图片扭曲

        // 添加噪点
        // addYawp(image, w, h);

        g2.setColor(fontColor);
        Font font = new Font("Times New Roman", Font.PLAIN, fontSize);
        g2.setFont(font);

        draw(g2, code, w, h, xOffSet, yOffSet, fontSpace);

        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }

    private static void draw(Graphics2D g2, String code, int w, int h, int xOffSet, int yOffSet, int fontSpace) {
        char[] chars = code.toCharArray();
        int verifySize = code.length();
        
        int direct = random.nextBoolean() ? 1 : -1;
        for (int i = 0; i < verifySize; i++) {
        	int endX = xOffSet + fontSpace * i;
        	AffineTransform affine = new AffineTransform();
        	double theta = Math.PI / 6 * random.nextDouble() * direct;
        	affine.setToRotation(theta, endX, yOffSet);
        	g2.setTransform(affine);
            g2.drawChars(chars, i, 1, endX, yOffSet);
        }
    }

    /**
     * 添加噪点
     * 
     * @param image
     * @param w
     * @param h
     */
    private static void addYawp(BufferedImage image, int w, int h) {
        float yawpRate = 0.05f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
    }
    
    /**
     * 绘制干扰线
     * 
     * @param g2
     * @param w
     * @param h
     * @param fontColor
     */
    private static void drawline(Graphics2D g2, int w, int h, int xOffSet, int yOffSet) {
        g2.setStroke(new BasicStroke(h / 20));
        g2.setColor(fontColor);// 设置线条的颜色
        for (int i = 0; i < 1; i++) {
            int gx = xOffSet - 5;
            if(gx < 0) {
            	gx = 0;
            }
            int gy = yOffSet - 5;
            int gxl = xOffSet + 12 * 4 + 5;
            if(gxl > w) {
            	gxl = w;
            }
            int gyl = yOffSet + random.nextInt(5) * (random.nextBoolean() ? 1 : -1);
            g2.drawLine(gx, gy, gxl, gyl);
        }
    }

    /**
     * 获取随机颜色
     * 
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    // 旋转
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                       * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase)
                                  / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(40) + 10; // 50;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                       * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase)
                                  / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }

        }

    }

}
