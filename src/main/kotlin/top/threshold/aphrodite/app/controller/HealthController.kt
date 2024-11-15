package top.threshold.aphrodite.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.pkg.entity.KtResult

@RestController
class HealthController {

    @RequestMapping("/")
    fun root(): KtResult<String> {
        return KtResult.ok("Thank you for using Aphrodite!")
    }

    @RequestMapping("/ping")
    fun ping(): KtResult<String> {
        return KtResult.ok("pong")
    }
}
