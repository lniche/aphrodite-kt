package top.threshold.ktscaffold.util

import cn.hutool.core.util.StrUtil
import cn.hutool.crypto.digest.HMac
import cn.hutool.crypto.digest.HmacAlgorithm
import java.nio.charset.StandardCharsets

object HmacUtil {
    /**
     * 检查请求头中的 client info 和 sign 是否匹配
     * os=1,at=0,cv=198,n=xxx
     * os：操作系统 0-安卓 1-ios
     * at：o-ou电音
     * cv：版本号
     * 使用 uid + nonce 对client info进行 HmacMD5 生成签名做比对
     * @param info
     * @param sign
     * @param uid
     * @return
     */
    fun checkReqHmac(info: String, sign: String, uid: String): Boolean {
        if (StrUtil.isEmpty(info) || StrUtil.isEmpty(sign)
            || StrUtil.isEmpty(uid)
        ) {
            return false
        }
        val infos = ciMap(info)
        // 未正确包含 nonce
        if (!infos.containsKey("n")) {
            return false
        }
        val nonce = infos["n"]
        val key = uid + nonce
        val mac = HMac(HmacAlgorithm.HmacMD5, key.toByteArray(StandardCharsets.UTF_8))
        return mac.digestHex(info) == sign
    }

    fun ciMap(info: String): Map<String, String> {
        val infos: MutableMap<String, String> = HashMap()
        val cis = info.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in cis) {
            if (i.contains("=")) {
                infos[i.substring(0, i.indexOf("="))] = i.substring(i.indexOf("=") + 1)
            }
        }
        return infos
    }
}