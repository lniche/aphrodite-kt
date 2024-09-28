package top.threshold.ktscaffold.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.ktscaffold.entity.ResultKt

@RestController

class HealthController {
    @RequestMapping(value = ["/health.do"])
    fun health(): ResultKt<Void> {
        return ResultKt.success()
    }
}