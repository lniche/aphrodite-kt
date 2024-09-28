package top.threshold.ktscaffold.exception

/**
 * @author Edward Guo
 * @date 2019/10/4 11:17 上午
 */
open class BaseException : RuntimeException {
    /**
     * 返回码
     */
    val responseEnum: IResponseEnum

    /**
     * 异常消息参数
     */
    @Transient
    val args: Array<out Any?>?

    constructor(responseEnum: IResponseEnum) : super(responseEnum.msg) {
        this.responseEnum = responseEnum
        args = null
    }

    constructor(responseEnum: IResponseEnum, message: String?) : super(message) {
        this.responseEnum = responseEnum
        args = null
    }

    constructor(responseEnum: IResponseEnum, args: Array<out Any?>) : super(responseEnum.msg) {
        this.responseEnum = responseEnum
        this.args = args
    }

    constructor(responseEnum: IResponseEnum, args: Array<out Any?>, message: String?) : super(message) {
        this.responseEnum = responseEnum
        this.args = args
    }

    constructor(responseEnum: IResponseEnum, args: Array<out Any?>, message: String?, cause: Throwable?) : super(
        message,
        cause
    ) {
        this.responseEnum = responseEnum
        this.args = args
    }

    companion object {
        private const val serialVersionUID = 6364577619817612762L
    }
}