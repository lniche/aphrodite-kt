package top.threshold.aphrodite.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.pkg.entity.Result

@RestController
class HealthController {

    @RequestMapping("/")
    fun root(): Result<String> {
        return Result.ok("Thank you for using Aphrodite!")
    }

    @RequestMapping("/ping")
    fun ping(): Result<String> {
        return Result.ok("pong")
    }
}