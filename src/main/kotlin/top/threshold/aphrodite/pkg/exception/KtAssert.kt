package top.threshold.aphrodite.pkg.exception

import KtException


interface KtAssert : IResponseEnum, Assert {
    override fun newException(vararg args: Any?): KtException {
        return KtException(this.code, this.message)
    }

    override fun newException(t: Throwable?, vararg args: Any?): KtException {
        return KtException(this.code, this.message)
    }
}