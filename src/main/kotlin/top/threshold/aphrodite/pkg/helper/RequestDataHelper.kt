package top.threshold.aphrodite.pkg.helper

import com.alibaba.ttl.TransmittableThreadLocal
import java.util.*


object RequestDataHelper {

    private val REQUEST_DATA = TransmittableThreadLocal<Map<String, String>>()

    fun setRequestData(requestData: Map<String, String>) {
        REQUEST_DATA.set(requestData)
    }

    fun getParam(param: String): String? {
        val dataMap = requestData
        return dataMap?.get(param)
    }

    val requestData: Map<String, String>?
        get() = REQUEST_DATA.get()

    fun clear() {
        if (Objects.nonNull(REQUEST_DATA.get())) {
            REQUEST_DATA.remove()
        }
    }
}
