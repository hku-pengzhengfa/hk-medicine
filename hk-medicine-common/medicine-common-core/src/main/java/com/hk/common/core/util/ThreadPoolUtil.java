package com.hk.common.core.util;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pengzhengfa
 */
public class ThreadPoolUtil {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;

    private static final int KEEP_ALIVE_TIME = 60;

    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>(2000);

    private static ThreadPoolExecutor threadPool;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "hku-thread-" + integer.getAndIncrement());
        }
    };

    private ThreadPoolUtil() {
    }

    private static class ThreadPoolHolder {
        private static final ThreadPoolExecutor INSTANCE = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                WORK_QUEUE,
                THREAD_FACTORY,
                new ThreadPoolExecutor.CallerRunsPolicy() // 使用 CallerRunsPolicy 作为拒绝策略
        );
    }

    public static ThreadPoolExecutor getThreadPool() {
        return ThreadPoolHolder.INSTANCE;
    }

    public static void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }

    public static void cancel(Future<?> futureTask) {
        if (futureTask != null) {
            futureTask.cancel(true);
        }
    }

    public static void shutdown() {
        getThreadPool().shutdown();
    }

    public static List<Runnable> shutdownNow() {
        return getThreadPool().shutdownNow();
    }

    /**
     * 获取线程池状态信息
     *
     * @return
     */
    public static String threadPoolStats() {
        ThreadPoolExecutor pool = getThreadPool();
        return String.format("Active: %d, Completed: %d, Task Count: %d, Queue Size: %d",
                pool.getActiveCount(), pool.getCompletedTaskCount(), pool.getTaskCount(), pool.getQueue().size());
    }
}
