/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.util.test;

import com.gandalf.framework.util.WeightedRandomNumber;

/**
 * 类WeightedRandomNumberTest.java的实现描述：按权重生成随机数
 * 
 * @author gandalf 2014-7-10 上午11:54:23
 */
public class WeightedRandomNumberTest {

    public static void main(String[] args) {
        double[] weights = new double[] { 0.5, 0.1, 0.3, 0.6, 0.2 };
        WeightedRandomNumber wrng = new WeightedRandomNumber(weights);
        for (int i = 0; i < 100; i++) {
            System.out.println(wrng.next());
        }
    }
}
