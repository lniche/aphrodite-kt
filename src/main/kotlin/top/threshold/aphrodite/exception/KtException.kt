import top.threshold.aphrodite.enums.KtCode
import java.io.Serializable

data class KtException(
    var code: Int,
    var msg: String,
    var data: Any? = null
) : RuntimeException(msg), Serializable {

    constructor(code: Int, message: String) : this(code, message, null)

    constructor(ktCode: KtCode) : this(ktCode.code, ktCode.message, null)
}
