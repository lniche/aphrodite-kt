import top.threshold.ktscaffold.enums.RCode
import java.io.Serializable

data class RainbowException(
    var code: String? = null,
    var msg: String? = null,
    var data: Any? = null
) : RuntimeException(msg), Serializable {

    constructor(code: String?, message: String?) : this(code, message, null)

    constructor(rCode: RCode) : this(rCode.code, rCode.msg, null)
}
