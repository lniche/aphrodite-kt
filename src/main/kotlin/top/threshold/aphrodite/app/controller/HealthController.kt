package top.threshold.aphrodite.app.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.pkg.model.R

@RestController
class HealthController {

    @RequestMapping("/")
    fun root(): R<String> {
        return R.ok("Thank you for using Aphrodite!")
    }

    @RequestMapping("/ping")
    fun ping(): R<String> {
        return R.ok("pong")
    }
}
