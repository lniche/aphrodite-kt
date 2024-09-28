package top.threshold.ktscaffold.exception

import RainbowException


interface RainbowAssert : IResponseEnum, Assert {
    override fun newException(vararg args: Any?): RainbowException {
        return RainbowException(this.code, this.msg)
    }

    override fun newException(t: Throwable?, vararg args: Any?): RainbowException {
        return RainbowException(this.code, this.msg)
    }
}