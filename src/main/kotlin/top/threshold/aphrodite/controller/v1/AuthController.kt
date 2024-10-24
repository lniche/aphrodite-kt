package top.threshold.aphrodite.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.repository.IUserRepository
import top.threshold.aphrodite.util.RedisUtil


/**
 * <p>
 *  认证控制器
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@RestController
@RequestMapping("/auth")
class AuthController(
    val userService: IUserRepository,
    val redisUtil: RedisUtil
) {

}
