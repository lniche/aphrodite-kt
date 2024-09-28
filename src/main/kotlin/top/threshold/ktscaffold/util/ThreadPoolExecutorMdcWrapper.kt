package com.yingwu.rainbow.infrastructure.utils

import org.slf4j.MDC
import java.util.concurrent.*

class ThreadPoolExecutorMdcWrapper : ThreadPoolExecutor {
    constructor(
        corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit?,
        workQueue: BlockingQueue<Runnable?>?
    ) : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue) {
    }

    constructor(
        corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit?,
        workQueue: BlockingQueue<Runnable?>?, threadFactory: ThreadFactory?
    ) : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory) {
    }

    constructor(
        corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit?,
        workQueue: BlockingQueue<Runnable?>?, handler: RejectedExecutionHandler?
    ) : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler) {
    }

    constructor(
        corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit?,
        workQueue: BlockingQueue<Runnable?>?, threadFactory: ThreadFactory?,
        handler: RejectedExecutionHandler?
    ) : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler) {
    }

    override fun execute(task: Runnable) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()))
    }

    override fun <T> submit(task: Runnable, result: T): Future<T> {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result)
    }

    override fun <T> submit(task: Callable<T>): Future<T> {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()))
    }

    override fun submit(task: Runnable): Future<*> {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()))
    }
}