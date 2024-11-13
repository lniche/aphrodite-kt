package top.threshold.aphrodite.app.controller.v1;

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
import top.threshold.aphrodite.app.controller.BaseController
import top.threshold.aphrodite.app.entity.pojo.UserDO
import top.threshold.aphrodite.app.repository.IUserRepository
import top.threshold.aphrodite.pkg.constant.CacheKey
import top.threshold.aphrodite.pkg.entity.Result
import top.threshold.aphrodite.pkg.entity.Slf4j
import top.threshold.aphrodite.pkg.util.RedisUtil
import java.time.OffsetDateTime
import java.util.*

@Slf4j
@RestController
@RequestMapping("/v1")
@Tag(name = "Auth Module")
class AuthController(
    val redisUtil: RedisUtil,
    val userRepository: IUserRepository,
) : BaseController() {

    @Data
    class SendVerifyCodeRequest {
        /**
         * Phone number
         */
        @field:NotBlank(message = "Phone number cannot be empty")
        @field:Schema(description = "User Phone", example = "13800138000", required = true)
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}\$", message = "Phone number format is incorrect")
        var phone: String? = null
    }

    @Operation(
        summary = "Send Verification Code",
    )
    @PostMapping("/send-code")
    fun sendVerifyCode(@Validated @RequestBody sendVerifyCodeRequest: SendVerifyCodeRequest): Result<Void> {
        val cacheKey = CacheKey.SMS_CODE + sendVerifyCodeRequest.phone
        if (redisUtil.hasKey(cacheKey)) {
            return Result.err("A verification code has already been sent within a minute, please try again later")
        }
        val cacheCode = RandomUtil.randomInt(1000, 9999).toString()
        redisUtil.setStr(cacheKey, cacheCode, 60)
        // TODO fake send
        return Result.ok()
    }

    @Data
    class LoginRequest {
        /**
         * Phone number
         */
        @field:NotBlank(message = "Phone number cannot be empty")
        @field:Schema(description = "User Phone", example = "13800138000", required = true)
        var phone: String? = null

        /**
         * Verification code
         */
        @field:NotBlank(message = "Verification code cannot be empty")
        @field:Schema(description = "Verification code", example = "1234", required = true)
        var code: String? = null
    }

    @Data
    class LoginResponse {
        /**
         * Access token
         */
        @field:Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        var accessToken: String? = null
    }

    @Operation(
        summary = "User Registration/Login",
    )
    @PostMapping("/login")
    fun login(@Validated @RequestBody loginRequest: LoginRequest): Result<LoginResponse> {
        val codeKey = CacheKey.SMS_CODE + loginRequest.phone
        val cacheCode = redisUtil.getStr(codeKey)
        if (!loginRequest.code.equals(cacheCode))
            return Result.err("Verification code is incorrect, please re-enter")
        var userDO = userRepository.getByPhone(loginRequest.phone!!)
        if (Objects.isNull(userDO)) {
            userDO = UserDO()
            userDO.userNo = redisUtil.nextId(CacheKey.NEXT_UNO)
            userDO.userCode = IdUtil.getSnowflakeNextIdStr()
            userDO.clientIp = realIpAddress
            userDO.nickname = "SUGAR_" + loginRequest.phone!!.takeLast(4)
            userDO.phone = loginRequest.phone
            userDO.loginAt = OffsetDateTime.now()
            userDO.loginToken = login(userDO.userCode!!)
            userRepository.save(userDO)
        } else {
            userDO!!.clientIp = realIpAddress
            userDO.loginAt = OffsetDateTime.now()
            userDO.loginToken = login(userDO.userCode!!)
            userRepository.updateById(userDO)
        }
        val loginResponse = LoginResponse()
        loginResponse.accessToken = userDO.loginToken
        return Result.ok(loginResponse)
    }

    @Operation(
        summary = "User Logout",
        description = "",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PostMapping("/logout")
    fun logout(): Result<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return Result.err("User not found")
        userDO.loginToken = ""
        userRepository.updateById(userDO)
        return Result.ok()
    }
}

