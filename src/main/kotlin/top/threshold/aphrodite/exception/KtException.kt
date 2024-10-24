import top.threshold.aphrodite.enums.KtCode
import java.io.Serializable

data class KtException(
    var code: String? = null,
    var msg: String? = null,
    var data: Any? = null
) : RuntimeException(msg), Serializable {

    constructor(code: String?, message: String?) : this(code, message, null)

    constructor(ktCode: KtCode) : this(ktCode.code, ktCode.msg, null)
}
