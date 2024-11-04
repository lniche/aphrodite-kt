package top.threshold.aphrodite.exception

open class BaseException : RuntimeException {

    val responseEnum: IResponseEnum

    @Transient
    val args: Array<out Any?>?

    constructor(responseEnum: IResponseEnum) : super(responseEnum.message) {
        this.responseEnum = responseEnum
        args = null
    }

    constructor(responseEnum: IResponseEnum, message: String?) : super(message) {
        this.responseEnum = responseEnum
        args = null
    }

    constructor(responseEnum: IResponseEnum, args: Array<out Any?>) : super(responseEnum.message) {
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