package top.threshold.aphrodite

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class AphroditeApplication

fun main(args: Array<String>) {
    System.setProperty("user.timezone", "UTC")
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<AphroditeApplication>(*args)
}
