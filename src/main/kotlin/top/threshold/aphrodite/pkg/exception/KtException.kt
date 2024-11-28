import top.threshold.aphrodite.pkg.enums.Errors
import java.io.Serializable

data class KtException(
    var code: Int,
    var msg: String,
    var data: Any? = null
) : RuntimeException(msg), Serializable {

    constructor(code: Int, message: String) : this(code, message, null)

    constructor(errors: Errors) : this(errors.code, errors.message, null)
}
