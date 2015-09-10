package com.gandalf.framework.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类ThreadPoolExecutorFactory.java的实现描述：线程池工厂
 * 
 * @author gandalf 2014-2-21 下午2:20:59
 */
public class ThreadPoolExecutorFactory {

    private ThreadPoolExecutorFactory() {
    }

    private static class ExcutorHolder {

        private static int        corePoolSize    = 50;
        private static int        maximumPoolSize = 200;
        private static TimeUnit   timeUnit        = TimeUnit.SECONDS;
        private static long       keepAliveTime   = 300;
        static ThreadPoolExecutor threadPool      = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                                                                           keepAliveTime, timeUnit,
                                                                           new ArrayBlockingQueue<Runnable>(300));
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return ExcutorHolder.threadPool;
    }

}
