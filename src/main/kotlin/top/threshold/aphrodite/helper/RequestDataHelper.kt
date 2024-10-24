package top.threshold.aphrodite.helper

import com.alibaba.ttl.TransmittableThreadLocal
import java.util.*

/**
 * 请求参数传递辅助类
 */
object RequestDataHelper {
    /**
     * 使用阿里的ttl，防止数据在跨线程时丢失
     */
    private val REQUEST_DATA = TransmittableThreadLocal<MutableMap<String, String>>()

    /**
     * 设置请求参数
     *
     * @param requestData 请求参数 MAP 对象
     */
    fun setRequestData(requestData: MutableMap<String, String>) {
        REQUEST_DATA.set(requestData)
    }

    /**
     * 获取请求参数
     *
     * @param param 请求参数
     * @return 请求参数 MAP 对象
     */
    fun getParam(param: String): String? {
        val dataMap = requestData
        return dataMap?.get(param)
    }

    /**
     * 获取请求参数
     *
     * @return 请求参数 MAP 对象
     */
    val requestData: MutableMap<String, String>?
        get() = REQUEST_DATA.get()

    fun clear() {
        if (Objects.nonNull(REQUEST_DATA.get())) {
            REQUEST_DATA.remove()
        }
    }
}