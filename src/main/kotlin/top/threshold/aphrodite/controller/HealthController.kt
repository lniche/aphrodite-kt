package top.threshold.aphrodite.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.entity.ResultKt

@RestController
class HealthController {
    @RequestMapping(value = ["/"])
    fun home(): ResultKt<String> {
        return ResultKt.success("Thank you for using Aphrodite!")
    }

    @RequestMapping(value = ["/ping"])
    fun ping(): ResultKt<String> {
        return ResultKt.success("pong")
    }
}