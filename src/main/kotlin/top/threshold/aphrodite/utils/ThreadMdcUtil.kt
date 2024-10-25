package top.threshold.aphrodite.utils

import cn.hutool.core.util.IdUtil
import org.slf4j.MDC
import top.threshold.aphrodite.constant.Const
import java.util.concurrent.Callable

object ThreadMdcUtil {
    private fun setTraceIdIfAbsent() {
        if (MDC.get(Const.TRACE_ID) == null) {
            MDC.put(Const.TRACE_ID, IdUtil.fastSimpleUUID())
        }
    }

    fun <T> wrap(callable: Callable<T>, context: Map<String?, String?>?): Callable<T> {
        return Callable {
            if (context == null) {
                MDC.clear()
            } else {
                MDC.setContextMap(context)
            }
            setTraceIdIfAbsent()
            try {
                return@Callable callable.call()
            } finally {
                MDC.clear()
            }
        }
    }

    fun wrap(runnable: Runnable, context: Map<String?, String?>?): Runnable {
        return Runnable {
            if (context == null) {
                MDC.clear()
            } else {
                MDC.setContextMap(context)
            }
            setTraceIdIfAbsent()
            try {
                runnable.run()
            } finally {
                MDC.clear()
            }
        }
    }
}