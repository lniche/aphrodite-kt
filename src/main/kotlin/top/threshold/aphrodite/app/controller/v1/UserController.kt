package top.threshold.aphrodite.app.controller.v1;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.StrUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.Data
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import top.threshold.aphrodite.app.controller.BaseController
import top.threshold.aphrodite.app.repository.IUserRepository
import top.threshold.aphrodite.pkg.constant.CacheKey
import top.threshold.aphrodite.pkg.entity.Result
import top.threshold.aphrodite.pkg.util.RedisUtil
import java.time.OffsetDateTime

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User Module")
class UserController(
    val userRepository: IUserRepository,
    val redisUtil: RedisUtil
) : BaseController() {

    @Data
    class GetUserResponse {
        /**
         * Nickname
         */
        @field:Schema(description = "User Nickname", example = "john")
        var nickname: String? = null

        /**
         * User Number
         */
        @field:Schema(description = "User Number", example = "A8000")
        var userNo: Long? = null

        /**
         * User Code
         */
        @field:Schema(description = "User Code", example = "100000")
        var userCode: String? = null

        /**
         * Email
         */
        @field:Schema(description = "User Email", example = "john@example.com")
        var email: String? = null

        /**
         * Phone Number
         */
        @field:Schema(description = "User Phone", example = "13800138000")
        var phone: String? = null
    }

    @Operation(
        summary = "User Info",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @Parameters(
        Parameter(name = "userCode", description = "User Code", `in` = ParameterIn.PATH)
    )
    @GetMapping("/{userCode}")
    fun getUser(@PathVariable userCode: String): Result<GetUserResponse?> {
        val actualUserCode = if (StrUtil.isBlank(userCode)) {
            loginUid()
        } else {
            userCode
        }
        val redisKey = CacheKey.USER + actualUserCode
        val getUserResponse = redisUtil.getObj(redisKey, GetUserResponse::class.java)

        if (getUserResponse == null) {
            val userDO = userRepository.getByCode(actualUserCode)
            userDO?.let {
                redisUtil[redisKey, it] = 60
                return Result.ok(GetUserResponse().apply { BeanUtil.copyProperties(it, this) })
            }
        }

        getUserResponse?.apply {
            email = DesensitizedUtil.email(email)
            phone = DesensitizedUtil.mobilePhone(phone)
        }

        return Result.ok(getUserResponse)
    }

    @Data
    @Schema(description = "Request object for updating user information")
    class UpdateUserRequest {
        /**
         * Nickname
         */
        @field:Schema(description = "User Nickname", example = "john", required = false)
        var nickname: String? = null

        /**
         * Email
         */
        @field:Schema(description = "User Email", example = "john@example.com", required = false)
        var email: String? = null
    }

    @Operation(
        summary = "User Update",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @PutMapping("")
    fun updateUser(@Validated @RequestBody updateUserRequest: UpdateUserRequest): Result<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return Result.err("User does not exist")
        BeanUtil.copyProperties(updateUserRequest, userDO, "userCode")
        userRepository.updateById(userDO)
        return Result.ok()
    }

    @Operation(
        summary = "User Delete",
        security = [SecurityRequirement(name = "Authorization")]
    )
    @DeleteMapping("")
    fun deleteUser(): Result<Void> {
        val userDO = userRepository.getByCode(loginUid()) ?: return Result.err("User is not valid")
        userDO.status = 3
        userDO.deletedAt = OffsetDateTime.now()
        userRepository.updateById(userDO)
        return Result.ok()
    }
}
