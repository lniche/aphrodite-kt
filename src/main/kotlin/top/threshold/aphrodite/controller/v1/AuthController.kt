package top.threshold.aphrodite.controller.v1;

import cn.hutool.core.util.RandomUtil
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.constraints.NotBlank
import lombok.Data
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.entity.ResultKt
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
    val redisUtil: RedisUtil
) {
    @Data
    class SmsSendQeq {
        /**
         * 手机号
         */
        @NotBlank(message = "手机号不能为空")
        var phone: String? = null
    }

    @Operation(summary = "发送验证码")
    @PostMapping("/send-code")
    fun sendCode(@Validated @RequestBody smsSendQeq: SmsSendQeq): ResultKt<Int> {
        return ResultKt.success(RandomUtil.randomInt(1000, 9999))
    }
}
