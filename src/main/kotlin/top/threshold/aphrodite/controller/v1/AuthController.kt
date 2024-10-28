package top.threshold.aphrodite.controller.v1;

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.RandomUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import lombok.Data
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.threshold.aphrodite.constant.CacheKey
import top.threshold.aphrodite.controller.BaseController
import top.threshold.aphrodite.entity.ResultKt
import top.threshold.aphrodite.entity.Slf4j
import top.threshold.aphrodite.entity.Slf4j.Companion.log
import top.threshold.aphrodite.entity.pojo.UserDO
import top.threshold.aphrodite.repository.IUserRepository
import top.threshold.aphrodite.utils.RedisUtil
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*


/**
 * <p>
 *  认证控制器
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Tag(name = "认证模块")
class AuthController(
    val redisUtil: RedisUtil,
    val userRepository: IUserRepository,
) : BaseController() {

    private val dailyLimit = 30
    private val codeValidityInSeconds = 60L

    @Data
    class SendVerifyCodeReq {
        /**
         * 手机号
         */
        @field:NotBlank(message = "手机号不能为空")
        @field:Schema(description = "用户手机号", example = "13800138000", required = true)
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}\$", message = "手机号格式不正确")
        var phone: String? = null
    }
    @Operation(
        summary = "发送验证码",
        description = "发送验证码",
    )
    @PostMapping("/send-code")
    fun sendVerifyCode(@Validated @RequestBody sendVerifyCodeReq: SendVerifyCodeReq): ResultKt<Void> {
        val phone = sendVerifyCodeReq.phone
        // 检查当天发送次数
        val today = LocalDate.now()
        val dailyKey = CacheKey.SMS_CODE_NUM + "$phone:$today"
        val count = redisUtil.getInt(dailyKey)

        if (count >= dailyLimit) {
            return ResultKt.fail("当天短信发送次数已达上限")
        }

        // 检查是否已发送验证码
        val codeKey = CacheKey.SMS_CODE + phone
        if (redisUtil.hasKey(codeKey)) {
            return ResultKt.fail("一分钟内已发送验证码，请稍后再试")
        }

        // 生成随机验证码
        val code = RandomUtil.randomInt(1000, 9999).toString()
        log.debug("$phone send verify code:$code")
        // 存储验证码到 Redis
        redisUtil.setStr(codeKey, code, codeValidityInSeconds)

        // 更新发送次数
        redisUtil.incr(dailyKey, 1)
        redisUtil.expire(dailyKey, 3600 * 24L) // 设置过期时间为1天

        // 发送验证码的逻辑（调用短信服务等）
//        sendSms(phone, code)
        return ResultKt.success()
    }

    @Data
    class LoginReq {
        /**
         * 手机号
         */
        @field:NotBlank(message = "手机号不能为空")
        @field:Schema(description = "用户手机号", example = "13800138000", required = true)
        var phone: String? = null

        /**
         * 验证码
         */
        @field:NotBlank(message = "验证码不能为空")
        @field:Schema(description = "用户注册时的验证码", example = "1234", required = true)
        var code: String? = null
    }

    @Operation(
        summary = "用户注册登录",
        description = "用户注册登录",
    )
    @PostMapping("/login")
    fun login(@Validated @RequestBody loginReq: LoginReq): ResultKt<String> {
        val today = LocalDate.now()
        val dailyKey = CacheKey.SMS_CODE_NUM + "${loginReq.phone}:$today"
        if (!redisUtil.hasKey(dailyKey)) return ResultKt.fail("验证码失效，请重新获取")
        if (loginReq.code.equals(redisUtil.getStr(dailyKey))) return ResultKt.fail("验证码错误，请重新输入")

        var userDO = userRepository.getByPhone(loginReq.phone!!)
        if (Objects.isNull(userDO)) {
            userDO = UserDO()
            userDO.userNo = redisUtil.nextId(CacheKey.NEXTID_UNO)
            userDO.userCode = IdUtil.getSnowflakeNextIdStr()
            userDO.clientIp = realIpAddress
            userDO.loginAt = OffsetDateTime.now()
            userDO.loginToken = login(userDO.userCode)
            userRepository.save(userDO)
        } else {
            userDO!!.clientIp = realIpAddress
            userDO.loginAt = OffsetDateTime.now()
            userDO.loginToken = login(userDO.userCode)
            userRepository.updateById(userDO)
        }

        redisUtil.del(dailyKey)
        return ResultKt.success(userDO.loginToken!!)
    }

    @Operation(
        summary = "用户注销",
        description = "用户注销",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PostMapping("/logout")
    fun logout(): ResultKt<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return ResultKt.fail("用户不合法")
        userDO.loginToken = ""
        userRepository.updateById(userDO)
        StpUtil.logout()
        return ResultKt.success()
    }
}
