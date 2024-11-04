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
import top.threshold.aphrodite.entity.Result
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
 *  Authentication Controller
 * </p>
 *
 * @author qingshan
 * @since 2024-09-29
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@Tag(name = "Authentication Module")
class AuthController(
    val redisUtil: RedisUtil,
    val userRepository: IUserRepository,
) : BaseController() {

    private val dailyLimit = 30
    private val codeValidityInSeconds = 60L

    @Data
    class SendVerifyCodeReq {
        /**
         * Phone number
         */
        @field:NotBlank(message = "Phone number cannot be empty")
        @field:Schema(description = "User phone number", example = "13800138000", required = true)
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}\$", message = "Phone number format is incorrect")
        var phone: String? = null
    }

    @Operation(
        summary = "Send Verification Code",
        description = "Send verification code",
    )
    @PostMapping("/send-code")
    fun sendVerifyCode(@Validated @RequestBody sendVerifyCodeReq: SendVerifyCodeReq): Result<Void> {
        val phone = sendVerifyCodeReq.phone
        val today = LocalDate.now()
        val dailyKey = CacheKey.SMS_CODE_NUM + "$phone:$today"
        val count = redisUtil.getInt(dailyKey)
        if (count >= dailyLimit) {
            return Result.err("The daily limit for SMS has been reached")
        }
        val codeKey = CacheKey.SMS_CODE + phone
        if (redisUtil.hasKey(codeKey)) {
            return Result.err("A verification code has already been sent within a minute, please try again later")
        }
        val code = RandomUtil.randomInt(1000, 9999).toString()
        log.debug("$phone send verify code: $code")
        redisUtil.setStr(codeKey, code, codeValidityInSeconds)
        redisUtil.incr(dailyKey, 1)
        redisUtil.expire(dailyKey, 3600 * 24L)
//        sendSms(phone, code)
        return Result.ok()
    }

    @Data
    class LoginReq {
        /**
         * Phone number
         */
        @field:NotBlank(message = "Phone number cannot be empty")
        @field:Schema(description = "User phone number", example = "13800138000", required = true)
        var phone: String? = null

        /**
         * Verification code
         */
        @field:NotBlank(message = "Verification code cannot be empty")
        @field:Schema(description = "Verification code for user registration", example = "1234", required = true)
        var code: String? = null
    }

    @Data
    class LoginResp {
        /**
         * Access token
         */
        @field:Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
        var accessToken: String? = null
    }

    @Operation(
        summary = "User Registration/Login",
        description = "User registration and login",
    )
    @PostMapping("/login")
    fun login(@Validated @RequestBody loginReq: LoginReq): Result<LoginResp> {
        val today = LocalDate.now()
        val dailyKey = CacheKey.SMS_CODE_NUM + "${loginReq.phone}:$today"
        if (!redisUtil.hasKey(dailyKey)) return Result.err("Verification code has expired, please retrieve it again")
        if (loginReq.code.equals(redisUtil.getStr(dailyKey))) return Result.err("Verification code is incorrect, please re-enter")

        var userDO = userRepository.getByPhone(loginReq.phone!!)
        if (Objects.isNull(userDO)) {
            userDO = UserDO()
            userDO.userNo = redisUtil.nextId(CacheKey.NEXTID_UNO)
            userDO.userCode = IdUtil.getSnowflakeNextIdStr()
            userDO.clientIp = realIpAddress
            userDO.nickname = "A" + loginReq.phone!!.takeLast(4)
            userDO.phone = loginReq.phone
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
        val loginResp = LoginResp()
        loginResp.accessToken = userDO.loginToken
        return Result.ok(loginResp)
    }

    @Operation(
        summary = "User Logout",
        description = "User logout",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PostMapping("/logout")
    fun logout(): Result<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return Result.err("User is not valid")
        userDO.loginToken = ""
        userRepository.updateById(userDO)
        StpUtil.logout()
        return Result.ok()
    }
}

