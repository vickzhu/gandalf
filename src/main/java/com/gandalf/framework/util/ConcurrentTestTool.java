package com.gandalf.framework.util;

import java.util.concurrent.CountDownLatch;

/**
 * 并发测试工具
 * 
 * @author vick.zhu
 *
 */
public class ConcurrentTestTool {

	public static void parallelTask(int threadNum, Runnable task) {
		// 1. 定义闭锁来拦截线程
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(threadNum);
		// 2. 创建指定数量的线程
		for (int i = 0; i < threadNum; i++) {
			Thread t = new Thread(() -> {
				try {
					startGate.await();
					try {
						task.run();
					} finally {
						endGate.countDown();
					}
				} catch (InterruptedException e) {
				}
			});
			t.start();
		}
		// 3. 线程统一放行，并记录时间！
		long start = System.nanoTime();
		startGate.countDown();
		try {
			endGate.await();
		} catch (InterruptedException e) {
		}
		long end = System.nanoTime();
		System.out.println("cost times :" + (end - start));
	}

	public static void main(String[] args) {
		parallelTask(100, new Runnable() {
			@Override
			public void run() {
				System.out.println(System.currentTimeMillis());
			}
		});
	}
}
