package com.gandalf.framework.util;

/**
 * ID生成器
 * 
 * <pre>
 * 第 1 位： 符号位，暂时不用。
 * 第 2~42 位：共41位，时间戳，单位是毫秒，可以支撑大约69年
 * 第 43~52 位：共10位，机器ID，最多可容纳1024台机器
 * 第 53~64 位：共12位，序列号，是自增值，表示同一毫秒内产生的ID，单台机器每毫秒最多可生成4096个订单ID
 * </pre>
 * 
 * @author Gandalf
 *
 */
public class IDGenerator {
	/**
     * 起始时间戳，从2021-12-01开始生成
     */
    private final static long START_STAMP = 1638288000000L;

    /**
     * 序列号占用的位数 12
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 10;

    /**
     * 机器数量最大值
     */
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * 序列号最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * 序列号
     */
    private static long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private static long lastStamp = -1L;

    /**
     * 产生下一个ID
     */
    public static synchronized long nextId(long machineId) {
    	if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new RuntimeException("机器超过最大数量");
        }
        long currStamp = getNewStamp();
        if (currStamp < lastStamp) {
            throw new RuntimeException("时钟后移，拒绝生成ID！");
        }

        if (currStamp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStamp = currStamp;
        return (currStamp - START_STAMP) << TIMESTAMP_LEFT // 时间戳部分
                | machineId << MACHINE_LEFT             // 机器标识部分
                | sequence;                             // 序列号部分
    }

    private static long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    private static long getNewStamp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
        	System.out.println(IDGenerator.nextId(1));
		}
    }
}
