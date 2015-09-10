package com.gandalf.framework.util;

import java.util.Arrays;
import java.util.Random;

/**
 * 类WeightedRandomNumber.java的实现描述：按权重生成随机数
 * 
 * <pre>
 * 输入权重数组，按权重输出数组下标
 * </pre>
 * 
 * @author gandalf 2014-2-21 下午2:04:44
 */
public class WeightedRandomNumber {

    private double[] regions;

    /**
     * 随机数组
     * 
     * @param weights
     */
    public WeightedRandomNumber(double[] weights) {
        regions = new double[weights.length];
        init(weights);
    }

    private void init(double[] weights) {
        double total = 0;
        for (int i = 0; i < weights.length; i++) {
            total += weights[i];
            regions[i] = total;
        }
    }

    /**
     * 输出一个下标
     * 
     * @return
     */
    public int next() {
        Random rnd = new Random(System.nanoTime());
        double rndNum = rnd.nextDouble() * regions[regions.length - 1];
        int sNum = Arrays.binarySearch(regions, rndNum);
        int idx = (sNum < 0) ? (Math.abs(sNum) - 1) : sNum;
        return idx;
    }

}
