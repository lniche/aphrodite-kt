package top.threshold.aphrodite.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.entity.ResultKt

@RestController
class HealthController {

    @RequestMapping("/")
    fun home(): ResultKt<String> {
        return ResultKt.success("Thank you for using Aphrodite!")
    }

    @RequestMapping("/ping")
    fun ping(): ResultKt<String> {
        return ResultKt.success("pong")
    }
}