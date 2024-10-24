package top.threshold.aphrodite.exception

import KtException


interface KtAssert : IResponseEnum, Assert {
    override fun newException(vararg args: Any?): KtException {
        return KtException(this.code, this.msg)
    }

    override fun newException(t: Throwable?, vararg args: Any?): KtException {
        return KtException(this.code, this.msg)
    }
}