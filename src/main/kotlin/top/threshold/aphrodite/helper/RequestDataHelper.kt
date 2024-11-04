package top.threshold.aphrodite.helper

import com.alibaba.ttl.TransmittableThreadLocal
import java.util.*


object RequestDataHelper {

    private val REQUEST_DATA = TransmittableThreadLocal<MutableMap<String, String>>()

    fun setRequestData(requestData: MutableMap<String, String>) {
        REQUEST_DATA.set(requestData)
    }

    fun getParam(param: String): String? {
        val dataMap = requestData
        return dataMap?.get(param)
    }

    val requestData: MutableMap<String, String>?
        get() = REQUEST_DATA.get()

    fun clear() {
        if (Objects.nonNull(REQUEST_DATA.get())) {
            REQUEST_DATA.remove()
        }
    }
}