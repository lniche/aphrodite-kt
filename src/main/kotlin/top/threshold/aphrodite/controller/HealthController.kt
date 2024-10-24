package top.threshold.aphrodite.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.entity.ResultKt

@RestController

class HealthController {
    @RequestMapping(value = ["/health.do"])
    fun health(): ResultKt<Void> {
        return ResultKt.success()
    }
}